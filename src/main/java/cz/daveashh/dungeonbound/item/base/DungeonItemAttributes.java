package cz.daveashh.dungeonbound.item.base;

import cz.daveashh.dungeonbound.ModConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public final class DungeonItemAttributes {
    private DungeonItemAttributes() {
    }

    public static Item.Properties applyMelee(Item.Properties properties, MeleeWeaponStats stats) {
        return properties.attributes(buildMeleeModifiers(stats));
    }

    public static ItemAttributeModifiers buildMeleeModifiers(MeleeWeaponStats stats) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        if (stats.attackDamage() != 0.0) {
            builder.add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            ModConstants.id("melee_attack_damage"),
                            stats.attackDamage(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        if (stats.attackSpeed() != 0.0) {
            builder.add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            ModConstants.id("melee_attack_speed"),
                            stats.attackSpeed(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        if (stats.attackReach() != 0.0) {
            builder.add(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(
                            ModConstants.id("melee_attack_reach"),
                            stats.attackReach(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        return builder.build();
    }

    public static Item.Properties applyRanged(Item.Properties properties, RangedWeaponStats stats) {
        return properties.attributes(buildRangedModifiers(stats));
    }

    public static ItemAttributeModifiers buildRangedModifiers(RangedWeaponStats stats) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        if (stats.attackDamage() != 0.0) {
            builder.add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            ModConstants.id("ranged_attack_damage"),
                            stats.attackDamage(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        if (stats.attackSpeed() != 0.0) {
            builder.add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            ModConstants.id("ranged_attack_speed"),
                            stats.attackSpeed(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        return builder.build();
    }

    public static Item.Properties applyArmor(Item.Properties properties, ArmorStats stats) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        if (stats.armor() != 0.0) {
            builder.add(
                    Attributes.ARMOR,
                    new AttributeModifier(
                            ModConstants.id("armor"),
                            stats.armor(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        if (stats.armorToughness() != 0.0) {
            builder.add(
                    Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(
                            ModConstants.id("armor_toughness"),
                            stats.armorToughness(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        if (stats.knockbackResistance() != 0.0) {
            builder.add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(
                            ModConstants.id("knockback_resistance"),
                            stats.knockbackResistance(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    stats.equipmentSlot()
            );
        }

        ItemAttributeModifiers modifiers = builder.build();
        if (!modifiers.modifiers().isEmpty()) {
            properties = properties.attributes(modifiers);
        }

        return properties;
    }

    public static Item.Properties applyArtifact(Item.Properties properties, ArtifactStats stats) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        stats.modifiers().forEach(entry -> builder.add(
                entry.attribute(),
                new AttributeModifier(entry.id(), entry.amount(), entry.operation()),
                entry.slot()
        ));

        return properties.attributes(builder.build());
    }

    public static Item.Properties durability(Item.Properties properties, int maxDamage) {
        return properties.durability(maxDamage).component(DataComponents.MAX_DAMAGE, maxDamage);
    }
}
