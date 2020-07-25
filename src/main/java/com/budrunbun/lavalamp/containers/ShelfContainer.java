package com.budrunbun.lavalamp.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ShelfContainer extends Container {

    IItemHandler playerInventory;

    public ShelfContainer(int id, IItemHandler shelfInventory, PlayerInventory playerInventory) {
        super(ModContainers.SHELF_CONTAINER, id);

        this.playerInventory = new InvWrapper(playerInventory);

        this.addSlot(new SlotItemHandler(shelfInventory, 0, 41, 24));

        this.addSlot(new SlotItemHandler(shelfInventory, 1, 126, 23));

        this.addSlot(new SlotItemHandler(shelfInventory, 2, 41, 66));

        this.addSlot(new SlotItemHandler(shelfInventory, 3, 126, 65));

        int leftCol = (184 - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new SlotItemHandler(this.playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, 184 - (4 - playerInvRow) * 18 - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new SlotItemHandler(this.playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, 184 - 24));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
