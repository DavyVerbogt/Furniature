package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.client.event.recipe.ColtMultiItem;
import com.colt.furniature.client.event.recipe.FurniatureRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class FurniatureRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Furniature.MOD_ID);

    private static <T extends RecipeSerializer<? extends Recipe<?>>> RegistryObject<T> register(String name, Supplier<T> serializer) {
        return RECIPE_SERIALIZER.register(name, serializer);
    }

    public static final RegistryObject<RecipeSerializer<FurniatureRecipe>> FURNIATURE_CRAFTING = RECIPE_SERIALIZER.register("furniature_making",
            () -> new ColtMultiItem.Serializer<FurniatureRecipe>(FurniatureRecipe::new) {
            }
    );


}