package cz.daveashh.dungeonbound.network.payload;

import cz.daveashh.dungeonbound.ModConstants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record S2CSoulUpdatePacket(int souls) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CSoulUpdatePacket> TYPE =
            new CustomPacketPayload.Type<>(ModConstants.id("soul_update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSoulUpdatePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            S2CSoulUpdatePacket::souls,
            S2CSoulUpdatePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
