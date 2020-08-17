package com.budrunbun.lavalamp.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class DisplayFreezerTileEntity extends ContainerTileEntity {

    public DisplayFreezerTileEntity() {
        super(ModTileEntities.DISPLAY_FREEZER_TE, 12, 1);
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return super.isItemValidForSlot(index, stack) && stack.getItem().isFood();
    }
}
