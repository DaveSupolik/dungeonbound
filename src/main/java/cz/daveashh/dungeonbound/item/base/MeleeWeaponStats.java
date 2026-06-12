package cz.daveashh.dungeonbound.item.base;

import net.minecraft.world.entity.EquipmentSlotGroup;

public record MeleeWeaponStats(
        double attackDamage,
        double attackSpeed,
        double attackReach,
        EquipmentSlotGroup equipmentSlot
) {
    public static MeleeWeaponStats defaults(double attackDamage, double attackSpeed) {
        return new MeleeWeaponStats(attackDamage, attackSpeed, 0.0, EquipmentSlotGroup.MAINHAND);
    }

    public static MeleeWeaponStats of(double attackDamage, double attackSpeed, double attackReach) {
        return new MeleeWeaponStats(attackDamage, attackSpeed, attackReach, EquipmentSlotGroup.MAINHAND);
    }
}
