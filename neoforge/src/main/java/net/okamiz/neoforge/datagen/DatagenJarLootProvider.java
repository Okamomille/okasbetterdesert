package net.okamiz.neoforge.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.okamiz.modded_registries.ItemsRegistry;
import net.okamiz.modded_registries.LootTablesRegistry;

import java.util.function.BiConsumer;

public class DatagenJarLootProvider implements LootTableSubProvider {

    public DatagenJarLootProvider(HolderLookup.Provider registries) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        LootPool.Builder jarContent = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 2))

                .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6))))

                .add(LootItem.lootTableItem(ItemsRegistry.DRIED_CACTUS.get()).setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))

                .add(LootItem.lootTableItem(Items.BONE).setWeight(8))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(8))
                .add(LootItem.lootTableItem(Items.STRING).setWeight(8))
                .add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD).setWeight(3))
                .add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD).setWeight(3))

                .add(LootItem.lootTableItem(Items.ARROW).setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))

                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(3))
                .add(potion(Potions.FIRE_RESISTANCE, 1))
                .add(potion(Potions.HEALING, 1))
                .add(potion(Potions.REGENERATION, 1))

                .add(EmptyLootItem.emptyItem().setWeight(5));

        output.accept(LootTablesRegistry.DESERT_JAR, LootTable.lootTable().withPool(jarContent));
    }

    private LootPoolSingletonContainer.Builder<?> potion(Holder<Potion> potion, int weight) {
        return LootItem.lootTableItem(Items.POTION)
                .setWeight(weight)
                .apply(SetPotionFunction.setPotion(potion));
    }
}
