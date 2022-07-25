package net.verox.arclight.entity.mob.client;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.ScorpionEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionModel extends AnimatedGeoModel<ScorpionEntity> {
    @Override
    public Identifier getModelResource(ScorpionEntity object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/scorpion.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScorpionEntity object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/entity/scorpion/scorpion.png");
    }

    @Override
    public Identifier getAnimationResource(ScorpionEntity animateable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/scorpion.animation.json");
    }
}