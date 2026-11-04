package net.okamiz.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.okamiz.OkasBetterDesert;

import java.util.List;

public class PlacedFeatures {

    public static final ResourceKey<PlacedFeature> TINY_CACTUS_PATCH_PLACED = registerKey("tiny_cactus_patch_placed");
    public static final ResourceKey<PlacedFeature> SMALL_CACTUS_PATCH_PLACED = registerKey("small_cactus_patch_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, TINY_CACTUS_PATCH_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatures.TINY_CACTUS_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, SMALL_CACTUS_PATCH_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatures.SMALL_CACTUS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
