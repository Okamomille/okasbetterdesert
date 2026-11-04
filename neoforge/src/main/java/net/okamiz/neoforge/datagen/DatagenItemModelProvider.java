package net.okamiz.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.ItemsRegistry;


public class DatagenItemModelProvider extends ItemModelProvider {

    public DatagenItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OkasBetterDesert.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
       flat(ItemsRegistry.DRIED_CACTUS.get());
       flat(ItemsRegistry.CACTUS_SOUP.get());


    }

    private void flat(Item item) {
        basicItem(item);
    }
}