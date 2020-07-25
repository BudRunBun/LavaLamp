package com.budrunbun.lavalamp.tileEntities;

import com.budrunbun.lavalamp.containers.ShelfContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ShelfTileEntity extends TileEntity implements INamedContainerProvider {

    private final ItemStackHandler handler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };

    public ShelfTileEntity() {
        super(ModTileEntities.SHELF_TE);
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.handler.deserializeNBT(compound.getCompound("inv"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("inv", handler.serializeNBT());
        return compound;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("shelf_block");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ShelfContainer(windowId, handler, playerInventory);
    }
}