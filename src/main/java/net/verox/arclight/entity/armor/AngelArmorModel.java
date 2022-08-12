package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.AngelArmorItem;
import net.verox.arclight.item.ArclightArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AngelArmorModel extends AnimatedGeoModel<AngelArmorItem> {
    @Override
    public Identifier getModelResource(AngelArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/angel_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(AngelArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/angel_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(AngelArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/angel_armor_animation.json");
    }
}
