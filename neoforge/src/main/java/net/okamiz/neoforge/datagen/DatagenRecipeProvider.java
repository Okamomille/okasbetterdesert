package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;
import net.okamiz.modded_registries.ItemsRegistry;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    public DatagenRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        /* STONECUTTER BLOCKS (terracotta, sandstone) */
        for (RegistrySupplier<? extends Block> block : BlocksRegistry.STONECUTTER_INGREDIENTS.keySet()) {
            Block vanillaBlock = BlocksRegistry.STONECUTTER_INGREDIENTS.get(block);
            stonecutterRecipe(output, block.get(), vanillaBlock, 1);
            shapeRecipes(output, block, vanillaBlock);
        }

        /* WOOLS AND CARPETS */
        Map<DyeColor, RegistrySupplier<Block>> royalWools = BlocksRegistry.WOOLS.get("royal");
        Map<DyeColor, RegistrySupplier<Block>> tiledWools = BlocksRegistry.WOOLS.get("tiled");
        for (DyeColor color : royalWools.keySet()) {
            Block vanillaWool = BlocksRegistry.getVanillaDyedBlock(color, "wool");
            royalWoolRecipe(output, royalWools.get(color).get(), vanillaWool);
            tiledWoolRecipe(output, tiledWools.get(color).get(), vanillaWool);
        }
        for (RegistrySupplier<Block> wool : BlocksRegistry.CARPETS.keySet()) {
            carpet(output, BlocksRegistry.CARPETS.get(wool).get(), wool.get());
        }

        /* CACTUS WOOD */
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlocksRegistry.CACTUS_PLANKS.get(), 2)
                .pattern("##")
                .pattern("##")
                .define('#', Blocks.CACTUS)
                .unlockedBy(getHasName(Blocks.CACTUS), has(Blocks.CACTUS))
                .save(output);

        BlockFamily cactusFamily = new BlockFamily.Builder(BlocksRegistry.CACTUS_PLANKS.get())
                .stairs(BlocksRegistry.CACTUS_STAIRS.get())
                .slab(BlocksRegistry.CACTUS_SLAB.get())
                .fence(BlocksRegistry.CACTUS_FENCE.get())
                .fenceGate(BlocksRegistry.CACTUS_FENCE_GATE.get())
                .door(BlocksRegistry.CACTUS_DOOR.get())
                .trapdoor(BlocksRegistry.CACTUS_TRAPDOOR.get())
                .button(BlocksRegistry.CACTUS_BUTTON.get())
                .pressurePlate(BlocksRegistry.CACTUS_PRESSURE_PLATE.get())
                .recipeGroupPrefix("wooden")
                .getFamily();
        generateRecipes(output, cactusFamily, FeatureFlags.VANILLA_SET);


        /* FOOD */
        driedCactusSmelting(output, BlocksRegistry.SMALL_CACTUS.get());
        driedCactusSmelting(output, BlocksRegistry.TINY_CACTUS.get());
        driedCactusSmokingAndCampfire(output, Blocks.CACTUS);
        driedCactusSmokingAndCampfire(output, BlocksRegistry.SMALL_CACTUS.get());
        driedCactusSmokingAndCampfire(output, BlocksRegistry.TINY_CACTUS.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemsRegistry.CACTUS_SOUP.get())
                .pattern("XXX")
                .pattern(" B ")
                .define('X', ItemsRegistry.DRIED_CACTUS.get())
                .define('B', Items.BOWL)
                .unlockedBy(getHasName(ItemsRegistry.DRIED_CACTUS.get()), has(ItemsRegistry.DRIED_CACTUS.get()))
                .save(output);


        /* DECO & MISC */

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlocksRegistry.JAR.get())
                .pattern(" X ")
                .pattern("X X")
                .pattern("XXX")
                .define('X', Blocks.TERRACOTTA)
                .unlockedBy(getHasName(Blocks.TERRACOTTA), has(Blocks.TERRACOTTA))
                .save(output);
    }

    private void driedCactusSmelting(RecipeOutput output, ItemLike cactus) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(cactus), RecipeCategory.FOOD, ItemsRegistry.DRIED_CACTUS.get(), 0.35f, 200)
                .unlockedBy(getHasName(cactus), has(cactus))
                .save(output, modRecipe("dried_cactus_from_smelting_" + getItemName(cactus)));
    }

    private void driedCactusSmokingAndCampfire(RecipeOutput output, ItemLike cactus) {
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(cactus), RecipeCategory.FOOD, ItemsRegistry.DRIED_CACTUS.get(), 0.35f, 100)
                .unlockedBy(getHasName(cactus), has(cactus))
                .save(output, modRecipe("dried_cactus_from_smoking_" + getItemName(cactus)));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(cactus), RecipeCategory.FOOD, ItemsRegistry.DRIED_CACTUS.get(), 0.35f, 600)
                .unlockedBy(getHasName(cactus), has(cactus))
                .save(output, modRecipe("dried_cactus_from_campfire_cooking_" + getItemName(cactus)));
    }

    private static ResourceLocation modRecipe(String name) {
        return ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, name);
    }

    private void shapeRecipes(RecipeOutput output, RegistrySupplier<? extends Block> base, Block vanillaBlock) {
        RegistrySupplier<StairBlock> stairs = BlocksRegistry.STAIRS.get(base);
        RegistrySupplier<SlabBlock> slab = BlocksRegistry.SLABS.get(base);
        RegistrySupplier<WallBlock> wall = BlocksRegistry.WALLS.get(base);

        if (stairs != null) {
            stairBuilder(stairs.get(), Ingredient.of(base.get()))
                    .unlockedBy(getHasName(base.get()), has(base.get()))
                    .save(output);
            stonecutterRecipe(output, stairs.get(), base.get(), 1);
            stonecutterRecipe(output, stairs.get(), vanillaBlock, 1);
        }

        if (slab != null) {
            slab(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), base.get());
            stonecutterRecipe(output, slab.get(), base.get(), 2);
            stonecutterRecipe(output, slab.get(), vanillaBlock, 2);
        }

        if (wall != null) {
            wall(output, RecipeCategory.DECORATIONS, wall.get(), base.get());
            stonecutterRecipe(output, wall.get(), base.get(), 1);
            stonecutterRecipe(output, wall.get(), vanillaBlock, 1);
        }
    }

    private void royalWoolRecipe(RecipeOutput output, Block royalWool, Block vanillaWool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, royalWool, 8)
                .pattern("XXX")
                .pattern("XSX")
                .pattern("XXX")
                .define('X', vanillaWool)
                .define('S', Items.STRING)
                .unlockedBy(getHasName(vanillaWool), has(vanillaWool))
                .save(output);
    }

    private void tiledWoolRecipe(RecipeOutput output, Block tiledWool, Block vanillaWool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tiledWool, 4)
                .pattern(" X ")
                .pattern("XSX")
                .pattern(" X ")
                .define('X', vanillaWool)
                .define('S', Items.STRING)
                .unlockedBy(getHasName(vanillaWool), has(vanillaWool))
                .save(output);
    }

    private static void stonecutterRecipe(RecipeOutput output, ItemLike result, ItemLike ingredient, int count) {
        String recipeName = getConversionRecipeName(result, ingredient) + "_stonecutting";
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, recipeName));
    }
}
