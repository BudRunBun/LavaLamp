package com.budrunbun.lavalamp.containers;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;

public class CheeseGeneratorContainer extends Container {

    private final IInventory inventory;

    public CheeseGeneratorContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        super(ModContainers.CHEESE_GENERATOR_CONTAINER, windowId);

        this.inventory = inventory;

        assertInventorySize(inventory, 1);

        this.addSlot(new Slot(inventory, 0, 12 + 4 * 18, 8 + 2 * 18));

        int leftCol = (184 - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
        {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
            {
                this.addSlot(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, 184 - (4 - playerInvRow) * 18 - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
        {
            this.addSlot(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, 184 - 24));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }
}
