package cz.daveashh.dungeonbound.component;

import com.mojang.serialization.Codec;
import cz.daveashh.dungeonbound.ModConstants;
import cz.daveashh.dungeonbound.enums.Rarity;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class DungeonsComponents {

    public static final DataComponentType<Integer> POWER_LEVEL = register("power_level", Codec.INT);
    public static final DataComponentType<Rarity> RARITY = register("rarity", Rarity.CODEC);
    public static final DataComponentType<Integer> CURRENT_COOLDOWN_TICK = register("current_cooldown_tick", Codec.INT);

    private static <T> DataComponentType<T> register(String name, Codec<T> codec) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, name),
                DataComponentType.<T>builder().persistent(codec).build()
        );
    }

    public static void initialize() {
        ModConstants.LOGGER.info("Registered Dungeons data components.");
    }
}
