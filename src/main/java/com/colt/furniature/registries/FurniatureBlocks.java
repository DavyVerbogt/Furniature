package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.*;
import com.colt.furniature.itemgroup.FurniatureItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class FurniatureBlocks {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Furniature.MOD_ID);
    public static final RegistryObject<Block> TABLE_SAW = register("table_saw", FurniatureCraftingTable::new);

    public static final RegistryObject<Block> HEATH_PLUSH = registerNoItem("heath_plush",  () -> new Plush(Plush.Types.HEATH, Block.Properties.of(Material.DECORATION).strength(1.0F)));
    public static final RegistryObject<Block> PLAYER_PLUSH = registerNoItem("player_plush",  () -> new PlayerPlush(Block.Properties.of(Material.DECORATION).strength(1.0F)));

    public static final List<RegistryObject<Block>> STAINED_GLASS_MODERN_TABLE = new ArrayList<>();
    public static final List<RegistryObject<Block>> CHAIR = new ArrayList<>();
    public static final List<RegistryObject<Block>> CABINET = new ArrayList<>();
    public static final List<RegistryObject<Block>> STRIPPED_STAIR = new ArrayList<>();

    public static String[] MODERN_TABLE_TYPES = {"stripped_oak_log", "stripped_spruce_log", "stripped_birch_log", "stripped_jungle_log"
            , "stripped_dark_oak_log", "stripped_acacia_log", "stripped_warped_stem", "stripped_crimson_stem",
            "purpur_block", "sandstone_top", "basalt_side"};

    public static String[] WOOD_TYPES = {"oak", "spruce", "birch", "jungle", "dark_oak", "acacia", "warped", "crimson"};

    public static HashMap<String, RegistryObject<Block>> CARPETS_ON_STARIS = new HashMap<>(16);

    static {
        Block BlockProperties = null;
        Material BlockMaterial = null;
        BlockState blockstate = null;
        SoundType BlockSound = null;

        for (DyeColor dyeColor : DyeColor.values()) {
            String color = dyeColor.getName();
            Block coloredCarpet = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(String.format("minecraft:%s_carpet", color)));
            //CARPETS_ON_STARIS.put(color, registerNoItem(String.format("%s_carpet_on_stairs", color), () -> new CarpetOnStairs(BlockBehaviour.Properties.copy(coloredCarpet).dropsLike(coloredCarpet), coloredCarpet)));
        }

        for (String wood_type : WOOD_TYPES) {
            switch (wood_type) {
                case "oak" -> BlockProperties = Blocks.OAK_PLANKS;
                case "spruce" -> BlockProperties = Blocks.SPRUCE_PLANKS;
                case "birch" -> BlockProperties = Blocks.BIRCH_PLANKS;
                case "jungle" -> BlockProperties = Blocks.JUNGLE_PLANKS;
                case "dark_oak" -> BlockProperties = Blocks.DARK_OAK_PLANKS;
                case "acacia" -> BlockProperties = Blocks.ACACIA_PLANKS;
                case "warped" -> BlockProperties = Blocks.WARPED_PLANKS;
                case "crimson" -> BlockProperties = Blocks.CRIMSON_PLANKS;
            }
            Block finalBlockProperties = BlockProperties;
            CHAIR.add(register(String.format("%s_chair", wood_type), () -> new Chair(Block.Properties.copy(finalBlockProperties))));
        }

        for (String wood_type : WOOD_TYPES) {
            CABINET.add(register(String.format("%s_cabinet", wood_type), () -> new Cabinet(Block.Properties.copy(Blocks.BARREL))));
        }

        for (String stair_type : WOOD_TYPES) {

            switch (stair_type) {
                case "oak" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.OAK_STAIRS.defaultBlockState();
                }
                case "spruce" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.SPRUCE_STAIRS.defaultBlockState();
                }
                case "birch" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.BIRCH_STAIRS.defaultBlockState();
                }
                case "jungle" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.JUNGLE_STAIRS.defaultBlockState();
                }
                case "dark_oak" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.DARK_OAK_STAIRS.defaultBlockState();
                }
                case "acacia" -> {
                    BlockMaterial = Material.WOOD;
                    blockstate = Blocks.ACACIA_STAIRS.defaultBlockState();
                }
                case "warped" -> {
                    BlockMaterial = Material.NETHER_WOOD;
                    blockstate = Blocks.WARPED_STAIRS.defaultBlockState();
                }
                case "crimson" -> {
                    BlockMaterial = Material.NETHER_WOOD;
                    blockstate = Blocks.CRIMSON_STAIRS.defaultBlockState();
                }
            }
            BlockState finalBlockstate = blockstate;
            Material finalBlockMaterial = BlockMaterial;
            STRIPPED_STAIR.add(register(String.format("stripped_%s_stairs", stair_type), () -> new StairBlock(() -> finalBlockstate, BlockBehaviour.Properties.of(finalBlockMaterial).strength(2.0f).sound(SoundType.WOOD))));
        }

        for (String modern_table_type : MODERN_TABLE_TYPES) {
            switch (modern_table_type) {
                case "sandstone_top":
                    BlockProperties = Blocks.SANDSTONE;
                    BlockSound = SoundType.STONE;
                    break;

                case "stripped_warped_stem":
                    BlockProperties = Blocks.WARPED_PLANKS;
                    BlockSound = SoundType.WOOD;
                    break;

                case "stripped_crimson_stem":
                    BlockProperties = Blocks.CRIMSON_PLANKS;
                    BlockSound = SoundType.WOOD;
                    break;

                default:
                    BlockProperties = Blocks.OAK_PLANKS;
                    BlockSound = SoundType.WOOD;
                    break;
            }
            Block finalBlockProperties = BlockProperties;
            SoundType finalBlockSound = BlockSound;
            RegistryObject<Block> block = register(String.format("modern_%s_table", modern_table_type), () -> new ModernTable(Block.Properties.copy(finalBlockProperties).sound(finalBlockSound)));

            STAINED_GLASS_MODERN_TABLE.add(block);
            for (DyeColor dyeColor : DyeColor.values()) {
                String color = dyeColor.getName();
                STAINED_GLASS_MODERN_TABLE.add(register(String.format("%s_stained_glass_modern_%s_table", color, modern_table_type), () -> new ModernTable(Block.Properties.copy(finalBlockProperties).sound(finalBlockSound))));
            }
        }
    }

    private static RegistryObject<Block> register(String Name, Supplier<Block> block) {
        return register(Name, block, new Item.Properties().tab(FurniatureItemGroup.FURNIATURE));
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties properties) {
        RegistryObject<Block> registryObject = BLOCK.register(name, block);
        FurniatureItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), properties));
        return registryObject;
    }

    private static RegistryObject<Block> registerNoItem(String name, Supplier<Block> block) {
        return BLOCK.register(name, block);
    }
}
