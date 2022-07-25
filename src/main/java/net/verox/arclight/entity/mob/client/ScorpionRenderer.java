package net.verox.arclight.entity.mob.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.ScorpionEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ScorpionRenderer extends GeoEntityRenderer<ScorpionEntity> {
    public ScorpionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScorpionModel());
    }

    @Override
    public Identifier getTextureResource(ScorpionEntity instance) {
        return new Identifier(ArclightMod.MOD_ID, "textures/entity/scorpion/scorpion.png");
    }
}