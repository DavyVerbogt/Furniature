package com.colt.furniature.intergration.jei;

import com.colt.furniature.Furniature;
import com.colt.furniature.client.event.recipe.FurniatureRecipe;
import com.colt.furniature.client.event.recipe.FurniatureRecipeTypes;
import com.colt.furniature.registries.FurniatureBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;

@JeiPlugin
public class FurniatureJEI implements IModPlugin {
    public static final ResourceLocation JEI_UI = new ResourceLocation(Furniature.MOD_ID, "textures/gui/jei.png");
    private static final ResourceLocation UID = new ResourceLocation(Furniature.MOD_ID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new FurniatureJEICatagory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        Collection<FurniatureRecipe> recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(FurniatureRecipeTypes.FURNIATURE_RECIPE);
        registration.addRecipes(Arrays.asList(recipes.toArray()), FurniatureRecipeTypes.FURNIATURE_RECIPE_LOC);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(new ItemStack(FurniatureBlocks.TABLE_SAW.get()), new ResourceLocation(Furniature.MOD_ID, "furniature_making"));
    }
}
