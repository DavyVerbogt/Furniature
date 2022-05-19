package com.colt.furniature.client.event.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;


public abstract class ColtMultiItem implements Recipe<Container> {


    protected final ItemStack result;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient Ingredient1;
    protected final Ingredient Ingredient2;
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;

    public ColtMultiItem(RecipeType<?> Type, RecipeSerializer<?> Serializer, ResourceLocation resourceLocation, String Group, Ingredient ingredient1, Ingredient ingredient2, ItemStack Result) {
        this.type = Type;
        this.serializer = Serializer;
        this.id = resourceLocation;
        this.group = Group;
        this.Ingredient1 = ingredient1;
        this.Ingredient2 = ingredient2;

        this.result = Result;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getGroup() {
        return this.group;
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.Ingredient1);
        nonnulllist.add(this.Ingredient2);
        return nonnulllist;
    }

    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    public ItemStack assemble(Container p_77572_1_) {
        return this.result.copy();
    }

    public static class Serializer<T extends ColtMultiItem> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
        final IRecipeFactory<T> factory;

        public Serializer(IRecipeFactory<T> fac) {
            this.factory = fac;
        }

        public T fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            String s = GsonHelper.getAsString(p_199425_2_, "group", "");
            Ingredient ingredient1;
            Ingredient ingredient2;
            if (GsonHelper.isArrayNode(p_199425_2_, "ingredient")) {
                ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonArray(p_199425_2_, "ingredient1"));
                ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonArray(p_199425_2_, "ingredient2"));
            } else {
                ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_199425_2_, "ingredient1"));
                ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_199425_2_, "ingredient2"));
            }

            String s1 = GsonHelper.getAsString(p_199425_2_, "result");
            int i = GsonHelper.getAsInt(p_199425_2_, "count");
            ItemStack itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s1)), i);
            return this.factory.create(p_199425_1_, s, ingredient1, ingredient2, itemstack);
        }

        public T fromNetwork(ResourceLocation p_199426_1_, FriendlyByteBuf p_199426_2_) {
            String s = p_199426_2_.readUtf(32767);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_199426_2_);
            Ingredient ingredient2 = Ingredient.fromNetwork(p_199426_2_);
            ItemStack itemstack = p_199426_2_.readItem();
            return this.factory.create(p_199426_1_, s, ingredient1, ingredient2, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_199427_1_, T p_199427_2_) {
            p_199427_1_.writeUtf(p_199427_2_.group);
            p_199427_2_.Ingredient1.toNetwork(p_199427_1_);
            p_199427_2_.Ingredient2.toNetwork(p_199427_1_);
            p_199427_1_.writeItem(p_199427_2_.result);
        }

        public interface IRecipeFactory<T extends ColtMultiItem> {
            T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient p_create_3_, Ingredient p_create_3_1, ItemStack p_create_4_);
        }
    }
}

