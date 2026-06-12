package cz.daveashh.dungeonbound.network.payload;

import cz.daveashh.dungeonbound.ModConstants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record C2SUseArtifactPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SUseArtifactPacket> TYPE =
            new CustomPacketPayload.Type<>(ModConstants.id("use_artifact"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SUseArtifactPacket> CODEC =
            StreamCodec.unit(new C2SUseArtifactPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
