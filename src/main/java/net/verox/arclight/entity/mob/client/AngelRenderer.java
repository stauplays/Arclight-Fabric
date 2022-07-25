package net.verox.arclight.entity.mob.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.AngelEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AngelRenderer extends GeoEntityRenderer<AngelEntity> {
    public AngelRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AngelModel());
    }

    @Override
    public Identifier getTextureResource(AngelEntity instance) {
        return new Identifier(ArclightMod.MOD_ID, "textures/entity/angel/angel.png");
    }
}