package net.verox.arclight.item.client;

import net.verox.arclight.item.custom.AngelSwordItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AngelSwordRenderer extends GeoItemRenderer<AngelSwordItem> {
    public AngelSwordRenderer() {
        super(new AngelSwordModel());
    }
}
