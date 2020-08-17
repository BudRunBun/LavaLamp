package com.budrunbun.lavalamp.tileentity;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class ShelfTileEntity extends ContainerTileEntity {

    public ShelfTileEntity() {
        super(ModTileEntities.SHELF_TE, 8, 1);
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
}
