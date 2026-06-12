package cz.daveashh.dungeonbound.generation;

import cz.daveashh.dungeonbound.component.DungeonsComponents;
import cz.daveashh.dungeonbound.component.EnchantmentSlotData;
import cz.daveashh.dungeonbound.talent.TalentDefinition;
import cz.daveashh.dungeonbound.talent.TalentRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class EnchantmentSlotGenerator {
    public static final int MIN_SLOT_COUNT = 1;
    public static final int MAX_SLOT_COUNT = 3;

    private EnchantmentSlotGenerator() {
    }

    public static List<EnchantmentSlotData> generate(RandomSource random, int powerLevel) {
        int slotCount = random.nextIntBetweenInclusive(MIN_SLOT_COUNT, MAX_SLOT_COUNT);
        List<EnchantmentSlotData> slots = new ArrayList<>(slotCount);

        for (int i = 0; i < slotCount; i++) {
            slots.add(generateSlot(random, powerLevel));
        }

        return List.copyOf(slots);
    }

    public static EnchantmentSlotData generateSlot(RandomSource random, int powerLevel) {
        List<TalentDefinition> pool = new ArrayList<>(TalentRegistry.all());
        List<String> offered = new ArrayList<>(EnchantmentSlotData.MAX_OFFERED_TALENTS);

        int picks = Math.min(EnchantmentSlotData.MAX_OFFERED_TALENTS, pool.size());
        for (int i = 0; i < picks; i++) {
            TalentDefinition picked = pickWeighted(random, pool, powerLevel);
            offered.add(picked.id());
            pool.remove(picked);
        }

        return new EnchantmentSlotData(offered, EnchantmentSlotData.NONE, EnchantmentSlotData.MIN_ACTIVE_LEVEL);
    }

    public static void rollForItem(ItemStack stack, RandomSource random) {
        int powerLevel = stack.getOrDefault(DungeonsComponents.POWER_LEVEL, 0);
        stack.set(DungeonsComponents.ENCHANTMENT_SLOTS, generate(random, powerLevel));
    }

    private static TalentDefinition pickWeighted(RandomSource random, List<TalentDefinition> pool, int powerLevel) {
        double totalWeight = 0.0;
        for (TalentDefinition talent : pool) {
            totalWeight += talent.tier().weight(powerLevel);
        }

        double roll = random.nextDouble() * totalWeight;
        double cumulative = 0.0;

        for (TalentDefinition talent : pool) {
            cumulative += talent.tier().weight(powerLevel);
            if (roll < cumulative) {
                return talent;
            }
        }

        return pool.getLast();
    }
}
