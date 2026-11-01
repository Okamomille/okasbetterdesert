package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    public DatagenRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        BlocksRegistry.TERRACOTTA.values().forEach(b ->
                stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, b.get(), Blocks.TERRACOTTA));

        BlocksRegistry.COLORED_TERRACOTTA.values().forEach(byColor -> byColor.forEach((color, b) ->
                stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, b.get(), BlocksRegistry.vanillaTerracotta(color))));
    }
}
