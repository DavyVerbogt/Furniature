package com.colt.furniature.datagen;

import com.colt.furniature.Furniature;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.colt.furniature.registries.FurniatureBlocks.BLOCK;

public class ItemModelGenerator extends ItemModelProvider {


    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Furniature.MOD_ID, existingFileHelper);
    }

    protected void SimpleBlockItem(Item item, String string) {
        getBuilder(item.getRegistryName().toString()).parent(getExistingFile(modLoc("block/" + item.getRegistryName().getPath() + string)));
    }

    protected void OneLayerItem(Item item, ResourceLocation Texture) {
        ResourceLocation ItemTexture = new ResourceLocation(Texture.getNamespace(), "item/" + Texture.getPath());
        if (existingFileHelper.exists(ItemTexture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", ItemTexture);
        } else {
            Furniature.LOGGER.error("Could not find texture for " + item.getRegistryName().toString() + " at " + ItemTexture);
        }
    }

    @Override
    protected void registerModels() {
        BLOCK.getEntries().forEach(blocks -> {
            Furniature.LOGGER.info(blocks.get().asItem()+" :Item Model Generated");
            if (String.valueOf(blocks.get().asItem()).contains("cabinet")) {
                SimpleBlockItem(blocks.get().asItem(), "_empty");
            }
            else if (String.valueOf(blocks.get().asItem()).contains("plush")) {}
            else {
                SimpleBlockItem(blocks.get().asItem(), "");
            }

        });
    }
}