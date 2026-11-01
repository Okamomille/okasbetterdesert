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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.okamiz.OkasBetterDesert;
import net.okamiz.blocks.custom.SmallCactusBlock;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.BLOCK);

    private static final List<String> TERRACOTTA_VARIANTS = List.of("chiseled_terracotta", "polished_terracotta", "terracotta_bricks", "tiled_terracotta");

    private static final List<String> WOOL_PATTERNS = List.of("royal", "tiled");

    public static final Map<String, RegistrySupplier<Block>> TERRACOTTA = new LinkedHashMap<>();
    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> COLORED_TERRACOTTA = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<StairBlock>> TERRACOTTA_STAIRS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<SlabBlock>> TERRACOTTA_SLABS = new LinkedHashMap<>();

    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> WOOLS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<WoolCarpetBlock>> CARPETS = new LinkedHashMap<>();


    private static final List<DyeColor> COLOR_ORDER = List.of(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE,
            DyeColor.YELLOW, DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);



    static {
        TERRACOTTA_VARIANTS.forEach(v -> TERRACOTTA.put(v, terracotta(v, Blocks.TERRACOTTA)));

        for (DyeColor color : COLOR_ORDER){
            for (String variant : TERRACOTTA_VARIANTS) {
                COLORED_TERRACOTTA.computeIfAbsent(variant, v ->
                        new EnumMap<>(DyeColor.class)).put(color, terracotta(color.getName() + "_" + variant, dyed(color,"terracotta")));
            }
        }

        for (DyeColor color : COLOR_ORDER) {
            for (String pattern : WOOL_PATTERNS) {
                WOOLS.computeIfAbsent(pattern, p -> new EnumMap<>(DyeColor.class)).put(color, wool(color, pattern));
            }
        }
        for (DyeColor color : COLOR_ORDER) {
            for (String pattern : WOOL_PATTERNS) {
                CARPETS.put(WOOLS.get(pattern).get(color), carpet(color, pattern));
            }
        }
    }




    // --------------------------- REGULAR BLOCK REGISTERS -------------------------------------------------------------------

    public static final RegistrySupplier<Block> SMALL_CACTUS = registerBlock("small_cactus", SmallCactusBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS).noOcclusion(), CreativeTabsRegistry.MAIN_TAB );






    // --------------------------- EXTERNS METHODS -------------------------------------------------------------------


    private static RegistrySupplier<Block> wool(DyeColor color, String pattern) {
        return registerBlock(color.getName() + "_" + pattern + "_wool", p -> flammable(new Block(p), 30, 60),
                () -> BlockBehaviour.Properties.ofFullCopy(dyed(color, "wool")), CreativeTabsRegistry.COLORED_BLOCKS_TAB);
    }

    private static RegistrySupplier<WoolCarpetBlock> carpet(DyeColor color, String pattern) {
        return registerBlock(color.getName() + "_" + pattern + "_carpet", p -> flammable(new WoolCarpetBlock(color, p), 60, 20),
                () -> BlockBehaviour.Properties.ofFullCopy(dyed(color, "carpet")), CreativeTabsRegistry.COLORED_BLOCKS_TAB);
    }

    private static <T extends Block> T flammable(T block, int igniteOdds, int burnOdds) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, igniteOdds, burnOdds);
        return block;
    }

    public static Stream<RegistrySupplier<Block>> allWools() {
        return WOOLS.values().stream().flatMap(m -> m.values().stream());
    }


    /** Vanilla dyed block, e.g. dyed(RED, "wool") -> minecraft:red_wool. */
    public static Block dyed(DyeColor color, String suffix) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_" + suffix));
    }

    /** Every terracotta block of the mod (uncolored + colored), for datagen loops. */
    public static Stream<RegistrySupplier<Block>> allTerracotta() {
        return Stream.concat(TERRACOTTA.values().stream(), COLORED_TERRACOTTA.values().stream().flatMap(m -> m.values().stream()));
    }


    private static RegistrySupplier<Block> terracotta(String name, Block base) {
        Supplier<BlockBehaviour.Properties> props = () -> BlockBehaviour.Properties.ofFullCopy(base);
        RegistrySupplier<Block> block = registerBlock(name, Block::new, props, CreativeTabsRegistry.COLORED_BLOCKS_TAB);
        if (!name.contains("chiseled")) {
            String prefix = name.endsWith("bricks") ? name.substring(0, name.length() - 1) : name;
            TERRACOTTA_STAIRS.put(block, registerBlock(prefix + "_stairs", p -> new StairBlock(block.get().defaultBlockState(), p),
                    props, CreativeTabsRegistry.COLORED_BLOCKS_TAB));
            TERRACOTTA_SLABS.put(block, registerBlock(prefix + "_slab", SlabBlock::new, props, CreativeTabsRegistry.COLORED_BLOCKS_TAB));
        }
        return block;
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                      Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> factory.apply(properties.get()));
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().arch$tab(creativeTab)));
        return toReturn;
    }
}
