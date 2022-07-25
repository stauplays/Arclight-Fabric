package net.verox.arclight.entity.mob.client;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.JellyfishEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public Identifier getModelResource(JellyfishEntity object) {
            return new Identifier(ArclightMod.MOD_ID, "geo/moonlight_jelly.geo.json");
    }

    @Override
    public Identifier getTextureResource(JellyfishEntity object) {
            return new Identifier(ArclightMod.MOD_ID, "textures/entity/jellyfish/moonlight_jelly.png");
    }

    @Override
    public Identifier getAnimationResource(JellyfishEntity animateable) {
            return new Identifier(ArclightMod.MOD_ID, "animations/moonlight_jelly.animation.json");
    }
}