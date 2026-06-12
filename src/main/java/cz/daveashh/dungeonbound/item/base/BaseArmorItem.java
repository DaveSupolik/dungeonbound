package cz.daveashh.dungeonbound.item.base;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.Item;

public class BaseArmorItem extends BaseDungeonItem {
    private final ArmorMaterial material;
    private final ArmorType armorType;
    private final ArmorStats extraStats;

    public BaseArmorItem(ArmorMaterial material, ArmorType armorType, int baseDurability, Properties properties, ArmorStats extraStats) {
        super(buildProperties(material, armorType, baseDurability, properties, extraStats));
        this.material = material;
        this.armorType = armorType;
        this.extraStats = extraStats;
    }

    public BaseArmorItem(ArmorMaterial material, ArmorType armorType, int baseDurability, Properties properties) {
        this(material, armorType, baseDurability, properties, ArmorStats.empty(armorType));
    }

    public ArmorMaterial material() {
        return material;
    }

    public ArmorType armorType() {
        return armorType;
    }

    public ArmorStats extraStats() {
        return extraStats;
    }

    private static Properties buildProperties(
            ArmorMaterial material,
            ArmorType armorType,
            int baseDurability,
            Properties properties,
            ArmorStats extraStats
    ) {
        Properties armorProperties = properties
                .humanoidArmor(material, armorType)
                .durability(armorType.getDurability(baseDurability));

        return DungeonItemAttributes.applyArmor(armorProperties, extraStats);
    }
}
