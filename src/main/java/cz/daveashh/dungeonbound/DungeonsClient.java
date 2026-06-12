package cz.daveashh.dungeonbound;

import cz.daveashh.dungeonbound.network.client.DungeonsClientNetworking;
import net.fabricmc.api.ClientModInitializer;

public class DungeonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DungeonsClientNetworking.registerClient();
    }
}
