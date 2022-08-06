package net.verox.arclight.item.client;

import net.verox.arclight.item.custom.AngelSwordItem;
import net.verox.arclight.item.custom.MoonlightGlaiveItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GlaiveRenderer extends GeoItemRenderer<MoonlightGlaiveItem> {
    public GlaiveRenderer() {
        super(new GlaiveModel());
    }
}
