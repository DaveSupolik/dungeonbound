package cz.daveashh.dungeonbound.network.client;

import cz.daveashh.dungeonbound.network.payload.C2SSelectEnchantmentPacket;
import cz.daveashh.dungeonbound.network.payload.C2SUseArtifactPacket;
import cz.daveashh.dungeonbound.network.payload.S2CSoulUpdatePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.InteractionHand;

public final class DungeonsClientNetworking {
    private DungeonsClientNetworking() {
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2CSoulUpdatePacket.TYPE, (payload, context) -> {
            context.client().execute(() -> ClientSoulData.setSouls(payload.souls()));
        });
    }

    public static void sendUseArtifact() {
        ClientPlayNetworking.send(new C2SUseArtifactPacket());
    }

    public static void sendSelectEnchantment(InteractionHand hand, int slotIndex, String talentId) {
        ClientPlayNetworking.send(new C2SSelectEnchantmentPacket(hand, slotIndex, talentId));
    }
}
