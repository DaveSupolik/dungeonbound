package cz.daveashh.dungeonbound.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record EnchantmentSlotData(
        List<String> offeredTalents,
        String activeSelection,
        int activeLevel
) {
    public static final String NONE = "none";
    public static final int MIN_ACTIVE_LEVEL = 0;
    public static final int MAX_ACTIVE_LEVEL = 3;
    public static final int MAX_OFFERED_TALENTS = 4;

    private static final Codec<List<String>> OFFERED_TALENTS_CODEC = Codec.STRING.listOf()
            .xmap(EnchantmentSlotData::normalizeOfferedTalents, talents -> talents);

    private static final Codec<Integer> ACTIVE_LEVEL_CODEC = Codec.INT
            .xmap(EnchantmentSlotData::normalizeActiveLevel, level -> level);

    public static final Codec<EnchantmentSlotData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            OFFERED_TALENTS_CODEC.optionalFieldOf("offered_talents", List.of()).forGetter(EnchantmentSlotData::offeredTalents),
            Codec.STRING.optionalFieldOf("active_selection", NONE).forGetter(EnchantmentSlotData::activeSelection),
            ACTIVE_LEVEL_CODEC.optionalFieldOf("active_level", MIN_ACTIVE_LEVEL).forGetter(EnchantmentSlotData::activeLevel)
    ).apply(instance, EnchantmentSlotData::new));

    public EnchantmentSlotData {
        offeredTalents = normalizeOfferedTalents(offeredTalents);
        activeLevel = normalizeActiveLevel(activeLevel);
        activeSelection = normalizeActiveSelection(activeSelection, offeredTalents);
    }

    public static EnchantmentSlotData empty() {
        return new EnchantmentSlotData(List.of(), NONE, MIN_ACTIVE_LEVEL);
    }

    private static List<String> normalizeOfferedTalents(List<String> talents) {
        if (talents.size() <= MAX_OFFERED_TALENTS) {
            return List.copyOf(talents);
        }
        return List.copyOf(talents.subList(0, MAX_OFFERED_TALENTS));
    }

    private static int normalizeActiveLevel(int level) {
        return Math.clamp(level, MIN_ACTIVE_LEVEL, MAX_ACTIVE_LEVEL);
    }

    private static String normalizeActiveSelection(String selection, List<String> offeredTalents) {
        if (NONE.equals(selection) || offeredTalents.contains(selection)) {
            return selection;
        }
        return NONE;
    }
}
