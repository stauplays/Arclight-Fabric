package net.verox.arclight;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.item.ModItems;

public class ModGroup {
    public static final ItemGroup ARCLIGHT_GROUP = FabricItemGroupBuilder.build(
            new Identifier(ArclightMod.MOD_ID, "arclight_group"), () -> new ItemStack(ModItems.ARCLIGHT_CORE));

    public static final ItemGroup ARCLIGHT_MOBS = FabricItemGroupBuilder.build(
            new Identifier(ArclightMod.MOD_ID, "arclight_mobs"), () -> new ItemStack(ModItems.ANGEL_SPAWN));

    public static final ItemGroup ARCLIGHT_RESOURCES = FabricItemGroupBuilder.build(
            new Identifier(ArclightMod.MOD_ID, "arclight_resources"), () -> new ItemStack(ModItems.ARCLIGHT_INGOT));

    public static final ItemGroup ARCLIGHT_BUILDING = FabricItemGroupBuilder.build(
            new Identifier(ArclightMod.MOD_ID, "arclight_building"), () -> new ItemStack(ModBlocks.CLOUDSTONE));
}
