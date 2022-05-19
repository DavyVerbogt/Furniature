package com.colt.furniature.itemgroup;

import com.colt.furniature.registries.FurniatureBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;


public class FurniatureItemGroup extends CreativeModeTab {

    public static final FurniatureItemGroup FURNIATURE = new FurniatureItemGroup(TABS.length, "furniature");


    public FurniatureItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(FurniatureBlocks.TABLE_SAW.get());
    }

}
