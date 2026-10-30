package net.okamiz.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.okamiz.OkasBetterDesert;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.ITEM);





    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Function<Item.Properties, T> factory, Supplier<Item.Properties> properties) {

        return ITEMS.register(name, () -> factory.apply(properties.get()));
    }
}
