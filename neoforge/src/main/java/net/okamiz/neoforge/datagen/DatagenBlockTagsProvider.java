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
import java.util.stream.Stream;

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

        BlocksRegistry.TERRACOTTA_STAIRS.values().forEach(s -> tag(BlockTags.STAIRS).add(s.get()));
        BlocksRegistry.TERRACOTTA_SLABS.values().forEach(s -> tag(BlockTags.SLABS).add(s.get()));

        Stream.concat(BlocksRegistry.TERRACOTTA_STAIRS.values().stream(), BlocksRegistry.TERRACOTTA_SLABS.values().stream())
                .forEach(s -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(s.get()));



        BlocksRegistry.allWools().forEach(b -> tag(BlockTags.WOOL).add(b.get()));
        BlocksRegistry.CARPETS.values().forEach(c -> tag(BlockTags.WOOL_CARPETS).add(c.get()));
    }
}
