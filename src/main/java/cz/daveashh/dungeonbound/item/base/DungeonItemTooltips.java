package cz.daveashh.dungeonbound.item.base;

import cz.daveashh.dungeonbound.component.DungeonsComponents;
import cz.daveashh.dungeonbound.component.EnchantmentSlotData;
import cz.daveashh.dungeonbound.enums.Rarity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

public final class DungeonItemTooltips {
    private DungeonItemTooltips() {
    }

    public static void appendDungeonComponents(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
        Integer powerLevel = stack.get(DungeonsComponents.POWER_LEVEL);
        if (powerLevel != null) {
            tooltip.accept(Component.translatable("tooltip.dungeonbound.power_level", powerLevel).withStyle(ChatFormatting.GRAY));
        }

        Rarity rarity = stack.get(DungeonsComponents.RARITY);
        if (rarity != null) {
            tooltip.accept(Component.translatable("tooltip.dungeonbound.rarity." + rarity.getSerializedName()).withStyle(colorFor(rarity)));
        }

        List<EnchantmentSlotData> slots = stack.get(DungeonsComponents.ENCHANTMENT_SLOTS);
        if (slots != null && !slots.isEmpty()) {
            tooltip.accept(Component.translatable("tooltip.dungeonbound.talent_slots", slots.size()).withStyle(ChatFormatting.DARK_AQUA));
        }
    }

    public static void appendDescription(Component description, Consumer<Component> tooltip) {
        tooltip.accept(description.copy().withStyle(ChatFormatting.DARK_GRAY));
    }

    private static ChatFormatting colorFor(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> ChatFormatting.WHITE;
            case RARE -> ChatFormatting.BLUE;
            case UNIQUE -> ChatFormatting.LIGHT_PURPLE;
        };
    }
}
