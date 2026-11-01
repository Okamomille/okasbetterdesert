package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DatagenBlockTagsProvider extends BlockTagsProvider {
    public DatagenBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OkasBetterDesert.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        BlocksRegistry.allTerracotta().forEach(b -> {
            tag(BlockTags.TERRACOTTA).add(b.getKey());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(b.getKey());
        });
    }
}
