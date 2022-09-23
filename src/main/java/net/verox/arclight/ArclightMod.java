package net.verox.arclight;

import net.fabricmc.api.ModInitializer;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.item.ModItems;
import net.verox.arclight.util.ArclightLogger;
import net.verox.arclight.util.ModLootTableModifier;
import net.verox.arclight.util.ModRegistries;
import net.verox.arclight.world.ModConfiguredFeatures;
import net.verox.arclight.world.ModWorldGen;
import net.verox.arclight.world.feature.structure.ModStructures;
import software.bernie.geckolib3.GeckoLib;

public class ArclightMod implements ModInitializer {
	public static final String MOD_ID = "arclight";
	public static final ArclightLogger LOGGER = new ArclightLogger(ArclightLogger.LogLevel.INFO);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModConfiguredFeatures.registerConfiguredFeatures();

		ModWorldGen.generateModWorldGen();
		ModRegistries.registerModStuffs();

		ModLootTableModifier.modifyLootTables();

		GeckoLib.initialize();

		ModStructures.registerStructureFeatures();

	}
}
