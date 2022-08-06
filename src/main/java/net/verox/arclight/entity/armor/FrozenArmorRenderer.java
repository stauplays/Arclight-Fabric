package net.verox.arclight.entity.armor;

import net.verox.arclight.item.FrozenArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class FrozenArmorRenderer extends GeoArmorRenderer<FrozenArmorItem> {
    public FrozenArmorRenderer() {
        super(new FrozenArmorModel());

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
