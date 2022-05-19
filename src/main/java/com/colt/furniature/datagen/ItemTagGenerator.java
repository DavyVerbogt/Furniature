package com.colt.furniature.datagen;

import com.colt.furniature.Furniature;
import com.colt.furniature.registries.FurniatureBlocks;
import com.colt.furniature.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGenerator extends ItemTagsProvider {

    public ItemTagGenerator(DataGenerator Generator, BlockTagsProvider TagsProvider, ExistingFileHelper existingFileHelper) {
        super(Generator, TagsProvider, Furniature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Items.CRAFTING_TAB).add(FurniatureBlocks.TABLE_SAW.get().asItem());

        copy(ModTags.Blocks.CHAIR, ModTags.Items.CHAIRS_TAB);
        copy(ModTags.Blocks.STAIR, ModTags.Items.STAIR_TAB);
        copy(ModTags.Blocks.MODERN_TABLE, ModTags.Items.MODERN_TABLE_TAB);
        copy(ModTags.Blocks.CABINET, ModTags.Items.CABINET_TAB);
    }

    @Override
    public String getName() {
        return "Furnature Mod Item Tags";
    }
}
