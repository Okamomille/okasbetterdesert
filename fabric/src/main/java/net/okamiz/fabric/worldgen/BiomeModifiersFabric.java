package net.okamiz.fabric.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.okamiz.modded_registries.TagsRegistry;
import net.okamiz.worldgen.PlacedFeatures;

public class BiomeModifiersFabric {

    public static void generateModWorldGen() {
        BiomeModifications.addFeature(BiomeSelectors.tag(TagsRegistry.HAS_CACTUS_PATCHES),
                GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.TINY_CACTUS_PATCH_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(TagsRegistry.HAS_CACTUS_PATCHES),
                GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SMALL_CACTUS_PATCH_PLACED);
    }
}
