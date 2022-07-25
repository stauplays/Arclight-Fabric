package net.verox.arclight.entity.mob.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.JellyfishEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class JellyfishRenderer extends GeoEntityRenderer<JellyfishEntity> {
    public JellyfishRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new JellyfishModel());
    }

    @Override
    public Identifier getTextureResource(JellyfishEntity instance) {
        return new Identifier(ArclightMod.MOD_ID, "textures/entity/jellyfish/moonlight_jelly.png");
    }
}