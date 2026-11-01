package net.okamiz.modded_registries;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.okamiz.OkasBetterDesert;

public class CreativeTabsRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.CREATIVE_MODE_TAB);


    public static final RegistrySupplier<CreativeModeTab> MAIN_TAB = TABS.register("main_tab", () ->
            CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("tab.okasbetterdesert.main_tab"));
                builder.icon(() -> new ItemStack(Items.CACTUS));
            }));

    public static final RegistrySupplier<CreativeModeTab> COLORED_BLOCKS_TAB = TABS.register("colored_blocks_tab", () ->
            CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("tab.okasbetterdesert.colored_blocks_tab"));
                builder.icon(() -> new ItemStack(Items.CACTUS));
            }));
}
