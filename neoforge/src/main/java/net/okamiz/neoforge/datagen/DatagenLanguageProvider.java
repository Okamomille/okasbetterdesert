package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;
import net.okamiz.modded_registries.ItemsRegistry;

public class DatagenLanguageProvider extends LanguageProvider {

    public DatagenLanguageProvider(PackOutput output) {
        super(output, OkasBetterDesert.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //OTHER
        add("tab.okasbetterdesert.main_tab", "Oka's Better Desert");
        add("tab.okasbetterdesert.colored_blocks_tab", "Oka's: Colored Blocks");



        // ITEMS
        add(ItemsRegistry.DRIED_CACTUS.get(), "Dried Cactus");
        add(ItemsRegistry.CACTUS_SOUP.get(), "Cactus Soup");


        // BLOCKS
        for (RegistrySupplier<Block> block : BlocksRegistry.BLOCKS) {
            add(block.get(), toDisplayName(block.getId().getPath()));
        }
    }

    private static String toDisplayName(String id) {
        StringBuilder displayName = new StringBuilder();
        for (String word : id.split("_")) {
            if (!displayName.isEmpty()) {
                displayName.append(" ");
            }
            displayName.append(Character.toUpperCase(word.charAt(0)));
            displayName.append(word.substring(1));
        }
        return displayName.toString();
    }
}
