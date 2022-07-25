package net.verox.arclight;

import net.fabricmc.api.ModInitializer;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.item.ModItems;
import net.verox.arclight.util.ModLootTableModifier;
import net.verox.arclight.util.ModRegistries;
import net.verox.arclight.world.ModConfiguredFeatures;
import net.verox.arclight.world.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class ArclightMod implements ModInitializer {
	public static final String MOD_ID = "arclight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModConfiguredFeatures.registerConfiguredFeatures();

		ModWorldGen.generateModWorldGen();
		ModRegistries.registerModStuffs();

		ModLootTableModifier.modifyLootTables();

		GeckoLib.initialize();

	}
}
