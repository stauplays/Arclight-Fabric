package net.verox.arclight.world;

import net.verox.arclight.world.feature.mob.ModEntitySpawn;
import net.verox.arclight.world.feature.ore.ModOreGeneration;

public class ModWorldGen {

    public static void generateModWorldGen() {

        ModTreeGen.generateTrees();
        ModOreGeneration.generateOres();
        ModEntitySpawn.addEntitySpawn();

    }

}
