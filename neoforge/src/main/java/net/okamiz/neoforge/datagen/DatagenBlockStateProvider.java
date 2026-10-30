package net.okamiz.neoforge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;


public class DatagenBlockStateProvider extends BlockStateProvider {

    public DatagenBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OkasBetterDesert.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        /* SIMPLE CUBES */
        simpleBlockWithItem(BlocksRegistry.SCULPTED_TERRACOTTA.get(), cubeAll(BlocksRegistry.SCULPTED_TERRACOTTA.get()));
        simpleBlockWithItem(BlocksRegistry.TILED_TERRACOTTA.get(), cubeAll(BlocksRegistry.TILED_TERRACOTTA.get()));
        simpleBlockWithItem(BlocksRegistry.POLISHED_TERRACOTTA.get(), cubeAll(BlocksRegistry.POLISHED_TERRACOTTA.get()));
        simpleBlockWithItem(BlocksRegistry.TERRACOTTA_BRICKS.get(), cubeAll(BlocksRegistry.TERRACOTTA_BRICKS.get()));


    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private ModelFile existing(Block block) {
        return models().getExistingFile(modLoc("block/" + name(block)));
    }

    private void crossBlock(Block block) {
        String name = name(block);
        simpleBlock(block, models().cross(name, modLoc("block/" + name)));
        // The item uses its own flat texture (textures/item/<name>.png).
        itemModels().basicItem(block.asItem());
    }

}