package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatagenBlockLootTableProvider extends BlockLootSubProvider {
    public DatagenBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BlocksRegistry.SCULPTED_TERRACOTTA.get());
        dropSelf(BlocksRegistry.TILED_TERRACOTTA.get());
        dropSelf(BlocksRegistry.POLISHED_TERRACOTTA.get());
        dropSelf(BlocksRegistry.TERRACOTTA_BRICKS.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> supplier : BlocksRegistry.BLOCKS) {
            blocks.add(supplier.get());
        }
        return blocks;
    }
}