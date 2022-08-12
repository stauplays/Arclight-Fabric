package net.verox.arclight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.entity.armor.*;
import net.verox.arclight.entity.mob.EntityTypes;
import net.verox.arclight.entity.mob.client.AngelRenderer;
import net.verox.arclight.entity.mob.client.JellyfishRenderer;
import net.verox.arclight.entity.mob.client.ScorpionRenderer;
import net.verox.arclight.item.ModItems;
import net.verox.arclight.item.client.AngelSwordRenderer;
import net.verox.arclight.item.client.GlaiveRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ArclightModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        GeoArmorRenderer.registerArmorRenderer(new ArclightArmorRenderer(), ModItems.ARCLIGHTS_BOOTS,
                ModItems.ARCLIGHTS_LEGGINGS, ModItems.ARCLIGHTS_CHESTLATE, ModItems.ARCLIGHTS_HELMET);

        GeoArmorRenderer.registerArmorRenderer(new HolyKnightArmorRenderer(), ModItems.HOLYKNIGHT_BOOTS,
                ModItems.HOLYKNIGHT_LEGGINGS, ModItems.HOLYKNIGHT_CHESTLATE, ModItems.HOLYKNIGHT_HELMET);

        GeoArmorRenderer.registerArmorRenderer(new ShadowArmorRenderer(), ModItems.SHADOW_BOOTS,
                ModItems.SHADOW_CHESTPLATE, ModItems.SHADOW_HELMET, ModItems.SHADOW_LEGGINGS);

        GeoArmorRenderer.registerArmorRenderer(new MoonlightArmorRenderer(), ModItems.MOONLIGHT_BOOTS,
                ModItems.MOONLIGHT_CHESTPLATE, ModItems.MOONLIGHT_LEGGINGS, ModItems.MOONLIGHT_HELMET);

        GeoArmorRenderer.registerArmorRenderer(new ScorpionArmorRenderer(), ModItems.SCORPION_BOOTS,
                ModItems.SCORPION_CHESTPLATE, ModItems.SCORPION_LEGGINGS, ModItems.SCORPION_HELMET);

        GeoArmorRenderer.registerArmorRenderer(new FrozenArmorRenderer(), ModItems.FROZEN_BOOTS,
                ModItems.FROZEN_CHESTPLATE, ModItems.FROZEN_HELMET, ModItems.FROZEN_LEGGINGS);

        GeoArmorRenderer.registerArmorRenderer(new SteelArmorRenderer(), ModItems.STEEL_BOOTS,
                ModItems.STEEL_CHESTPLATE, ModItems.STEEL_HELMET, ModItems.STEEL_LEGGINGS);

        GeoArmorRenderer.registerArmorRenderer(new AngelArmorRenderer(), ModItems.ANGEL_CHESTLATE,
                ModItems.ANGEL_BOOTS, ModItems.ANGEL_HELMET, ModItems.ANGEL_LEGGINGS);

        GeoItemRenderer.registerItemRenderer(ModItems.ANGEL_SWORD, new AngelSwordRenderer());

        GeoItemRenderer.registerItemRenderer(ModItems.MOONLIGHT_GLAIVE, new GlaiveRenderer());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JADE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JADE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADOW_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADOW_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOON_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOON_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JADE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JADE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADOW_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADOW_SAPLING, RenderLayer.getCutout());

        EntityRendererRegistry.register(EntityTypes.ANGEL, AngelRenderer::new);
        EntityRendererRegistry.register(EntityTypes.JELLY, JellyfishRenderer::new);
        EntityRendererRegistry.register(EntityTypes.SCORPION, ScorpionRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOSS_SPAWNER, RenderLayer.getCutout());

    }
}
