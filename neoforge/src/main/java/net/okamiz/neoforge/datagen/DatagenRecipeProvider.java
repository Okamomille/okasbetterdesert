package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    private RecipeOutput output;

    public DatagenRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }


    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        this.output = recipeOutput;




    }
}