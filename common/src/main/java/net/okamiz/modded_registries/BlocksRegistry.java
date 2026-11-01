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

    public static final Map<RegistrySupplier<Block>, RegistrySupplier<StairBlock>> STAIRS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<SlabBlock>> SLABS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<WallBlock>> WALLS = new LinkedHashMap<>();

    // Stonecutter source of every cuttable block (block -> vanilla base)
    public static final Map<RegistrySupplier<? extends Block>, Block> CUT_FROM = new LinkedHashMap<>();

    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> WOOLS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<WoolCarpetBlock>> CARPETS = new LinkedHashMap<>();

    // "%s" = sandstone / red_sandstone. Surfaces get stairs, slab and wall
    private static final List<String> SANDSTONE_SURFACES = List.of("%s_bricks", "tiled_%s", "small_%s_tiles", "engraved_%s");
    private static final List<String> SANDSTONE_PILLARS = List.of("%s_pillar", "small_tiled_%s_pillar");

    public static final List<RegistrySupplier<Block>> SANDSTONE_CUBES = new ArrayList<>();
    public static final List<RegistrySupplier<RotatedPillarBlock>> SANDSTONE_PILLAR_BLOCKS = new ArrayList<>();

    private static final List<DyeColor> COLOR_ORDER = List.of(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE,
            DyeColor.YELLOW, DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);


    // REGISTERING BLOCKS IN THE INCONVENTIONNAL WAY (Ask @Okamiz for details)
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

        for (Block base : List.of(Blocks.SANDSTONE, Blocks.RED_SANDSTONE)) {
            String stone = BuiltInRegistries.BLOCK.getKey(base).getPath();
            Supplier<BlockBehaviour.Properties> props = () -> BlockBehaviour.Properties.ofFullCopy(base);
            for (String surface : SANDSTONE_SURFACES) {
                String name = surface.formatted(stone);
                RegistrySupplier<Block> block = registerBlock(name, Block::new, props, CreativeTabsRegistry.MAIN_TAB);
                SANDSTONE_CUBES.add(block);
                CUT_FROM.put(block, base);
                shapes(name, block, props, CreativeTabsRegistry.MAIN_TAB, true);
            }
            RegistrySupplier<Block> framed = registerBlock("framed_" + stone, Block::new, props, CreativeTabsRegistry.MAIN_TAB);
            SANDSTONE_CUBES.add(framed);
            CUT_FROM.put(framed, base);
            for (String pillar : SANDSTONE_PILLARS) {
                var block = registerBlock(pillar.formatted(stone), RotatedPillarBlock::new, props, CreativeTabsRegistry.MAIN_TAB);
                SANDSTONE_PILLAR_BLOCKS.add(block);
                CUT_FROM.put(block, base);
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

    // Registers stairs + slab (+ wall) right after their base block. "sandstone_bricks" -> "sandstone_brick_stairs"
    private static void shapes(String name, RegistrySupplier<Block> block, Supplier<BlockBehaviour.Properties> props, DeferredSupplier<CreativeModeTab> tab, boolean wall) {
        String prefix = name.replaceAll("(brick|tile)s$", "$1");
        STAIRS.put(block, registerBlock(prefix + "_stairs", p -> new StairBlock(block.get().defaultBlockState(), p), props, tab));
        SLABS.put(block, registerBlock(prefix + "_slab", SlabBlock::new, props, tab));
        if (wall) {
            WALLS.put(block, registerBlock(prefix + "_wall", WallBlock::new, props, tab));
        }
    }

    private static RegistrySupplier<Block> terracotta(String name, Block base) {
        Supplier<BlockBehaviour.Properties> props = () -> BlockBehaviour.Properties.ofFullCopy(base);
        RegistrySupplier<Block> block = registerBlock(name, Block::new, props, CreativeTabsRegistry.COLORED_BLOCKS_TAB);
        CUT_FROM.put(block, base);
        if (!name.contains("chiseled")) {
            shapes(name, block, props, CreativeTabsRegistry.COLORED_BLOCKS_TAB, false);
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
