package com.budrunbun.lavalamp.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class DisplayFreezerTileEntity extends ContainerTileEntity {

    public DisplayFreezerTileEntity() {
        super(ModTileEntities.DISPLAY_FREEZER_TE, 12, 1);
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return super.isItemValidForSlot(index, stack) && stack.getItem().isFood();
    }
}
