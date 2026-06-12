package cz.daveashh.dungeonbound.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class BaseArtifactItem extends BaseDungeonItem {
    private final ArtifactStats stats;

    public BaseArtifactItem(Properties properties, ArtifactStats stats) {
        super(DungeonItemAttributes.applyArtifact(properties, stats));
        this.stats = stats;
    }

    public ArtifactStats stats() {
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
        tooltip.accept(Component.translatable("tooltip.dungeonbound.artifact").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
