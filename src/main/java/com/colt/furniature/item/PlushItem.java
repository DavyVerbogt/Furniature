package com.colt.furniature.item;

import com.colt.furniature.client.render.ItemStackRenderer;
import com.colt.furniature.itemgroup.FurniatureItemGroup;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class PlushItem extends BlockItem {

    public PlushItem(Block block, Properties tab) {
        super(block, new Item.Properties().tab(FurniatureItemGroup.FURNIATURE));
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        ItemStackRenderer plushItemRender = new ItemStackRenderer();
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                //Furniature.LOGGER.info("But does it get here?");
                return plushItemRender;
            }
        });
    }
}