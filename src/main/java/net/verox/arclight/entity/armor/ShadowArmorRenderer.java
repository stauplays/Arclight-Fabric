package net.verox.arclight.entity.armor;

import net.verox.arclight.item.ArclightArmorItem;
import net.verox.arclight.item.ShadowArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShadowArmorRenderer extends GeoArmorRenderer<ShadowArmorItem> {
    public ShadowArmorRenderer() {
        super(new ShadowArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoots";
        this.leftBootBone = "armorLeftBoots";

    }
}
