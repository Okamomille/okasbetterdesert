package net.okamiz.modded_registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.okamiz.OkasBetterDesert;

public class LootTablesRegistry {

    public static final ResourceKey<LootTable> DESERT_JAR = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(OkasBetterDesert.MOD_ID, "jars/desert_jar"));
}
