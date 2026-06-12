package cz.daveashh.dungeonbound.talent;

import cz.daveashh.dungeonbound.ModConstants;
import cz.daveashh.dungeonbound.enums.TalentTier;

import java.util.ArrayList;
import java.util.List;

public final class TalentRegistry {
    private static final List<TalentDefinition> TALENTS = new ArrayList<>();

    private TalentRegistry() {
    }

    public static void initialize() {
        register("sharpness", TalentTier.COMMON);
        register("swift_strike", TalentTier.COMMON);
        register("echo", TalentTier.COMMON);
        register("chain_reaction", TalentTier.COMMON);
        register("gravity", TalentTier.COMMON);

        register("radiance", TalentTier.POWERFUL);
        register("critical_hit", TalentTier.POWERFUL);
        register("thundering", TalentTier.POWERFUL);

        ModConstants.LOGGER.info("Registered {} talents.", TALENTS.size());
    }

    private static void register(String id, TalentTier tier) {
        TALENTS.add(new TalentDefinition(id, tier));
    }

    public static List<TalentDefinition> all() {
        return List.copyOf(TALENTS);
    }
}
