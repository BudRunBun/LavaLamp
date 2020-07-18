package com.budrunbun.lavalamp.containers;


import com.budrunbun.lavalamp.containers.slots.InputSlot;
import com.budrunbun.lavalamp.containers.slots.OutputSlot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import net.minecraft.util.IIntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CheeseGeneratorContainer extends Container {

    private final IItemHandler generatorInventory, playerInventory;
    private final IIntArray generatorData;
    private boolean flag = true;

    public CheeseGeneratorContainer(int windowId, PlayerInventory playerInventory, IItemHandler generatorInventory, IIntArray data) {
        super(ModContainers.CHEESE_GENERATOR_CONTAINER, windowId);
        assertIntArraySize(data, 2);
        this.generatorInventory = generatorInventory;
        this.playerInventory = new InvWrapper(playerInventory);
        this.generatorData = data;

        this.addSlot(new InputSlot(Items.MILK_BUCKET, generatorInventory, 0, 12, 58));

        this.addSlot(new InputSlot(Items.ACACIA_FENCE, generatorInventory, 1, 84, 81));

        this.addSlot(new InputSlot(Items.WATER_BUCKET, generatorInventory, 2, 156, 58));

        this.addSlot(new OutputSlot(generatorInventory, 3, 84, 13, playerInventory.player));


        int leftCol = (184 - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new SlotItemHandler(this.playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, 184 - (4 - playerInvRow) * 18 - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new SlotItemHandler(this.playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, 184 - 24));
        }

        this.trackIntArray(data);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (stack.getItem() == Items.MILK_BUCKET) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    public int getMilkCapacityScaled() {
        return this.generatorData.get(0) * 49 / 8;
    }

    public int getWaterCapacityScaled() {
        return this.generatorData.get(1) * 49 / 8;
    }

    public int getLeftAndRightArrowProgressionScaled() {
        if (this.generatorData.get(2) <= 200) {
            return this.generatorData.get(2) * 50 / 200;
        } else
            return 50;
    }

    public int getDownArrowProgressionScaled() {
        if (this.generatorData.get(2) <= 200) {
            return this.generatorData.get(2) * 7 / 200;
        } else
            return 7;
    }

    public int getUpArrowProgressionScaled() {
        if (this.generatorData.get(2) >= 300) {
            return (this.generatorData.get(2) - 300) * 18 / 100;
        } else
            return -1;
    }

    public boolean isWorking() {
        return this.generatorData.get(2) > 0;
    }

    public int getFanXPosition() {
        if (this.generatorData.get(2) >= 200) {
            if (flag) {
                flag = !flag;
                return 0;
            } else {
                flag = !flag;
                return 27;
            }
        } else {
            return 0;
        }
    }

}