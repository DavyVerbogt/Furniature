package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.block.container.FurniatureCraftingTableContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FurniatureContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            Furniature.MOD_ID);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String key, MenuType.MenuSupplier<T> supplier) {
        MenuType<T> type = new MenuType<>(supplier);
        return CONTAINER.register(key, () -> type);
    }

    public static final RegistryObject<MenuType<FurniatureCraftingTableContainer>> TABLE_SAW = CONTAINER.register("table_saw", () -> new MenuType<>(FurniatureCraftingTableContainer::new));


}
