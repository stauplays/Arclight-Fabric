package net.verox.arclight.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.entity.mob.EntityTypes;
import net.verox.arclight.entity.mob.custom.AngelEntity;
import net.verox.arclight.entity.mob.custom.JellyfishEntity;
import net.verox.arclight.entity.mob.custom.ScorpionEntity;

public class ModRegistries {
    public static void registerModStuffs() {

        registerAttributes();
        registerFlammableBlock();
        registerStrippables();

    }
    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(EntityTypes.ANGEL, AngelEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypes.JELLY, JellyfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypes.SCORPION, ScorpionEntity.setAttributes());
    }

    private static void registerStrippables(){

        StrippableBlockRegistry.register(ModBlocks.MOON_LOG, ModBlocks.STRIPPED_MOON_LOG);
        StrippableBlockRegistry.register(ModBlocks.MOON_WOOD, ModBlocks.STRIPPED_MOON_WOOD);
        StrippableBlockRegistry.register(ModBlocks.JADE_LOG, ModBlocks.STRIPPED_JADE_LOG);
        StrippableBlockRegistry.register(ModBlocks.JADE_WOOD, ModBlocks.STRIPPED_JADE_WOOD);
        StrippableBlockRegistry.register(ModBlocks.SHADOW_LOG, ModBlocks.STRIPPED_SHADOW_LOG);
        StrippableBlockRegistry.register(ModBlocks.SHADOW_WOOD, ModBlocks.STRIPPED_SHADOW_WOOD);

    }

    private static void registerFlammableBlock() {

        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();

        instance.add(ModBlocks.MOON_LOG, 5, 5);
        instance.add(ModBlocks.MOON_WOOD, 5, 5);
        instance.add(ModBlocks.STRIPPED_MOON_LOG, 5, 5);
        instance.add(ModBlocks.STRIPPED_MOON_WOOD, 5, 5);
        instance.add(ModBlocks.MOON_PLANKS, 5, 20);
        instance.add(ModBlocks.MOON_LEAVES, 30, 60);
        instance.add(ModBlocks.MOON_DOOR, 5, 20);
        instance.add(ModBlocks.MOON_TRAPDOOR, 5, 20);
        instance.add(ModBlocks.MOON_SLAB, 5, 20);
        instance.add(ModBlocks.MOON_STAIRS, 5, 20);
        instance.add(ModBlocks.MOON_FENCE, 5, 20);
        instance.add(ModBlocks.MOON_FENCE_GATE, 5, 20);

        instance.add(ModBlocks.JADE_LOG, 5, 5);
        instance.add(ModBlocks.JADE_WOOD, 5, 5);
        instance.add(ModBlocks.STRIPPED_JADE_LOG, 5, 5);
        instance.add(ModBlocks.STRIPPED_JADE_WOOD, 5, 5);
        instance.add(ModBlocks.JADE_PLANKS, 5, 20);
        instance.add(ModBlocks.JADE_LEAVES, 30, 60);
        instance.add(ModBlocks.JADE_DOOR, 5, 20);
        instance.add(ModBlocks.JADE_TRAPDOOR, 5, 20);
        instance.add(ModBlocks.JADE_SLAB, 5, 20);
        instance.add(ModBlocks.JADE_STAIRS, 5, 20);
        instance.add(ModBlocks.JADE_FENCE, 5, 20);
        instance.add(ModBlocks.JADE_FENCE_GATE, 5, 20);

    }

}
