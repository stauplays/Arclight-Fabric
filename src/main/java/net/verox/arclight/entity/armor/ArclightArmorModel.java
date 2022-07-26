package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.ArclightArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArclightArmorModel extends AnimatedGeoModel<ArclightArmorItem> {
    @Override
    public Identifier getModelResource(ArclightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/arclight_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArclightArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/arclight_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(ArclightArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
