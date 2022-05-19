package com.colt.furniature.client;

import com.colt.furniature.client.gui.FurniatureCraftingTableScreen;
import com.colt.furniature.client.render.ItemStackRenderer;
import com.colt.furniature.client.render.PlushBlockRenderer;
import com.colt.furniature.client.render.SittingRender;
import com.colt.furniature.registries.FurniatureBlockEntities;
import com.colt.furniature.registries.FurniatureBlocks;
import com.colt.furniature.registries.FurniatureContainers;
import com.colt.furniature.registries.FurniatureEntities;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

public class ClientStuff {

    public static void setup() {
        EntityRenderers();
        BlockEntityRender();
        Screen();
        Layers();
    }

    private static void EntityRenderers() {
        EntityRenderers.register(FurniatureEntities.SEAT.get(), SittingRender::new);
    }

    private static void BlockEntityRender() {
        BlockEntityRenderers.register(FurniatureBlockEntities.PLUSH.get(), PlushBlockRenderer::new);
    }


    private static void Screen() {
        MenuScreens.register(FurniatureContainers.TABLE_SAW.get(), FurniatureCraftingTableScreen::new);
    }

    private static void Layers() {
        Predicate<RenderType> CutOut = renderType -> renderType == RenderType.cutout();
        Predicate<RenderType> Translucent = renderType -> renderType == RenderType.translucent();
        int i = 0;
        for (RegistryObject<Block> block : FurniatureBlocks.CHAIR) {
            ItemBlockRenderTypes.setRenderLayer(FurniatureBlocks.CHAIR.get(i).get(), CutOut);
            i++;
        }
        i = 0;
        for (RegistryObject<Block> block : FurniatureBlocks.STAINED_GLASS_MODERN_TABLE) {
            ItemBlockRenderTypes.setRenderLayer(FurniatureBlocks.STAINED_GLASS_MODERN_TABLE.get(i).get(), Translucent);
            i++;
        }
    }
}

