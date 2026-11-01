package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    public DatagenRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        BlocksRegistry.CUT_FROM.forEach((block, base) -> {
            cut(output, block.get(), base, 1);
            shapes(output, block, base);
        });

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

    private static void cut(RecipeOutput out, ItemLike result, ItemLike from, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(from), has(from))
                .save(out, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, getConversionRecipeName(result, from) + "_stonecutting"));
    }

    private void shapes(RecipeOutput out, RegistrySupplier<? extends Block> base, Block vanilla) {
        var stairs = BlocksRegistry.STAIRS.get(base);
        var slab = BlocksRegistry.SLABS.get(base);
        var wall = BlocksRegistry.WALLS.get(base);
        if (wall != null) {
            wall(out, RecipeCategory.DECORATIONS, wall.get(), base.get());
            for (ItemLike from : List.of(base.get(), vanilla)) cut(out, wall.get(), from, 1);
        }
        if (stairs == null) {
            return; // chiseled
        }
        stairBuilder(stairs.get(), Ingredient.of(base.get())).unlockedBy(getHasName(base.get()), has(base.get())).save(out);
        slab(out, RecipeCategory.BUILDING_BLOCKS, slab.get(), base.get());
        for (ItemLike from : List.of(base.get(), vanilla)) {
            cut(out, stairs.get(), from, 1);
            cut(out, slab.get(), from, 2);
        }
    }
}
