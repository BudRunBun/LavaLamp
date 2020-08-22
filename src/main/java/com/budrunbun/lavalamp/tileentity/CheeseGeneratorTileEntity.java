package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.container.CheeseGeneratorContainer;
import com.budrunbun.lavalamp.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CheeseGeneratorTileEntity extends ContainerTileEntity implements INamedContainerProvider, ITickableTileEntity {
    //TODO: get side slots

    private int milkCapacity;
    private int waterCapacity;
    private int cookingTimeLeft = 0;
    private int on = 0;

    private final IIntArray generatorData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return CheeseGeneratorTileEntity.this.milkCapacity;
                case 1:
                    return CheeseGeneratorTileEntity.this.waterCapacity;
                case 2:
                    return CheeseGeneratorTileEntity.this.cookingTimeLeft;
                case 3:
                    return CheeseGeneratorTileEntity.this.on;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    CheeseGeneratorTileEntity.this.milkCapacity = value;
                    break;
                case 1:
                    CheeseGeneratorTileEntity.this.waterCapacity = value;
                    break;
                case 2:
                    CheeseGeneratorTileEntity.this.cookingTimeLeft = value;
                    break;
                case 3:
                    CheeseGeneratorTileEntity.this.on = value;
                    break;
            }

        }

        public int size() {
            return 4;
        }
    };

    public CheeseGeneratorTileEntity() {
        super(ModTileEntities.CHEESE_GENERATOR_TE, 4, 64);
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.milkCapacity = compound.getInt("milk");
        this.waterCapacity = compound.getInt("water");
        this.cookingTimeLeft = compound.getInt("time");
        this.on = compound.getInt("on");
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt("milk", milkCapacity);
        compound.putInt("water", milkCapacity);
        compound.putInt("time", cookingTimeLeft);
        compound.putInt("on", on);
        return compound;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("cheese_generator");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        this.fillWithLoot();
        return new CheeseGeneratorContainer(windowId, playerInventory, handler, this.generatorData);
    }

    @Override
    public void tick() {
        if (handler.getStackInSlot(0).getItem() == (Items.MILK_BUCKET) && milkCapacity < 8) {
            handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
            this.milkCapacity++;
        }

        if (handler.getStackInSlot(2).getItem() == (Items.WATER_BUCKET) && waterCapacity < 8) {
            handler.setStackInSlot(2, new ItemStack(Items.BUCKET));
            this.waterCapacity++;
        }

        if (this.milkCapacity > 0 && this.waterCapacity > 0 && handler.getStackInSlot(1).getItem() == Items.ACACIA_FENCE && on == 0) {
            on = 1;
            ItemStack items = handler.getStackInSlot(1);
            this.waterCapacity--;
            this.milkCapacity--;
            items.shrink(1);
        }

        if (on == 1) {
            this.cookingTimeLeft++;
            if (this.cookingTimeLeft == 400) {
                this.cookingTimeLeft = 0;
                handler.insertItem(3, new ItemStack(ModItems.CHEESE), false);
                on = 0;
            }
        }
        this.markDirty();
    }
}