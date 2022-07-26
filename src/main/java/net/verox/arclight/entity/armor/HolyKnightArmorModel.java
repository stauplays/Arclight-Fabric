package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.ArclightArmorItem;
import net.verox.arclight.item.HolyknightArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HolyKnightArmorModel extends AnimatedGeoModel<HolyknightArmorItem> {
    @Override
    public Identifier getModelResource(HolyknightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/holyknight_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(HolyknightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/holyknight_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(HolyknightArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}