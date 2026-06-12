package cz.daveashh.dungeonbound.item.base;

public class BaseRangedItem extends BaseDungeonItem {
    private final RangedWeaponStats stats;

    public BaseRangedItem(Properties properties, RangedWeaponStats stats) {
        super(DungeonItemAttributes.applyRanged(properties, stats));
        this.stats = stats;
    }

    public BaseRangedItem(Properties properties, RangedWeaponStats stats, int maxDamage) {
        super(DungeonItemAttributes.applyRanged(DungeonItemAttributes.durability(properties, maxDamage), stats));
        this.stats = stats;
    }

    public RangedWeaponStats stats() {
        return stats;
    }
}
