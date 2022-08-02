package net.verox.arclight.entity.armor;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.MoonlightArmorItem;
import net.verox.arclight.item.ScorpionArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionArmorModel extends AnimatedGeoModel<ScorpionArmorItem> {
    @Override
    public Identifier getModelResource(ScorpionArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/scorpion_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScorpionArmorItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/models/armor/scorpion_armor_textures.png");
    }

    @Override
    public Identifier getAnimationResource(ScorpionArmorItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
