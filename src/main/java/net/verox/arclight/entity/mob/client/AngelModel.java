package net.verox.arclight.entity.mob.client;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.entity.mob.custom.AngelEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AngelModel extends AnimatedGeoModel<AngelEntity> {
    @Override
    public Identifier getModelResource(AngelEntity object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/angel.geo.json");
    }

    @Override
    public Identifier getTextureResource(AngelEntity object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/entity/angel/angel.png");
    }

    @Override
    public Identifier getAnimationResource(AngelEntity animateable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/angel.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(AngelEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

}