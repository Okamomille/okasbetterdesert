package net.okamiz.neoforge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
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
        /* SIMPLE CUBES */
        BlocksRegistry.allTerracotta().forEach(b -> simpleBlockWithItem(b.get(), cubeAll(b.get())));

        ModelFile small_cactus = existing(BlocksRegistry.SMALL_CACTUS.get());
        simpleBlockWithItem(BlocksRegistry.SMALL_CACTUS.get(), small_cactus);

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
