package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
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
        /* TERRACOTTA */
        for (RegistrySupplier<Block> block : BlocksRegistry.getAllTerracotta()) {
            tag(BlockTags.TERRACOTTA).add(block.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
        }

        /* SANDSTONE */
        for (RegistrySupplier<Block> block : BlocksRegistry.SANDSTONE_CUBES) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
        }
        for (RegistrySupplier<RotatedPillarBlock> pillar : BlocksRegistry.SANDSTONE_PILLARS) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(pillar.get());
        }

        /* STONE SHAPES */
        for (RegistrySupplier<StairBlock> stairs : BlocksRegistry.STAIRS.values()) {
            tag(BlockTags.STAIRS).add(stairs.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(stairs.get());
        }
        for (RegistrySupplier<SlabBlock> slab : BlocksRegistry.SLABS.values()) {
            tag(BlockTags.SLABS).add(slab.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(slab.get());
        }
        for (RegistrySupplier<WallBlock> wall : BlocksRegistry.WALLS.values()) {
            tag(BlockTags.WALLS).add(wall.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(wall.get());
        }

        /* WOOLS */
        for (RegistrySupplier<Block> wool : BlocksRegistry.getAllWools()) {
            tag(BlockTags.WOOL).add(wool.get());
        }
        for (RegistrySupplier<WoolCarpetBlock> carpet : BlocksRegistry.CARPETS.values()) {
            tag(BlockTags.WOOL_CARPETS).add(carpet.get());
        }

        /* CACTUS WOOD */
        tag(BlockTags.PLANKS).add(BlocksRegistry.CACTUS_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(BlocksRegistry.CACTUS_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(BlocksRegistry.CACTUS_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(BlocksRegistry.CACTUS_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(BlocksRegistry.CACTUS_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(BlocksRegistry.CACTUS_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(BlocksRegistry.CACTUS_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS).add(BlocksRegistry.CACTUS_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlocksRegistry.CACTUS_PRESSURE_PLATE.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlocksRegistry.CACTUS_PLANKS.get())
                .add(BlocksRegistry.CACTUS_STAIRS.get())
                .add(BlocksRegistry.CACTUS_SLAB.get())
                .add(BlocksRegistry.CACTUS_FENCE.get())
                .add(BlocksRegistry.CACTUS_FENCE_GATE.get())
                .add(BlocksRegistry.CACTUS_DOOR.get())
                .add(BlocksRegistry.CACTUS_TRAPDOOR.get())
                .add(BlocksRegistry.CACTUS_BUTTON.get())
                .add(BlocksRegistry.CACTUS_PRESSURE_PLATE.get());
    }
}
