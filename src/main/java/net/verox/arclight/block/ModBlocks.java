package net.verox.arclight.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.ModGroup;
import net.verox.arclight.block.custom.*;
import net.verox.arclight.world.feature.tree.JadeSaplingGenerator;
import net.verox.arclight.world.feature.tree.MoonSaplingGenerator;
import net.verox.arclight.world.feature.tree.ShadowSaplingGenerator;

public class ModBlocks {

    public static final Block  CLOUDSTONE = registerBlock("cloudstone",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_BRICK = registerBlock("cloudstone_brick",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_COBBLED = registerBlock("cloudstone_cobbled",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_WALL = registerBlock("cloudstone_wall",
            new ModWallBlocks(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_BRICK_WALL = registerBlock("cloudstone_brick_wall",
            new ModWallBlocks(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_SLAB = registerBlock("cloudstone_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_STAIRS = registerBlock("cloudstone_stairs",
            new ModStairsBlock(ModBlocks.CLOUDSTONE.getDefaultState(),
                    FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_BRICK_SLAB = registerBlock("cloudstone_brick_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  CLOUDSTONE_BRICK_STAIRS = registerBlock("cloudstone_brick_stairs",
            new ModStairsBlock(ModBlocks.CLOUDSTONE_BRICK.getDefaultState(),
                    FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  ARCLIGHT_BLOCK = registerBlock("arclight_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    //MOONWOOD
    public static final Block  MOON_LOG = registerBlock("moon_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  MOON_WOOD = registerBlock("moon_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_MOON_LOG = registerBlock("stripped_moon_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_MOON_WOOD = registerBlock("stripped_moon_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  MOON_PLANKS = registerBlock("moon_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_FENCE = registerBlock("moon_fence",
            new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_FENCE_GATE = registerBlock("moon_fence_gate",
            new FenceGateBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_SLAB = registerBlock("moon_slab",
            new SlabBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_STAIRS = registerBlock("moon_stairs",
            new ModStairsBlock(ModBlocks.MOON_PLANKS.getDefaultState(),
                    FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_DOOR = registerBlock("moon_door",
            new ModDoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  MOON_TRAPDOOR = registerBlock("moon_trapdoor",
            new ModTrapdoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);

    //JADEWOOD
    public static final Block  JADE_LOG = registerBlock("jade_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  JADE_WOOD = registerBlock("jade_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_JADE_LOG = registerBlock("stripped_jade_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_JADE_WOOD = registerBlock("stripped_jade_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  JADE_PLANKS = registerBlock("jade_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_FENCE = registerBlock("jade_fence",
            new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_FENCE_GATE = registerBlock("jade_fence_gate",
            new FenceGateBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_SLAB = registerBlock("jade_slab",
            new SlabBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_STAIRS = registerBlock("jade_stairs",
            new ModStairsBlock(ModBlocks.JADE_PLANKS.getDefaultState(),
                    FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_DOOR = registerBlock("jade_door",
            new ModDoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_TRAPDOOR = registerBlock("jade_trapdoor",
            new ModTrapdoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);

    //SHADOWWOOD
    public static final Block  SHADOW_LOG = registerBlock("shadow_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  SHADOW_WOOD = registerBlock("shadow_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_SHADOW_LOG = registerBlock("stripped_shadow_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  STRIPPED_SHADOW_WOOD = registerBlock("stripped_shadow_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  SHADOW_PLANKS = registerBlock("shadow_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_FENCE = registerBlock("shadow_fence",
            new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_FENCE_GATE = registerBlock("shadow_fence_gate",
            new FenceGateBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_SLAB = registerBlock("shadow_slab",
            new SlabBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_STAIRS = registerBlock("shadow_stairs",
            new ModStairsBlock(ModBlocks.SHADOW_PLANKS.getDefaultState(),
                    FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_DOOR = registerBlock("shadow_door",
            new ModDoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_TRAPDOOR = registerBlock("shadow_trapdoor",
            new ModTrapdoorBlock(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool().nonOpaque()), ModGroup.ARCLIGHT_BUILDING);


    public static final Block  MOON_LEAVES = registerBlock("moon_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  MOON_SAPLING = registerBlock("moon_sapling",
            new ModSpalingBlockDirt(new MoonSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  JADE_LEAVES = registerBlock("jade_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  JADE_SAPLING = registerBlock("jade_sapling",
            new ModSpalingBlockDirt(new JadeSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  SHADOW_LEAVES = registerBlock("shadow_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()), ModGroup.ARCLIGHT_BUILDING);
    public static final Block  SHADOW_SAPLING = registerBlock("shadow_sapling",
        new ModSpalingBlockDirt(new ShadowSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), ModGroup.ARCLIGHT_BUILDING);

    public static final Block  ARCLIGHT_ORE = registerBlock("arclight_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(3, 7)), ModGroup.ARCLIGHT_RESOURCES);

    public static final Block  MOONLIGHT_ORE = registerBlock("moonlight_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(3, 7)), ModGroup.ARCLIGHT_RESOURCES);

    public static final Block  JADE_ORE = registerBlock("jade_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(3, 7)), ModGroup.ARCLIGHT_RESOURCES);

    public static final Block  ARMOR_FORGE = registerBlock("armor_forge",
            new OreBlock(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool(),
                    UniformIntProvider.create(3, 7)), ModGroup.ARCLIGHT_BUILDING);

    public static final Block BOSS_SPAWNER = registerBlock("boss_spawner",
            new Block(FabricBlockSettings.copy(Blocks.BEDROCK).nonOpaque()), ModGroup.ARCLIGHT_MOBS);

    public static final Block BOSS_SPAWNER_NETHER = registerBlock("boss_spawner_nether",
            new Block(FabricBlockSettings.copy(Blocks.BEDROCK).nonOpaque()), ModGroup.ARCLIGHT_MOBS);

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(ArclightMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(ArclightMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }



    public static void registerModBlocks() {
        ArclightMod.LOGGER.debug("Registering ModBlocks for" + ArclightMod.MOD_ID);
    }
}
