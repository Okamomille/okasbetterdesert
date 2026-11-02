package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
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
        for (Block block : getKnownBlocks()) {
            if (block instanceof SlabBlock slab) {
                add(slab, createSlabItemTable(slab));
            } else if (block instanceof DoorBlock door) {
                add(door, createDoorTable(door));
            } else {
                dropSelf(block);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> block : BlocksRegistry.BLOCKS) {
            blocks.add(block.get());
        }
        return blocks;
    }
}
