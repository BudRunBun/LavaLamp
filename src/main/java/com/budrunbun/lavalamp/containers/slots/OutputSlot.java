package com.budrunbun.lavalamp.containers.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class OutputSlot extends SlotItemHandler {

    private final PlayerEntity player;
    private int removeCount;

    public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(itemHandler, index, xPosition, yPosition);

        this.player = player;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }
        return super.decrStackSize(amount);
    }

    @Override
    protected void onCrafting(@Nonnull ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack onTake(@Nonnull PlayerEntity thePlayer, @Nonnull ItemStack stack) {
        this.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    @Override
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }
}

