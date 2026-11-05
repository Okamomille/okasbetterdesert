package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.okamiz.blocks.custom.JarBlock;
import net.okamiz.modded_registries.BlocksRegistry;
import net.okamiz.modded_registries.LootTablesRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatagenBlockLootTableProvider extends BlockLootSubProvider {
    public DatagenBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        for (Block block : getKnownBlocks()) {
            if (block instanceof SlabBlock slab) {
                add(slab, createSlabItemTable(slab));
            } else if (block instanceof DoorBlock door) {
                add(door, createDoorTable(door));
            } else if (block instanceof JarBlock jar) {
                add(jar, createJarTable(jar));
            } else {
                dropSelf(block);
            }
        }
    }

    private LootTable.Builder createJarTable(JarBlock jar) {
        LootItemCondition.Builder hasLoot = LootItemBlockStatePropertyCondition.hasBlockStateProperties(jar)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JarBlock.HAS_LOOT, true));

        LootPool.Builder foundJarLoot = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1)).when(hasLoot).add(NestedLootTable.lootTableReference(LootTablesRegistry.DESERT_JAR));

        LootPool.Builder craftedJarItself = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1)).when(hasLoot.invert()).add(LootItem.lootTableItem(jar));

        return LootTable.lootTable().withPool(foundJarLoot).withPool(craftedJarItself);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> block : BlocksRegistry.BLOCKS) {
            blocks.add(block.get());
        }
        return blocks;
    }
}
