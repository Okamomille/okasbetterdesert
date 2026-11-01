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
        BlocksRegistry.TERRACOTTA.values().forEach(b -> {
            cut(output, b.get(), Blocks.TERRACOTTA, 1);
            shapes(output, b, Blocks.TERRACOTTA);
        });

        BlocksRegistry.COLORED_TERRACOTTA.values().forEach(byColor -> byColor.forEach((color, b) -> {
            Block vanilla = BlocksRegistry.dyed(color, "terracotta");
            cut(output, b.get(), vanilla, 1);
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

    private static void cut(RecipeOutput out, ItemLike result, ItemLike from, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(from), has(from))
                .save(out, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, getConversionRecipeName(result, from) + "_stonecutting"));
    }

    private void shapes(RecipeOutput out, RegistrySupplier<Block> base, Block vanilla) {
        var stairs = BlocksRegistry.TERRACOTTA_STAIRS.get(base);
        var slab = BlocksRegistry.TERRACOTTA_SLABS.get(base);
        if (stairs == null) return; // chiseled
        stairBuilder(stairs.get(), Ingredient.of(base.get())).unlockedBy(getHasName(base.get()), has(base.get())).save(out);
        slab(out, RecipeCategory.BUILDING_BLOCKS, slab.get(), base.get());
        for (ItemLike from : List.of(base.get(), vanilla)) {
            cut(out, stairs.get(), from, 1);
            cut(out, slab.get(), from, 2);
        }
    }
}
