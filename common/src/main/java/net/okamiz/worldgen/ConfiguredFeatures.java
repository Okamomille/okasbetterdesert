package net.okamiz.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.BlocksRegistry;

public class ConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> TINY_CACTUS_PATCH = registerKey("tiny_cactus_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_CACTUS_PATCH = registerKey("small_cactus_patch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, TINY_CACTUS_PATCH, Feature.RANDOM_PATCH, plantPatch(BlocksRegistry.TINY_CACTUS.get(), 8));
        register(context, SMALL_CACTUS_PATCH, Feature.RANDOM_PATCH, plantPatch(BlocksRegistry.SMALL_CACTUS.get(), 5));
    }

    private static RandomPatchConfiguration plantPatch(Block plant, int tries) {
        SimpleBlockConfiguration placeThePlant = new SimpleBlockConfiguration(BlockStateProvider.simple(plant));
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, placeThePlant));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
