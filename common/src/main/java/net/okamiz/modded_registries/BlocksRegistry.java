package net.okamiz.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.okamiz.OkasBetterDesert;
import net.okamiz.blocks.custom.SmallCactusBlock;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.BLOCK);

    private static final List<String> TERRACOTTA_VARIANTS =
            List.of("chiseled_terracotta", "polished_terracotta", "terracotta_bricks", "tiled_terracotta");

    public static final Map<String, RegistrySupplier<Block>> TERRACOTTA = new LinkedHashMap<>();
    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> COLORED_TERRACOTTA = new LinkedHashMap<>();

    private static final List<DyeColor> COLOR_ORDER = List.of(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE,
            DyeColor.YELLOW, DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);
    static {
        // Registration order = creative tab order: uncolored first, then grouped by color
        TERRACOTTA_VARIANTS.forEach(v -> TERRACOTTA.put(v, terracotta(v, Blocks.TERRACOTTA)));
        for (DyeColor color : COLOR_ORDER){
            for (String variant : TERRACOTTA_VARIANTS) {
                COLORED_TERRACOTTA.computeIfAbsent(variant, v ->
                        new EnumMap<>(DyeColor.class)).put(color, terracotta(color.getName() + "_" + variant, vanillaTerracotta(color)));
            }
        }

    }

    public static final RegistrySupplier<Block> SMALL_CACTUS = registerBlock("small_cactus", SmallCactusBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS).noOcclusion(), CreativeTabsRegistry.MAIN_TAB );

















    /** Vanilla colored terracotta for a dye color, e.g. minecraft:red_terracotta. */
    public static Block vanillaTerracotta(DyeColor color) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_terracotta"));
    }

    /** Every terracotta block of the mod (uncolored + colored), for datagen loops. */
    public static Stream<RegistrySupplier<Block>> allTerracotta() {
        return Stream.concat(TERRACOTTA.values().stream(), COLORED_TERRACOTTA.values().stream().flatMap(m -> m.values().stream()));
    }


    private static RegistrySupplier<Block> terracotta(String name, Block base) {
        return registerBlock(name, Block::new, () -> BlockBehaviour.Properties.ofFullCopy(base), CreativeTabsRegistry.COLORED_BLOCKS_TAB);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                      Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> factory.apply(properties.get()));
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().arch$tab(creativeTab)));
        return toReturn;
    }
}
