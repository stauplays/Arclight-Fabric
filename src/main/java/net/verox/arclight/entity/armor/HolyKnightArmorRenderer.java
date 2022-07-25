package net.verox.arclight.entity.armor;

import net.verox.arclight.item.ArclightArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class HolyKnightArmorRenderer extends GeoArmorRenderer<ArclightArmorItem> {
    public HolyKnightArmorRenderer() {
        super(new HolyKnightArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorLeftArm";
        this.leftArmBone = "armorRightArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoots";
        this.leftBootBone = "armorRightBoots";

    }
}
