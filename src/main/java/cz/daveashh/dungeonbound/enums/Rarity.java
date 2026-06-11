package cz.daveashh.dungeonbound.enums;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum Rarity implements StringRepresentable {
    COMMON("common"),
    RARE("rare"),
    UNIQUE("unique");

    public static final Codec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);

    private final String name;

    Rarity(String name) {
        this.name = name;
    }

    @Override
    public @NonNull String getSerializedName() {
        return this.name;
    }
}
