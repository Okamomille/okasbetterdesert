package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;

public class DatagenBlockStateProvider extends BlockStateProvider {

    public DatagenBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OkasBetterDesert.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        /* BASE BLOCKS (MUST COME FIRST! -> shapes reuse their models) */
        for (RegistrySupplier<Block> block : BlocksRegistry.getAllTerracotta()) {
            cubeAllWithItem(block.get());
        }
        for (RegistrySupplier<Block> block : BlocksRegistry.getAllWools()) {
            cubeAllWithItem(block.get());
        }
        for (RegistrySupplier<Block> block : BlocksRegistry.SANDSTONE_CUBES) {
            cubeAllWithItem(block.get());
        }
        for (RegistrySupplier<RotatedPillarBlock> pillar : BlocksRegistry.SANDSTONE_PILLARS) {
            pillarWithItem(pillar.get());
        }
        cubeAllWithItem(BlocksRegistry.CACTUS_PLANKS.get());

        simpleBlockWithItem(BlocksRegistry.SMALL_CACTUS.get(), existing(BlocksRegistry.SMALL_CACTUS.get()));

        /* SHAPES */
        for (RegistrySupplier<Block> base : BlocksRegistry.STAIRS.keySet()) {
            stairsWithItem(BlocksRegistry.STAIRS.get(base).get(), base.get());
        }
        for (RegistrySupplier<Block> base : BlocksRegistry.SLABS.keySet()) {
            slabWithItem(BlocksRegistry.SLABS.get(base).get(), base.get());
        }
        for (RegistrySupplier<Block> base : BlocksRegistry.WALLS.keySet()) {
            wallWithItem(BlocksRegistry.WALLS.get(base).get(), base.get());
        }
        for (RegistrySupplier<Block> wool : BlocksRegistry.CARPETS.keySet()) {
            carpetWithItem(BlocksRegistry.CARPETS.get(wool).get(), wool.get());
        }

        /* CACTUS WOOD */
        Block cactusPlanks = BlocksRegistry.CACTUS_PLANKS.get();
        ResourceLocation planksTexture = blockTexture(cactusPlanks);

        stairsWithItem(BlocksRegistry.CACTUS_STAIRS.get(), cactusPlanks);
        slabWithItem(BlocksRegistry.CACTUS_SLAB.get(), cactusPlanks);

        fenceBlock(BlocksRegistry.CACTUS_FENCE.get(), planksTexture);
        itemModels().fenceInventory(name(BlocksRegistry.CACTUS_FENCE.get()), planksTexture);

        fenceGateBlock(BlocksRegistry.CACTUS_FENCE_GATE.get(), planksTexture);
        blockItem(BlocksRegistry.CACTUS_FENCE_GATE.get(), "");

        buttonBlock(BlocksRegistry.CACTUS_BUTTON.get(), planksTexture);
        itemModels().buttonInventory(name(BlocksRegistry.CACTUS_BUTTON.get()), planksTexture);

        pressurePlateBlock(BlocksRegistry.CACTUS_PRESSURE_PLATE.get(), planksTexture);
        blockItem(BlocksRegistry.CACTUS_PRESSURE_PLATE.get(), "");

        doorBlockWithRenderType(BlocksRegistry.CACTUS_DOOR.get(), modLoc("block/cactus_door_bottom"), modLoc("block/cactus_door_top"), "cutout");
        itemModels().basicItem(BlocksRegistry.CACTUS_DOOR.get().asItem());

        trapdoorBlockWithRenderType(BlocksRegistry.CACTUS_TRAPDOOR.get(), blockTexture(BlocksRegistry.CACTUS_TRAPDOOR.get()), true, "cutout");
        blockItem(BlocksRegistry.CACTUS_TRAPDOOR.get(), "_bottom");
    }

    private void cubeAllWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void pillarWithItem(RotatedPillarBlock pillar) {
        ResourceLocation sideTexture = blockTexture(pillar);
        ResourceLocation topTexture = sideTexture.withSuffix("_top");
        axisBlock(pillar, sideTexture, topTexture);
        blockItem(pillar, "");
    }

    private void stairsWithItem(StairBlock stairs, Block base) {
        stairsBlock(stairs, blockTexture(base));
        blockItem(stairs, "");
    }

    private void slabWithItem(SlabBlock slab, Block base) {
        slabBlock(slab, blockTexture(base), blockTexture(base));
        blockItem(slab, "");
    }

    private void wallWithItem(WallBlock wall, Block base) {
        wallBlock(wall, blockTexture(base));
        itemModels().wallInventory(name(wall), blockTexture(base));
    }

    private void carpetWithItem(WoolCarpetBlock carpet, Block wool) {
        simpleBlockWithItem(carpet, models().carpet(name(carpet), blockTexture(wool)));
    }

    private void blockItem(Block block, String modelSuffix) {
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + modelSuffix)));
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private void crossBlock(Block block) {
        String name = name(block);
        simpleBlock(block, models().cross(name, modLoc("block/" + name)).renderType("cutout"));
        itemModels().basicItem(block.asItem());
    }

    private ModelFile existing(Block block) {
        return models().getExistingFile(modLoc("block/" + name(block)));
    }
}
