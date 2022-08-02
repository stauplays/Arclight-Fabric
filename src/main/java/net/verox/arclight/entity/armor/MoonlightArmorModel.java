package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.MoonlightArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoonlightArmorModel extends AnimatedGeoModel<MoonlightArmorItem> {
    @Override
    public Identifier getModelResource(MoonlightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/moonlight_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(MoonlightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/moonlight_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(MoonlightArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
