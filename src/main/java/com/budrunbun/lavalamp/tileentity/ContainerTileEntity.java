package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.container.ModItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Basic class for all mod container tile entities that contains some useful utilities
 */
public class ContainerTileEntity extends TileEntity implements ISidedInventory {
    protected ModItemStackHandler handler;

    public ContainerTileEntity(TileEntityType<?> tileEntityTypeIn, int handlerSize, int slotLimit) {
        super(tileEntityTypeIn);

        handler = new ModItemStackHandler(handlerSize) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }

            @Override
            public int getSlotLimit(int slot) {
                return slotLimit;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isItemValidForSlot(slot, stack);
            }
        };
    }

    @Override
    public int getSizeInventory() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.handler.deserializeNBT(compound.getCompound("inv"));
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.put("inv", handler.serializeNBT());
        return compound;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index >= 0 && index < handler.getSlots()) {
            return handler.getStackInSlot(index);
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < handler.getSlots() && index >= 0) {
            update();
            return handler.getStackInSlot(index).split(count);
        } else
            return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index >= 0 && index < handler.getSlots()) {
            ItemStack itemstack = handler.getStackInSlot(index);
            handler.setStackInSlot(index, ItemStack.EMPTY);
            update();
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        if (index >= 0 && index < handler.getSlots()) {
            handler.setStackInSlot(index, stack);
            if (stack.getCount() > this.getInventoryStackLimit()) {
                stack.setCount(this.getInventoryStackLimit());
            }
        }
        update();
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return index >= 0 && index < handler.getSlots() && handler.getStackInSlot(index).isEmpty();
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < handler.getSlots(); i++) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }
        update();
    }

    private void update() {
        if (this.world != null) {
            this.world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
        }
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        int[] ALL = new int[handler.getSlots()];

        for (int i = 0; i < handler.getSlots(); i++) {
            ALL[i] = i;
        }

        return ALL;
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return true;
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        write(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }

    @Nonnull
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getNbtCompound();
        handleUpdateTag(tag);
        update();
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public void setHandler(ItemStackHandler handlerIn) {
        handler = (ModItemStackHandler) handlerIn;
    }
}
