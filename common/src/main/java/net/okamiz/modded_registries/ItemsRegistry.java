package net.okamiz.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.okamiz.OkasBetterDesert;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> DRIED_CACTUS = registerItem("dried_cactus", Item::new,
            () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build()).arch$tab(CreativeTabsRegistry.MAIN_TAB));

    public static final RegistrySupplier<Item> CACTUS_SOUP = registerItem("cactus_soup", Item::new,
            () -> new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).usingConvertsTo(Items.BOWL).build())
                    .arch$tab(CreativeTabsRegistry.MAIN_TAB));



    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Function<Item.Properties, T> factory, Supplier<Item.Properties> properties) {

        return ITEMS.register(name, () -> factory.apply(properties.get()));
    }
}
