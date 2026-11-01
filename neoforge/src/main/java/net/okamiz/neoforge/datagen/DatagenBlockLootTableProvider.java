package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DatagenBlockLootTableProvider extends BlockLootSubProvider {
    public DatagenBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        BlocksRegistry.allTerracotta().forEach(b -> dropSelf(b.get()));
        BlocksRegistry.STAIRS.values().forEach(s -> dropSelf(s.get()));
        BlocksRegistry.SLABS.values().forEach(s -> add(s.get(), createSlabItemTable(s.get())));

        Stream.concat(BlocksRegistry.allWools(), BlocksRegistry.CARPETS.values().stream()).forEach(b -> dropSelf(b.get()));

        BlocksRegistry.BLOCKS.forEach(b -> {
            if (b.get() instanceof SlabBlock slab) {
                add(slab, createSlabItemTable(slab));
            }
            else {
                dropSelf(b.get());
            }
        });

        dropSelf(BlocksRegistry.SMALL_CACTUS.get());
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return StreamSupport.stream(BlocksRegistry.BLOCKS.spliterator(), false).map(RegistrySupplier::get).toList();
    }
}
