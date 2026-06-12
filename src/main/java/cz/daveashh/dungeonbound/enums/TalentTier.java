package cz.daveashh.dungeonbound.enums;

public enum TalentTier {
    COMMON,
    POWERFUL;

    public double weight(int powerLevel) {
        return switch (this) {
            case COMMON -> 100.0;
            case POWERFUL -> 5.0 + 0.4 * powerLevel;
        };
    }
}
