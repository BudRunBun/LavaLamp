package com.budrunbun.lavalamp.containers;


import com.budrunbun.lavalamp.containers.slots.InputSlot;
import com.budrunbun.lavalamp.containers.slots.OutputSlot;
import com.budrunbun.lavalamp.tileEntities.CheeseGeneratorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CheeseGeneratorContainer extends Container {

    private final IItemHandler generatorInventory, playerInventory;
    private short milkCapacity;

    @OnlyIn(Dist.CLIENT)
    public CheeseGeneratorContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(windowID, playerInventory, new CheeseGeneratorTileEntity().getHandler(), new CheeseGeneratorTileEntity().getMilkCapacity());
    }

    public CheeseGeneratorContainer(int windowId, PlayerInventory playerInventory, IItemHandler generatorInventory, short milkCapacity) {
        super(ModContainers.CHEESE_GENERATOR_CONTAINER, windowId);

        this.generatorInventory = generatorInventory;
        this.playerInventory = new InvWrapper(playerInventory);
        this.milkCapacity = milkCapacity;

        this.addSlot(new InputSlot(Items.MILK_BUCKET, generatorInventory, 0, 12, 58));

        this.addSlot(new InputSlot(Items.ACACIA_FENCE,generatorInventory, 1, 84, 75));

        this.addSlot(new InputSlot(Items.WATER_BUCKET,generatorInventory, 2, 156, 58));

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

        System.out.println("Container " + milkCapacity + "/8");
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public int getMilkCapacity() {
        return this.milkCapacity;
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
        return this.milkCapacity * 49 / 8;
    }
}
