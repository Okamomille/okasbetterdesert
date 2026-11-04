package net.okamiz.neoforge.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.okamiz.OkasBetterDesert;
import net.okamiz.modded_registries.TagsRegistry;
import net.okamiz.worldgen.PlacedFeatures;

public class BiomeModifiersNeoForge {

    public static final ResourceKey<BiomeModifier> ADD_CACTUS_PATCHES = registerKey("add_cactus_patches");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        HolderSet<PlacedFeature> cactusPatches = HolderSet.direct(
                placedFeatures.getOrThrow(PlacedFeatures.TINY_CACTUS_PATCH_PLACED),
                placedFeatures.getOrThrow(PlacedFeatures.SMALL_CACTUS_PATCH_PLACED));

        context.register(ADD_CACTUS_PATCHES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.HAS_CACTUS_PATCHES), cactusPatches, GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, name));
    }
}
