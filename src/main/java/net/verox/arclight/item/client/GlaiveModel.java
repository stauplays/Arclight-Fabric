package net.verox.arclight.item.client;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.custom.AngelSwordItem;
import net.verox.arclight.item.custom.MoonlightGlaiveItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GlaiveModel extends AnimatedGeoModel<MoonlightGlaiveItem> {
    @Override
    public Identifier getModelResource(MoonlightGlaiveItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/moonlight_glaive.geo.json");
    }

    @Override
    public Identifier getTextureResource(MoonlightGlaiveItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/item/moonlight_glaive.png");
    }

    @Override
    public Identifier getAnimationResource(MoonlightGlaiveItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }
}
