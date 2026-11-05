package net.okamiz.modded_registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.okamiz.OkasBetterDesert;

public class TagsRegistry {

    public static final TagKey<Biome> HAS_CACTUS_PATCHES = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, "has_feature/cactus_patches"));

    public static final TagKey<Biome> HAS_DESERT_DUNGEON = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, "has_structure/desert_dungeon"));
}
