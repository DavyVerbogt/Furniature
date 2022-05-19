package com.colt.furniature.util;

import com.colt.furniature.Furniature;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    private static TagKey<Item> ItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Furniature.MOD_ID, name));
    }

    private static TagKey<Block> BlockTag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Furniature.MOD_ID, name));
    }

    public static class Items {
        public static final TagKey<Item> CRAFTING_TAB = ItemTag("crafting_tab");
        public static final TagKey<Item> STAIR_TAB = ItemTag("stairs");
        public static final TagKey<Item> CHAIRS_TAB = ItemTag("chairs");
        public static final TagKey<Item> MODERN_TABLE_TAB = ItemTag("modern_tables");
        public static final TagKey<Item> CABINET_TAB = ItemTag("cabinets");
    }

    public static class Blocks {
        public static final TagKey<Block> CHAIR = BlockTag("chairs");
        public static final TagKey<Block> STAIR = BlockTag("stairs");
        public static final TagKey<Block> MODERN_TABLE = BlockTag("modern_tables");
        public static final TagKey<Block> CABINET = BlockTag("cabinets");
    }
}