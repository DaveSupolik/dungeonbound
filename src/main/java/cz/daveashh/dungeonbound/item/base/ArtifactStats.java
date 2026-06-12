package cz.daveashh.dungeonbound.item.base;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public record ArtifactStats(List<ModifierEntry> modifiers) {
    public static ArtifactStats empty() {
        return new ArtifactStats(List.of());
    }

    public static ArtifactStats of(ModifierEntry... modifiers) {
        return new ArtifactStats(List.of(modifiers));
    }

    public record ModifierEntry(
            Holder<Attribute> attribute,
            Identifier id,
            double amount,
            AttributeModifier.Operation operation,
            EquipmentSlotGroup slot
    ) {
    }
}
