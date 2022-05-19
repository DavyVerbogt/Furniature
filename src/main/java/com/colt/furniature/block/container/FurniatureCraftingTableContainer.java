package com.colt.furniature.block.container;

import com.colt.furniature.client.event.recipe.FurniatureRecipe;
import com.colt.furniature.client.event.recipe.FurniatureRecipeTypes;
import com.colt.furniature.registries.FurniatureBlocks;
import com.colt.furniature.registries.FurniatureContainers;
import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class FurniatureCraftingTableContainer extends AbstractContainerMenu {

    public static int AmountOfSlots = 2;
    final List<Slot> inputSlots = Lists.newArrayList();
    final Slot outputSlot;
    private final ContainerLevelAccess access;
    private final Level level;
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final ItemStack itemStackInput = ItemStack.EMPTY;
    private final NonNullList<ItemStack> inputItem = NonNullList.withSize(AmountOfSlots, ItemStack.EMPTY);
    private final ResultContainer resultContainer = new ResultContainer();
    private List<FurniatureRecipe> recipes = Lists.newArrayList();
    private long lastOnTake;
    private Runnable inventoryUpdateListener = () -> {
    };
    public final Container inputContainer = new SimpleContainer(AmountOfSlots) {
        public void setChanged() {
            super.setChanged();
            FurniatureCraftingTableContainer.this.slotsChanged(this);
            FurniatureCraftingTableContainer.this.inventoryUpdateListener.run();
        }
    };

    public FurniatureCraftingTableContainer(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public FurniatureCraftingTableContainer(int syncId, Inventory playerInventory, final ContainerLevelAccess posCallable) {
        super(FurniatureContainers.TABLE_SAW.get(), syncId);
        this.access = posCallable;
        this.level = playerInventory.player.level;

        for (int i = 0; i < AmountOfSlots; i++) {
            int rowIndex = i;

            int x = 20;
            int y = 21 + rowIndex * 22;
            this.inputSlots.add(
                    this.addSlot(new Slot(this.inputContainer, i, x, y))
            );
        }

        this.outputSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player thePlayer, ItemStack stack) {
                boolean SlotEmpty = false;
                List<Slot> InventorySlots = FurniatureCraftingTableContainer.this.inputSlots;
                for (int i = 0; i < InventorySlots.size(); i++) {
                    final Slot InputSlot = InventorySlots.get(i);

                    if (!InputSlot.getItem().isEmpty()) {
                        if (InputSlot.remove(1).isEmpty()) {
                            SlotEmpty = true;
                        }
                    }
                }
                if (!SlotEmpty) {
                    FurniatureCraftingTableContainer.this.updateRecipeResultSlot();
                }

                stack.getItem().onCraftedBy(stack, thePlayer.level, thePlayer);
                access.execute((world, pos) -> {
                    long l = world.getGameTime();
                    if (FurniatureCraftingTableContainer.this.lastOnTake != l) {
                        world.playSound(null, pos, SoundEvents.VILLAGER_WORK_SHEPHERD, SoundSource.BLOCKS, 1.0f, 1.0f);
                        FurniatureCraftingTableContainer.this.lastOnTake = l;
                    }
                });
                super.onTake(thePlayer, stack);
            }
        });

        // player's inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // player's hotbar
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<FurniatureRecipe> getRecipeList() {
        return this.recipes;
    }

    public int getRecipeListSize() {
        return this.recipes.size();
    }

    public boolean hasItemsInInputSlot() {
        return this.inputSlots.stream().anyMatch(Slot::hasItem) && !this.recipes.isEmpty();
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(this.access, playerIn, FurniatureBlocks.TABLE_SAW.get());
    }

    @Override
    public boolean clickMenuButton(Player playerIn, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipe.set(id);
            this.updateRecipeResultSlot();
        }
        return true;
    }

    public boolean isValidRecipeIndex(int n) {
        return n >= 0 && n < this.getRecipeListSize();
    }

    @Override
    public void slotsChanged(Container inv) {
        Boolean SlotChanged = false;
        List<Slot> slots = this.inputSlots;
        for (int i = 0; i < slots.size(); i++) {
            final Slot InputSlot = slots.get(i);
            ItemStack itemStack = this.inputItem.get(i);

            if (InputSlot.getItem().getItem() != itemStack.getItem()) {
                SlotChanged = true;
            }
        }
        if (SlotChanged) {
            List<ItemStack> Stacks = new ArrayList<>();
            for (int i = 0; i < slots.size(); i++) {
                final ItemStack itemStack = this.inputSlots.get(i).getItem();
                Stacks.add(itemStack);
                this.inputItem.set(i, itemStack.copy());
            }
            this.updateAvailableRecipes(inv, Stacks);
        }
    }

    private void updateAvailableRecipes(Container inv, List<ItemStack> stack) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(FurniatureRecipeTypes.FURNIATURE_RECIPE, inv, this.level);
        }
    }

    public void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipe.get())) {
            FurniatureRecipe recipe = this.recipes.get(this.selectedRecipe.get());
            this.outputSlot.set(recipe.assemble(this.inputContainer));
        } else {
            this.outputSlot.set(ItemStack.EMPTY);
        }
        this.broadcastChanges();
    }

    @Override
    public MenuType<?> getType() {
        return FurniatureContainers.TABLE_SAW.get();
    }

    public void setInventoryUpdateListener(Runnable listenerIn) {
        this.inventoryUpdateListener = listenerIn;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            Item item = itemStack1.getItem();
            itemStack = itemStack1.copy();
            if (index == 1) {
                item.onCraftedBy(itemStack1, playerIn.level, playerIn);
                if (!this.moveItemStackTo(itemStack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemStack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(FurniatureRecipeTypes.FURNIATURE_RECIPE, new SimpleContainer(itemStack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemStack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemStack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStack1);
            this.broadcastChanges();
        }

        return itemStack;
    }

    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((world, pos) -> {
            this.clearContainer(playerIn, this.inputContainer);
        });
    }
}
