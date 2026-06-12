package cz.daveashh.dungeonbound.network.client;

public final class ClientSoulData {
    private static int souls;

    private ClientSoulData() {
    }

    public static int getSouls() {
        return souls;
    }

    public static void setSouls(int value) {
        souls = Math.max(0, value);
    }
}
