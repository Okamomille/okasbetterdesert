package net.okamiz.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DatagenLanguageProvider extends LanguageProvider {

    public DatagenLanguageProvider(PackOutput output) {
        super(output, OkasBetterDesert.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tab.okasbetterdesert.main_tab", "Oka's Better Desert");
        add("tab.okasbetterdesert.colored_blocks_tab", "Oka's: Colored Blocks");

        BlocksRegistry.BLOCKS.forEach(b -> add(b.get(), titleCase(b.getId().getPath())));
    }

    private static String titleCase(String id) {
        return Arrays.stream(id.split("_")).map(w -> Character.toUpperCase(w.charAt(0)) + w.substring(1)).collect(Collectors.joining(" "));
    }
}
