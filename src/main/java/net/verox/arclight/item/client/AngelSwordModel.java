package net.verox.arclight.item.client;

import net.minecraft.util.Identifier;
import net.verox.arclight.ArclightMod;
import net.verox.arclight.item.custom.AngelSwordItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class AngelSwordModel extends AnimatedGeoModel<AngelSwordItem> {

    @Override
    public Identifier getModelResource(AngelSwordItem object) {
        return new Identifier(ArclightMod.MOD_ID, "geo/angel_sword.geo.json");
    }

    @Override
    public Identifier getTextureResource(AngelSwordItem object) {
        return new Identifier(ArclightMod.MOD_ID, "textures/item/angel_sword.png");
    }

    @Override
    public Identifier getAnimationResource(AngelSwordItem animatable) {
        return new Identifier(ArclightMod.MOD_ID, "animations/armor_animation.json");
    }

}
