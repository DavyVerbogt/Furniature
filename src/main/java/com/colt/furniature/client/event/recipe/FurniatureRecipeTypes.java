package com.colt.furniature.client.event.recipe;

import com.colt.furniature.Furniature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class FurniatureRecipeTypes {
    public static final ResourceLocation FURNIATURE_RECIPE_LOC = new ResourceLocation(Furniature.MOD_ID, "furniature_making");
    public static RecipeType<FurniatureRecipe> FURNIATURE_RECIPE = RecipeType.register(FURNIATURE_RECIPE_LOC.toString());

    public static void init() {
    }
}
