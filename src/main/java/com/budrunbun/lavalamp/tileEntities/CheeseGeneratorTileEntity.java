package com.budrunbun.lavalamp.tileEntities;

import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class CheeseGeneratorTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    private short milkCapacity;

    private final ItemStackHandler handler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };

    public CheeseGeneratorTileEntity() {
        super(ModTileEntities.CHEESE_GENERATOR_TE);
        System.out.println("Tile entity " + milkCapacity + "/8");
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        handler.deserializeNBT(compound.getCompound("inv"));
        milkCapacity = compound.getShort("milk");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("inv", handler.serializeNBT());
        compound.putShort("milk", milkCapacity);
        return compound;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("cheese_generator");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {

        return new CheeseGeneratorContainer(windowId, playerInventory, handler, milkCapacity);
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public short getMilkCapacity() {
        return milkCapacity;
    }

    @Override
    public void tick() {
        if (handler.getStackInSlot(0).getItem() == (Items.MILK_BUCKET) && milkCapacity < 8) {
            handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
            milkCapacity++;
            this.markDirty();
        }
    }
}