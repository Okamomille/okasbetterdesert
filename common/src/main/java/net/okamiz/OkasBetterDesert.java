package net.okamiz;

import net.okamiz.modded_registries.BlocksRegistry;
import net.okamiz.modded_registries.CreativeTabsRegistry;
import net.okamiz.modded_registries.ItemsRegistry;

public final class OkasBetterDesert {
    public static final String MOD_ID = "okasbetterdesert";

    public static void init() {


        BlocksRegistry.BLOCKS.register();
        ItemsRegistry.ITEMS.register();
        CreativeTabsRegistry.TABS.register();
    }
}
