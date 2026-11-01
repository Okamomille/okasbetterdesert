package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    public DatagenRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        BlocksRegistry.TERRACOTTA.values().forEach(b -> {
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, b.get(), Blocks.TERRACOTTA);
            shapes(output, b, Blocks.TERRACOTTA);
        });

        BlocksRegistry.COLORED_TERRACOTTA.values().forEach(byColor -> byColor.forEach((color, b) -> {
            Block vanilla = BlocksRegistry.dyed(color, "terracotta");
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, b.get(), vanilla);
            shapes(output, b, vanilla);
        }));

        BlocksRegistry.WOOLS.forEach((pattern, byColor) -> byColor.forEach((color, wool) -> {
            Block vanilla = BlocksRegistry.dyed(color, "wool");
            boolean royal = pattern.equals("royal");
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wool.get(), royal ? 8 : 4)
                    .pattern(royal ? "XXX" : " X ")
                    .pattern("XSX")
                    .pattern(royal ? "XXX" : " X ")
                    .define('X', vanilla)
                    .define('S', Items.STRING)
                    .unlockedBy(getHasName(vanilla), has(vanilla)).save(output);
            carpet(output, BlocksRegistry.CARPETS.get(wool).get(), wool.get());
        }));

    }

    private void shapes(RecipeOutput out, RegistrySupplier<Block> base, Block vanilla) {
        var stairs = BlocksRegistry.TERRACOTTA_STAIRS.get(base);
        var slab = BlocksRegistry.TERRACOTTA_SLABS.get(base);
        if (stairs == null) return; // chiseled
        stairBuilder(stairs.get(), Ingredient.of(base.get())).unlockedBy(getHasName(base.get()), has(base.get())).save(out);
        slab(out, RecipeCategory.BUILDING_BLOCKS, slab.get(), base.get());
        for (ItemLike from : List.of(base.get(), vanilla)) {
            stonecutterResultFromBase(out, RecipeCategory.BUILDING_BLOCKS, stairs.get(), from);
            stonecutterResultFromBase(out, RecipeCategory.BUILDING_BLOCKS, slab.get(), from, 2);
        }
    }
}
