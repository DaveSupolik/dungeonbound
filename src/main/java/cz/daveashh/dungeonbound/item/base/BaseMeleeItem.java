package cz.daveashh.dungeonbound.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class BaseMeleeItem extends BaseDungeonItem {
    private final MeleeWeaponStats stats;

    public BaseMeleeItem(Properties properties, MeleeWeaponStats stats) {
        super(DungeonItemAttributes.applyMelee(properties, stats));
        this.stats = stats;
    }

    public BaseMeleeItem(Properties properties, MeleeWeaponStats stats, int maxDamage) {
        super(DungeonItemAttributes.applyMelee(DungeonItemAttributes.durability(properties, maxDamage), stats));
        this.stats = stats;
    }

    public MeleeWeaponStats stats() {
        return stats;
    }

    @Override
    protected void appendAdditionalHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
        if (stats.attackReach() != 0.0) {
            tooltip.accept(Component.translatable("tooltip.dungeonbound.attack_reach", stats.attackReach()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }
}
