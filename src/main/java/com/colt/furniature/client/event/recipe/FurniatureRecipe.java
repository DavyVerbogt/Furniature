package com.colt.furniature.client.event.recipe;

import com.colt.furniature.registries.FurniatureBlocks;
import com.colt.furniature.registries.FurniatureRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class FurniatureRecipe extends ColtMultiItem {

    public FurniatureRecipe(ResourceLocation rl, String s, Ingredient ing1, Ingredient ing2, ItemStack stack) {
        super(FurniatureRecipeTypes.FURNIATURE_RECIPE, FurniatureRecipeSerializer.FURNIATURE_CRAFTING.get(), rl, s, ing1, ing2, stack);
    }

    @Override
    public boolean matches(Container inv, Level l) {
        boolean ContainsItems = false;
        ContainsItems = this.Ingredient1.test(inv.getItem(0)) && this.Ingredient2.test(inv.getItem(1));
        return ContainsItems;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(FurniatureBlocks.TABLE_SAW.get());
    }
}