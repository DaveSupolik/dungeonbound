package cz.daveashh.dungeonbound;

import cz.daveashh.dungeonbound.component.DungeonsComponents;
import cz.daveashh.dungeonbound.talent.TalentRegistry;
import net.fabricmc.api.ModInitializer;

public class DungeonsMain implements ModInitializer {

	@Override
	public void onInitialize() {
		ModConstants.LOGGER.info("Inicializace Dungeons Mod");
		DungeonsComponents.initialize();
		TalentRegistry.initialize();
	}
}