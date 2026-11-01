package net.okamiz.neoforge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.stream.Stream;

public class DatagenBlockStateProvider extends BlockStateProvider {

    public DatagenBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OkasBetterDesert.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // BASE BLOCKS (MUST COME FIRST! -> shapes reuse their models)
        Stream.of(BlocksRegistry.allTerracotta(), BlocksRegistry.allWools(), BlocksRegistry.SANDSTONE_CUBES.stream())
                .flatMap(s -> s).forEach(b -> simpleBlockWithItem(b.get(), cubeAll(b.get())));

        BlocksRegistry.SANDSTONE_PILLAR_BLOCKS.forEach(b -> {
            axisBlock(b.get(), blockTexture(b.get()), blockTexture(b.get()).withSuffix("_top"));
            simpleBlockItem(b.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + b.getId().getPath())));
        });

        simpleBlockWithItem(BlocksRegistry.SMALL_CACTUS.get(), existing(BlocksRegistry.SMALL_CACTUS.get()));


        // SHAPES
        BlocksRegistry.STAIRS.forEach((base, stairs) -> {
            stairsBlock(stairs.get(), blockTexture(base.get()));
            simpleBlockItem(stairs.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + stairs.getId().getPath())));
        });
        BlocksRegistry.SLABS.forEach((base, slab) -> {
            slabBlock(slab.get(), blockTexture(base.get()), blockTexture(base.get()));
            simpleBlockItem(slab.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + slab.getId().getPath())));
        });
        BlocksRegistry.WALLS.forEach((base, wall) -> {
            wallBlock(wall.get(), blockTexture(base.get()));
            itemModels().wallInventory(wall.getId().getPath(), blockTexture(base.get()));
        });
        BlocksRegistry.CARPETS.forEach((wool, carpet) ->
                simpleBlockWithItem(carpet.get(), models().carpet(carpet.getId().getPath(), blockTexture(wool.get()))));
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
