package net.verox.arclight.entity.mob;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.AngelEntity;
import net.verox.arclight.entity.mob.custom.JellyfishEntity;
import net.verox.arclight.entity.mob.custom.ScorpionEntity;

public class EntityTypes {

    public static final EntityType<AngelEntity> ANGEL = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(ArclightMod.MOD_ID, "angel"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AngelEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 2.0f)).build());

    public static final EntityType<JellyfishEntity> JELLY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(ArclightMod.MOD_ID, "jelly"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JellyfishEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 1.0f)).build());

    public static final EntityType<ScorpionEntity> SCORPION = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(ArclightMod.MOD_ID, "scorpion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ScorpionEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 2.5f)).fireImmune().build());

}