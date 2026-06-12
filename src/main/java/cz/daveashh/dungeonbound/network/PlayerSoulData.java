package cz.daveashh.dungeonbound.network;

import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerSoulData {
    private static final Map<UUID, Integer> SOULS = new ConcurrentHashMap<>();

    private PlayerSoulData() {
    }

    public static int get(ServerPlayer player) {
        return SOULS.getOrDefault(player.getUUID(), 0);
    }

    public static void set(ServerPlayer player, int souls) {
        SOULS.put(player.getUUID(), Math.max(0, souls));
        DungeonsNetworking.syncSouls(player);
    }

    public static void add(ServerPlayer player, int amount) {
        set(player, get(player) + amount);
    }

    public static void remove(ServerPlayer player, int amount) {
        set(player, get(player) - amount);
    }

    public static void clear(ServerPlayer player) {
        SOULS.remove(player.getUUID());
    }
}
