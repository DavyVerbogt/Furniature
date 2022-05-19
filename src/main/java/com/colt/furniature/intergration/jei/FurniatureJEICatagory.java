package com.colt.furniature.intergration.jei;

import com.colt.furniature.client.event.recipe.FurniatureRecipe;
import com.colt.furniature.client.event.recipe.FurniatureRecipeTypes;
import com.colt.furniature.registries.FurniatureBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FurniatureJEICatagory implements IRecipeCategory<FurniatureRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    private final int Input_Slot1 = 0;
    private final int Input_Slot2 = 1;
    private final int Output_Slot = 2;

    public FurniatureJEICatagory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(FurniatureJEI.JEI_UI, 0, 0, 82, 39);
        icon = guiHelper.createDrawableIngredient(new ItemStack(FurniatureBlocks.TABLE_SAW.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return FurniatureRecipeTypes.FURNIATURE_RECIPE_LOC;
    }

    @Override
    public Class<? extends FurniatureRecipe> getRecipeClass() {
        return FurniatureRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("furniature_making.table_saw");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(FurniatureRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FurniatureRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
        stackGroup.init(Input_Slot1, true, 0, 0);
        stackGroup.init(Input_Slot2, true, 0, 21);
        stackGroup.init(Output_Slot, false, 60, 11);
        stackGroup.set(ingredients);
    }
}
