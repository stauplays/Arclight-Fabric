package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.ArclightArmorItem;
import net.verox.arclight.item.FrozenArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrozenArmorModel extends AnimatedGeoModel<FrozenArmorItem> {
    @Override
    public Identifier getModelResource(FrozenArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/frosted_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(FrozenArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/frosted_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(FrozenArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
