package cz.daveashh.dungeonbound.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public abstract class BaseDungeonItem extends Item {
    protected BaseDungeonItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
        appendBaseHoverText(stack, context, display, tooltip, flag);
        appendAdditionalHoverText(stack, context, display, tooltip, flag);
    }

    protected void appendBaseHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
        DungeonItemTooltips.appendDungeonComponents(stack, context, display, tooltip, flag);
    }

    protected void appendAdditionalHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
    }
}
