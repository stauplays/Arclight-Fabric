package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.ArclightArmorItem;
import net.verox.arclight.item.ShadowArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShadowArmorModel extends AnimatedGeoModel<ShadowArmorItem> {
    @Override
    public Identifier getModelResource(ShadowArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/shadow_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/shadow_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(ShadowArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
