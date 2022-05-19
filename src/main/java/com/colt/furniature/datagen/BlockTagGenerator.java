package com.colt.furniature.datagen;

import com.colt.furniature.Furniature;
import com.colt.furniature.registries.FurniatureBlocks;
import com.colt.furniature.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(DataGenerator Generator, ExistingFileHelper existingFileHelper) {
        super(Generator, Furniature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        TagsProvider.TagAppender<Block> CHAIR_TAG = tag(ModTags.Blocks.CHAIR);
        FurniatureBlocks.CHAIR.stream()
                .map(RegistryObject::get)
                .forEach(CHAIR_TAG::add);

        TagsProvider.TagAppender<Block> STAIR_TAG = tag(ModTags.Blocks.STAIR);
        FurniatureBlocks.STRIPPED_STAIR.stream()
                .map(RegistryObject::get)
                .forEach(STAIR_TAG::add);

        TagsProvider.TagAppender<Block> MODERN_TABLE_TAG = tag(ModTags.Blocks.MODERN_TABLE);
        FurniatureBlocks.STAINED_GLASS_MODERN_TABLE.stream()
                .map(RegistryObject::get)
                .forEach(MODERN_TABLE_TAG::add);

        TagsProvider.TagAppender<Block> CABINET = tag(ModTags.Blocks.CABINET);
        FurniatureBlocks.CABINET.stream()
                .map(RegistryObject::get)
                .forEach(CABINET::add);

        TagsProvider.TagAppender<Block> MineableWithAxe = tag(BlockTags.MINEABLE_WITH_AXE);
        FurniatureBlocks.BLOCK.getEntries().stream()
                .filter(s -> s.get().defaultBlockState().getMaterial() == Material.WOOD || s.get().defaultBlockState().getMaterial() == Material.NETHER_WOOD)
                .map(RegistryObject::get)
                .forEach(MineableWithAxe::add);

        TagsProvider.TagAppender<Block> MineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        FurniatureBlocks.BLOCK.getEntries().stream()
                .filter(s -> s.get().defaultBlockState().getMaterial() == Material.STONE || s.get().defaultBlockState().getMaterial() == Material.METAL)
                .map(RegistryObject::get)
                .forEach(MineableWithPickaxe::add);
    }

    @Override
    public String getName() {
        return "Furnature Mod Block Tags";
    }
}
