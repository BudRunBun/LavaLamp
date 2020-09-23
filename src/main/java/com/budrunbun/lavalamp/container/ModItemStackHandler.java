package com.budrunbun.lavalamp.container;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Utility class, the purpose is to make default ItemStackHandler more comfortable to use.
 * Forge developers can't insert "return this;" in their methods for some reason.
 */
//SRSLY forge????
public class ModItemStackHandler extends ItemStackHandler {
    public ModItemStackHandler(int handlerSize) {
        super(handlerSize);
    }

    public ItemStackHandler setSizeFixed(int size) {
        setSize(size);
        return this;
    }

    public ItemStackHandler setStackInSlotFixed(int slot, @Nonnull ItemStack stack) {
        super.setStackInSlot(slot, stack);
        return this;
    }

    public ItemStackHandler deserializeNBTFixed(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        return this;
    }
}
