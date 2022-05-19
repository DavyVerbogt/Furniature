package com.colt.furniature;

import com.colt.furniature.client.ClientStuff;
import com.colt.furniature.client.event.CreativeFilter;
import com.colt.furniature.client.event.PlacedCarpet;
import com.colt.furniature.client.model.FurniatureModelLayers;
import com.colt.furniature.datagen.*;
import com.colt.furniature.registries.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Furniature.MOD_ID)
public class Furniature {

    public static final String MOD_ID = "coltfurniature";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Furniature() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus(),
                forgeEventBus = MinecraftForge.EVENT_BUS;

        addRegistries(modEventBus);
        MinecraftForge.EVENT_BUS.register(PlacedCarpet.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::CommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ModelLayers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::Data);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void CommonSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CommonStuff::setup);
    }

    private void ClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientStuff::setup);
    }

    private void ModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        FurniatureModelLayers.register(event);
    }

    private void addRegistries(final IEventBus modEventBus) {
        FurniatureBlocks.BLOCK.register(modEventBus);
        FurniatureItems.ITEMS.register(modEventBus);
        FurniatureContainers.CONTAINER.register(modEventBus);
        FurniatureEntities.ENTITY.register(modEventBus);
        FurniatureBlockEntities.BLOCK_ENTITY.register(modEventBus);
        FurniatureRecipeSerializer.RECIPE_SERIALIZER.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new CreativeFilter());
    }

    public void Data(GatherDataEvent event) {
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {
            generator.addProvider(new BlockStateGenerator(generator, existingFileHelper));
            generator.addProvider(new ItemModelGenerator(generator, existingFileHelper));
        }
        if (event.includeServer()) {
            BlockTagGenerator blockTagGenerator = new BlockTagGenerator(generator, existingFileHelper);
            generator.addProvider(blockTagGenerator);
            generator.addProvider(new ItemTagGenerator(generator, blockTagGenerator, existingFileHelper));
            generator.addProvider(new LootTableGenerator(generator));
        }
    }

}
