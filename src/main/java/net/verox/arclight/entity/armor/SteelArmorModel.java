package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.FrozenArmorItem;
import net.verox.arclight.item.SteelArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SteelArmorModel extends AnimatedGeoModel<SteelArmorItem> {
    @Override
    public Identifier getModelResource(SteelArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/steel_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(SteelArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/steel_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(SteelArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
