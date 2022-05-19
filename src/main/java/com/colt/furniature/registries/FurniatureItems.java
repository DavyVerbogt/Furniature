package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.item.PlayerPlushItem;
import com.colt.furniature.item.PlushItem;
import com.colt.furniature.itemgroup.FurniatureItemGroup;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class FurniatureItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            Furniature.MOD_ID);

    public static final RegistryObject<Item> HEATH_PLUSH = register("heath_plush", () -> new PlushItem(FurniatureBlocks.HEATH_PLUSH.get(), new Item.Properties().tab(FurniatureItemGroup.FURNIATURE)));
    public static final RegistryObject<Item> PLAYER_PLUSH = register("player_plush", () -> new PlayerPlushItem(FurniatureBlocks.PLAYER_PLUSH.get(), new Item.Properties().tab(FurniatureItemGroup.FURNIATURE)));

    private static RegistryObject<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

}