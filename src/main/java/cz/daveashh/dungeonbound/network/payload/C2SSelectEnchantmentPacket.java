package cz.daveashh.dungeonbound.network.payload;

import cz.daveashh.dungeonbound.ModConstants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;

public record C2SSelectEnchantmentPacket(
        InteractionHand hand,
        int slotIndex,
        String talentId
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SSelectEnchantmentPacket> TYPE =
            new CustomPacketPayload.Type<>(ModConstants.id("select_enchantment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSelectEnchantmentPacket> CODEC = StreamCodec.composite(
            InteractionHand.STREAM_CODEC,
            C2SSelectEnchantmentPacket::hand,
            ByteBufCodecs.VAR_INT,
            C2SSelectEnchantmentPacket::slotIndex,
            ByteBufCodecs.STRING_UTF8,
            C2SSelectEnchantmentPacket::talentId,
            C2SSelectEnchantmentPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
