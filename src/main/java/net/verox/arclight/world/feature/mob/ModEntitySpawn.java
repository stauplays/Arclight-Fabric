package net.verox.arclight.world.feature.mob;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.Heightmap;
import net.verox.arclight.entity.mob.EntityTypes;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(),
                SpawnGroup.CREATURE, EntityTypes.JELLY, 5, 2, 5);

        SpawnRestrictionAccessor.callRegister(EntityTypes.JELLY, SpawnRestriction.Location.NO_RESTRICTIONS,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyingEntity::canMobSpawn);
    }
}
