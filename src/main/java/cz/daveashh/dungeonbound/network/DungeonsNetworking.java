package cz.daveashh.dungeonbound.network;

import cz.daveashh.dungeonbound.network.payload.C2SSelectEnchantmentPacket;
import cz.daveashh.dungeonbound.network.payload.C2SUseArtifactPacket;
import cz.daveashh.dungeonbound.network.payload.S2CSoulUpdatePacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import cz.daveashh.dungeonbound.ModConstants;
import cz.daveashh.dungeonbound.component.DungeonsComponents;
import cz.daveashh.dungeonbound.component.EnchantmentSlotData;
import cz.daveashh.dungeonbound.item.base.BaseArtifactItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class DungeonsNetworking {
    private DungeonsNetworking() {
    }

    public static void registerServer() {
        PayloadTypeRegistry.serverboundPlay().register(C2SUseArtifactPacket.TYPE, C2SUseArtifactPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(C2SSelectEnchantmentPacket.TYPE, C2SSelectEnchantmentPacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(S2CSoulUpdatePacket.TYPE, S2CSoulUpdatePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2SUseArtifactPacket.TYPE, (payload, context) -> {
            context.server().execute(() -> handleUseArtifact(context.player()));
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SSelectEnchantmentPacket.TYPE, (payload, context) -> {
            context.server().execute(() -> handleSelectEnchantment(context.player(), payload));
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> syncSouls(handler.getPlayer()));
    }

    public static void syncSouls(ServerPlayer player) {
        ServerPlayNetworking.send(player, new S2CSoulUpdatePacket(PlayerSoulData.get(player)));
    }

    private static void handleUseArtifact(ServerPlayer player) {
        ItemStack artifact = findEquippedArtifact(player);
        if (artifact.isEmpty()) {
            return;
        }

        ModConstants.LOGGER.debug("Player {} used artifact {}", player.getGameProfile().name(), artifact.getItem());
    }

    private static void handleSelectEnchantment(ServerPlayer player, C2SSelectEnchantmentPacket payload) {
        InteractionHand hand = payload.hand();
        if (hand != InteractionHand.MAIN_HAND && hand != InteractionHand.OFF_HAND) {
            return;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            return;
        }

        List<EnchantmentSlotData> slots = stack.get(DungeonsComponents.ENCHANTMENT_SLOTS);
        if (slots == null || payload.slotIndex() < 0 || payload.slotIndex() >= slots.size()) {
            return;
        }

        EnchantmentSlotData currentSlot = slots.get(payload.slotIndex());
        if (!currentSlot.offeredTalents().contains(payload.talentId())) {
            return;
        }

        List<EnchantmentSlotData> updatedSlots = new ArrayList<>(slots);
        updatedSlots.set(payload.slotIndex(), new EnchantmentSlotData(
                currentSlot.offeredTalents(),
                payload.talentId(),
                currentSlot.activeLevel()
        ));
        stack.set(DungeonsComponents.ENCHANTMENT_SLOTS, updatedSlots);
    }

    private static ItemStack findEquippedArtifact(ServerPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty() && stack.getItem() instanceof BaseArtifactItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }
}
