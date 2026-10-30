package net.okamiz.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.okamiz.OkasBetterDesert;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> SCULPTED_TERRACOTTA = registerBlock("sculpted_terracotta", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA), CreativeTabsRegistry.MAIN_TAB );
    public static final RegistrySupplier<Block> POLISHED_TERRACOTTA = registerBlock("polished_terracotta", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA), CreativeTabsRegistry.MAIN_TAB );
    public static final RegistrySupplier<Block> TERRACOTTA_BRICKS = registerBlock("terracotta_bricks", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA), CreativeTabsRegistry.MAIN_TAB );
    public static final RegistrySupplier<Block> TILED_TERRACOTTA = registerBlock("tiled_terracotta", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA), CreativeTabsRegistry.MAIN_TAB );






    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                      Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {

        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> factory.apply(properties.get()));
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().arch$tab(creativeTab)));
        return toReturn;
    }
}
