package cz.daveashh.dungeonbound.item.base;

import net.minecraft.world.entity.EquipmentSlotGroup;

public record RangedWeaponStats(
        double attackDamage,
        double attackSpeed,
        EquipmentSlotGroup equipmentSlot
) {
    public static RangedWeaponStats defaults(double attackDamage, double attackSpeed) {
        return new RangedWeaponStats(attackDamage, attackSpeed, EquipmentSlotGroup.MAINHAND);
    }
}
