package cz.daveashh.dungeonbound.item.base;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.equipment.ArmorType;

public record ArmorStats(
        double armor,
        double armorToughness,
        double knockbackResistance,
        EquipmentSlotGroup equipmentSlot
) {
    public static ArmorStats empty(ArmorType armorType) {
        return new ArmorStats(0.0, 0.0, 0.0, slotGroupFor(armorType));
    }

    public static ArmorStats of(double armor, ArmorType armorType) {
        return new ArmorStats(armor, 0.0, 0.0, slotGroupFor(armorType));
    }

    public static ArmorStats of(double armor, double armorToughness, double knockbackResistance, ArmorType armorType) {
        return new ArmorStats(armor, armorToughness, knockbackResistance, slotGroupFor(armorType));
    }

    private static EquipmentSlotGroup slotGroupFor(ArmorType armorType) {
        return EquipmentSlotGroup.bySlot(armorType.getSlot());
    }
}
