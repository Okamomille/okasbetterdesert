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
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.okamiz.OkasBetterDesert;
import net.okamiz.blocks.custom.SmallCactusBlock;
import net.okamiz.blocks.custom.TinyCactusBlock;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OkasBetterDesert.MOD_ID, Registries.BLOCK);

    private static final List<DyeColor> COLOR_ORDER = List.of(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE,
            DyeColor.YELLOW, DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);

    private static final List<String> TERRACOTTA_VARIANTS = List.of("chiseled_terracotta", "polished_terracotta", "terracotta_bricks", "tiled_terracotta");
    private static final List<String> WOOL_PATTERNS = List.of("royal", "tiled");

    public static final Map<String, RegistrySupplier<Block>> TERRACOTTA = new LinkedHashMap<>();
    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> COLORED_TERRACOTTA = new LinkedHashMap<>();

    public static final Map<String, Map<DyeColor, RegistrySupplier<Block>>> WOOLS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<WoolCarpetBlock>> CARPETS = new LinkedHashMap<>();

    public static final List<RegistrySupplier<Block>> SANDSTONE_CUBES = new ArrayList<>();
    public static final List<RegistrySupplier<RotatedPillarBlock>> SANDSTONE_PILLARS = new ArrayList<>();

    public static final Map<RegistrySupplier<Block>, RegistrySupplier<StairBlock>> STAIRS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<SlabBlock>> SLABS = new LinkedHashMap<>();
    public static final Map<RegistrySupplier<Block>, RegistrySupplier<WallBlock>> WALLS = new LinkedHashMap<>();

    public static final Map<RegistrySupplier<? extends Block>, Block> STONECUTTER_INGREDIENTS = new LinkedHashMap<>();


    // REGISTERING BLOCKS IN THE INCONVENTIONNAL WAY (Ask @Okamiz for details)
    static {
        registerTerracotta();
        registerWoolsAndCarpets();
        registerSandstone();
    }


    // --------------------------- REGULAR BLOCK REGISTERS -------------------------------------------------------------------

    public static final RegistrySupplier<Block> SMALL_CACTUS = registerBlock("small_cactus", SmallCactusBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS).noOcclusion(), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<Block> TINY_CACTUS = registerBlock("tiny_cactus", TinyCactusBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).offsetType(BlockBehaviour.OffsetType.XZ), CreativeTabsRegistry.MAIN_TAB);

    /* CACTUS WOOD */

    public static final RegistrySupplier<Block> CACTUS_PLANKS = registerBlock("cactus_planks",
            props -> makeFlammable(new Block(props), 5, 20), copyOf(Blocks.BAMBOO_PLANKS), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<StairBlock> CACTUS_STAIRS = registerBlock("cactus_stairs",
            props -> makeFlammable(new StairBlock(CACTUS_PLANKS.get().defaultBlockState(), props), 5, 20), copyOf(Blocks.BAMBOO_STAIRS), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<SlabBlock> CACTUS_SLAB = registerBlock("cactus_slab",
            props -> makeFlammable(new SlabBlock(props), 5, 20), copyOf(Blocks.BAMBOO_SLAB), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<FenceBlock> CACTUS_FENCE = registerBlock("cactus_fence",
            props -> makeFlammable(new FenceBlock(props), 5, 20), copyOf(Blocks.BAMBOO_FENCE), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<FenceGateBlock> CACTUS_FENCE_GATE = registerBlock("cactus_fence_gate",
            props -> makeFlammable(new FenceGateBlock(WoodType.BAMBOO, props), 5, 20), copyOf(Blocks.BAMBOO_FENCE_GATE), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<DoorBlock> CACTUS_DOOR = registerBlock("cactus_door",
            props -> new DoorBlock(BlockSetType.BAMBOO, props), copyOf(Blocks.BAMBOO_DOOR), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<TrapDoorBlock> CACTUS_TRAPDOOR = registerBlock("cactus_trapdoor",
            props -> new TrapDoorBlock(BlockSetType.BAMBOO, props), copyOf(Blocks.BAMBOO_TRAPDOOR), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<ButtonBlock> CACTUS_BUTTON = registerBlock("cactus_button",
            props -> new ButtonBlock(BlockSetType.BAMBOO, 30, props), copyOf(Blocks.BAMBOO_BUTTON), CreativeTabsRegistry.MAIN_TAB);

    public static final RegistrySupplier<PressurePlateBlock> CACTUS_PRESSURE_PLATE = registerBlock("cactus_pressure_plate",
            props -> new PressurePlateBlock(BlockSetType.BAMBOO, props), copyOf(Blocks.BAMBOO_PRESSURE_PLATE), CreativeTabsRegistry.MAIN_TAB);


    // --------------------------- BLOCK FAMILIES -------------------------------------------------------------------

    private static void registerTerracotta() {
        for (String variant : TERRACOTTA_VARIANTS) {
            TERRACOTTA.put(variant, registerTerracottaBlock(variant, Blocks.TERRACOTTA));
            COLORED_TERRACOTTA.put(variant, new EnumMap<>(DyeColor.class));
        }

        for (DyeColor color : COLOR_ORDER) {
            for (String variant : TERRACOTTA_VARIANTS) {
                String name = color.getName() + "_" + variant;
                Block vanillaTerracotta = getVanillaDyedBlock(color, "terracotta");
                COLORED_TERRACOTTA.get(variant).put(color, registerTerracottaBlock(name, vanillaTerracotta));
            }
        }
    }

    private static RegistrySupplier<Block> registerTerracottaBlock(String name, Block vanillaTerracotta) {
        Supplier<BlockBehaviour.Properties> properties = copyOf(vanillaTerracotta);
        RegistrySupplier<Block> block = registerBlock(name, Block::new, properties, CreativeTabsRegistry.COLORED_BLOCKS_TAB);
        STONECUTTER_INGREDIENTS.put(block, vanillaTerracotta);

        if (!name.contains("chiseled")) {
            registerStairsAndSlab(name, block, properties, CreativeTabsRegistry.COLORED_BLOCKS_TAB);
        }
        return block;
    }

    private static void registerWoolsAndCarpets() {
        for (String pattern : WOOL_PATTERNS) {
            WOOLS.put(pattern, new EnumMap<>(DyeColor.class));
        }

        for (DyeColor color : COLOR_ORDER) {
            for (String pattern : WOOL_PATTERNS) {
                WOOLS.get(pattern).put(color, registerWool(color, pattern));
            }
        }

        for (DyeColor color : COLOR_ORDER) {
            for (String pattern : WOOL_PATTERNS) {
                RegistrySupplier<Block> wool = WOOLS.get(pattern).get(color);
                CARPETS.put(wool, registerCarpet(color, pattern));
            }
        }
    }

    private static RegistrySupplier<Block> registerWool(DyeColor color, String pattern) {
        String name = color.getName() + "_" + pattern + "_wool";
        return registerBlock(name, props -> makeFlammable(new Block(props), 30, 60),
                copyOf(getVanillaDyedBlock(color, "wool")), CreativeTabsRegistry.COLORED_BLOCKS_TAB);
    }

    private static RegistrySupplier<WoolCarpetBlock> registerCarpet(DyeColor color, String pattern) {
        String name = color.getName() + "_" + pattern + "_carpet";
        return registerBlock(name, props -> makeFlammable(new WoolCarpetBlock(color, props), 60, 20),
                copyOf(getVanillaDyedBlock(color, "carpet")), CreativeTabsRegistry.COLORED_BLOCKS_TAB);
    }

    private static void registerSandstone() {
        registerSandstoneFamily("sandstone", Blocks.SANDSTONE);
        registerSandstoneFamily("red_sandstone", Blocks.RED_SANDSTONE);
    }

    private static void registerSandstoneFamily(String stone, Block vanillaSandstone) {
        Supplier<BlockBehaviour.Properties> properties = copyOf(vanillaSandstone);

        List<String> surfaces = List.of(stone + "_bricks", "tiled_" + stone, "small_" + stone + "_tiles", "engraved_" + stone);
        for (String name : surfaces) {
            RegistrySupplier<Block> block = registerBlock(name, Block::new, properties, CreativeTabsRegistry.MAIN_TAB);
            SANDSTONE_CUBES.add(block);
            STONECUTTER_INGREDIENTS.put(block, vanillaSandstone);
            registerStairsAndSlab(name, block, properties, CreativeTabsRegistry.MAIN_TAB);
            registerWall(name, block, properties, CreativeTabsRegistry.MAIN_TAB);
        }

        RegistrySupplier<Block> framed = registerBlock("framed_" + stone, Block::new, properties, CreativeTabsRegistry.MAIN_TAB);
        SANDSTONE_CUBES.add(framed);
        STONECUTTER_INGREDIENTS.put(framed, vanillaSandstone);

        List<String> pillars = List.of(stone + "_pillar", "small_tiled_" + stone + "_pillar");
        for (String name : pillars) {
            RegistrySupplier<RotatedPillarBlock> pillar = registerBlock(name, RotatedPillarBlock::new, properties, CreativeTabsRegistry.MAIN_TAB);
            SANDSTONE_PILLARS.add(pillar);
            STONECUTTER_INGREDIENTS.put(pillar, vanillaSandstone);
        }
    }

    private static void registerStairsAndSlab(String baseName, RegistrySupplier<Block> baseBlock,
                                              Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {
        String shapeName = getShapeName(baseName);

        RegistrySupplier<StairBlock> stairs = registerBlock(shapeName + "_stairs",
                props -> new StairBlock(baseBlock.get().defaultBlockState(), props), properties, creativeTab);
        RegistrySupplier<SlabBlock> slab = registerBlock(shapeName + "_slab", SlabBlock::new, properties, creativeTab);

        STAIRS.put(baseBlock, stairs);
        SLABS.put(baseBlock, slab);
    }

    private static void registerWall(String baseName, RegistrySupplier<Block> baseBlock,
                                     Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {
        RegistrySupplier<WallBlock> wall = registerBlock(getShapeName(baseName) + "_wall", WallBlock::new, properties, creativeTab);
        WALLS.put(baseBlock, wall);
    }

    private static String getShapeName(String baseName) {
        if (baseName.endsWith("_bricks") || baseName.endsWith("_tiles")) {
            return baseName.substring(0, baseName.length() - 1);
        }
        return baseName;
    }


    // --------------------------- UTILS -------------------------------------------------------------------

    public static List<RegistrySupplier<Block>> getAllTerracotta() {
        List<RegistrySupplier<Block>> blocks = new ArrayList<>(TERRACOTTA.values());
        for (Map<DyeColor, RegistrySupplier<Block>> blocksByColor : COLORED_TERRACOTTA.values()) {
            blocks.addAll(blocksByColor.values());
        }
        return blocks;
    }

    public static List<RegistrySupplier<Block>> getAllWools() {
        List<RegistrySupplier<Block>> blocks = new ArrayList<>();
        for (Map<DyeColor, RegistrySupplier<Block>> blocksByColor : WOOLS.values()) {
            blocks.addAll(blocksByColor.values());
        }
        return blocks;
    }

    public static Block getVanillaDyedBlock(DyeColor color, String suffix) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_" + suffix));
    }

    private static <T extends Block> T makeFlammable(T block, int igniteOdds, int burnOdds) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteOdds, burnOdds);
        return block;
    }

    private static Supplier<BlockBehaviour.Properties> copyOf(Block block) {
        return () -> BlockBehaviour.Properties.ofFullCopy(block);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                      Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, () -> factory.apply(properties.get()));
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().arch$tab(creativeTab)));
        return toReturn;
    }
}
