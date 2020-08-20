package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.block.HorizontalFacingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class ConveyorBeltTileEntity extends ContainerTileEntity implements ITickableTileEntity {

    //TODO: Smooth animation, transition fix, duplication fix, sync conveyor with item movement

    private final float[] progress = new float[]{0, 0, 0};

    private final boolean[] isWaiting = new boolean[]{true, true, true};

    @Override
    public void tick() {
        List<Entity> list = this.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.getPos().getX(), this.getPos().getY() + 3 / 16.0, this.getPos().getZ(), this.getPos().getX() + 1, this.getPos().getY() + 1, this.getPos().getZ() + 1).shrink(0.001));

        for (Object o : list) {
            a:
            if (o != null) {
                for (int i = 0; i < 3; i++) {
                    if (handler.getStackInSlot(i).isEmpty()) {
                        handler.setStackInSlot(i, ((ItemEntity) o).getItem());
                        ((ItemEntity) o).remove();
                        break a;
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if (progress[i] >= 16F) {
                progress[i] = 0F;
                isWaiting[i] = true;
                IInventory inv = getInventory();
                if (inv == null) {
                    dropItemStack(world, pos, handler.getStackInSlot(i));
                } else {
                    if (!handler.getStackInSlot(i).isEmpty()) {
                        putItemStack(handler.getStackInSlot(i), inv, 0);
                    }
                }
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }

            if (handler.getStackInSlot(i).isEmpty() && !isWaiting[i]) {
                progress[i] = 0F;
                isWaiting[i] = true;
            }
        }

        if ((!handler.getStackInSlot(0).isEmpty() && progress[1] < 7.9F && !isWaiting[1] && progress[0] < 8)
                || (!handler.getStackInSlot(0).isEmpty() && progress[2] < 7.9F && !isWaiting[2] && progress[0] < 8)) {
            isWaiting[0] = true;
        }

        if ((!handler.getStackInSlot(1).isEmpty() && progress[0] < 7.9F && !isWaiting[0] && progress[1] < 8)
                || (!handler.getStackInSlot(1).isEmpty() && progress[2] < 7.9F && !isWaiting[2] && progress[1] < 8)) {
            isWaiting[1] = true;
        }

        if ((!handler.getStackInSlot(2).isEmpty() && progress[0] < 7.9F && !isWaiting[0] && progress[2] < 8)
                || (!handler.getStackInSlot(2).isEmpty() && progress[1] < 7.9F && !isWaiting[1] && progress[2] < 8)) {
            isWaiting[2] = true;
        }

        if ((handler.getStackInSlot(2).isEmpty() || progress[2] >= 8.0F || isWaiting[2])
                && (handler.getStackInSlot(1).isEmpty() || progress[1] >= 8.0F || isWaiting[1])
                && !handler.getStackInSlot(0).isEmpty()) {
            isWaiting[0] = false;
        }

        if ((handler.getStackInSlot(2).isEmpty() || progress[2] >= 8.0F || isWaiting[2])
                && (handler.getStackInSlot(0).isEmpty() || progress[0] >= 8.0F || isWaiting[0])
                && !handler.getStackInSlot(1).isEmpty()) {
            isWaiting[1] = false;
        }

        if ((handler.getStackInSlot(1).isEmpty() || progress[1] >= 8.0F || isWaiting[1])
                && (handler.getStackInSlot(0).isEmpty() || progress[0] >= 8.0F || isWaiting[0])
                && !handler.getStackInSlot(2).isEmpty()) {
            isWaiting[2] = false;
        }

        if (!isWaiting[0]) {
            progress[0] += 12 / 32.0;
        }

        if (!isWaiting[1]) {
            progress[1] += 12 / 32.0;
        }

        if (!isWaiting[2]) {
            progress[2] += 12 / 32.0;
        }

        //update();
        this.markDirty();
    }

    private void dropItemStack(World world, BlockPos pos, @Nonnull ItemStack stack) {
        ItemEntity entity = null;
        switch (this.getBlockState().get(HorizontalFacingBlock.FACING)) {
            case NORTH:
                entity = new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 0.3F, pos.getZ() - 0.5F, stack);
                break;
            case SOUTH:
                entity = new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 0.3F, pos.getZ() + 1.5F, stack);
                break;
            case EAST:
                entity = new ItemEntity(world, pos.getX() + 1.5F, pos.getY() + 0.3F, pos.getZ() + 0.5F, stack);
                break;
            case WEST:
                entity = new ItemEntity(world, pos.getX() - 0.5F, pos.getY() + 0.3F, pos.getZ() + 0.5F, stack);
                break;
        }
        Vec3d motion = entity.getMotion();
        entity.addVelocity(-motion.x, -motion.y, -motion.z);
        world.addEntity(entity);
    }

    public boolean[] getQueue() {
        return isWaiting;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);

        this.handler.deserializeNBT(compound.getCompound("inv"));

        progress[0] = compound.getFloat("progress0");
        progress[1] = compound.getFloat("progress1");
        progress[2] = compound.getFloat("progress2");

        isWaiting[0] = compound.getBoolean("wait0");
        isWaiting[1] = compound.getBoolean("wait1");
        isWaiting[2] = compound.getBoolean("wait2");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);

        compound.put("inv", handler.serializeNBT());

        compound.putFloat("progress0", progress[0]);
        compound.putFloat("progress1", progress[1]);
        compound.putFloat("progress2", progress[2]);

        compound.putBoolean("wait0", isWaiting[0]);
        compound.putBoolean("wait1", isWaiting[1]);
        compound.putBoolean("wait2", isWaiting[2]);

        return compound;
    }

    private IInventory getInventory() {
        IInventory iinventory = null;
        BlockPos blockpos = pos.offset(getBlockState().get(HorizontalFacingBlock.FACING)).add(0.5, 0.5, 0.5);
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iinventory = ((ISidedInventoryProvider) block).createInventory(blockstate, world, blockpos);
        } else if (blockstate.hasTileEntity()) {
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity instanceof IInventory) {
                iinventory = (IInventory) tileentity;
                if (iinventory instanceof ChestTileEntity && block instanceof ChestBlock) {
                    iinventory = ChestBlock.getInventory(blockstate, world, blockpos, true);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(blockpos.getX() - 0.5D, blockpos.getY() - 0.5D, blockpos.getZ() - 0.5D, blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D), EntityPredicates.HAS_INVENTORY);
            if (!list.isEmpty()) {
                iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
            }
        }

        return iinventory;
    }

    private void putItemStack(ItemStack stack, IInventory inv, int slot) {
        if (inv instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory) inv;
            int[] sideSlots = isidedinventory.getSlotsForFace(this.getBlockState().get(HorizontalFacingBlock.FACING));

            if (slot == sideSlots.length) {
                dropItemStack(world, pos, stack);
                return;
            }

            ItemStack residue = canCombine(inv, stack, sideSlots[slot]);

            if (residue != ItemStack.EMPTY) {
                putItemStack(residue, inv, slot + 1);
            }
        } else {
            if (slot == inv.getSizeInventory()) {
                dropItemStack(world, pos, stack);
                return;
            }

            ItemStack residue = canCombine(inv, stack, slot);

            if (residue != ItemStack.EMPTY) {
                putItemStack(residue, inv, slot + 1);
            }
        }
    }

    private ItemStack canCombine(IInventory inv, ItemStack comingStack, int slot) {
        ItemStack invStack = inv.getStackInSlot(slot);
        CompoundNBT nbt = invStack.serializeNBT();

        if (!inv.isItemValidForSlot(slot, comingStack)) {
            return comingStack;
        }

        if (invStack.isEmpty()) {
            inv.setInventorySlotContents(slot, comingStack);
            return ItemStack.EMPTY;
        }

        if (invStack.getItem() == comingStack.getItem() && ItemStack.areItemStackTagsEqual(invStack, comingStack)) {
            if (invStack.getCount() + comingStack.getCount() <= invStack.getMaxStackSize()) {
                inv.setInventorySlotContents(slot, new ItemStack(invStack.getItem(), invStack.getCount() + comingStack.getCount(), nbt));
                inv.markDirty();
                return ItemStack.EMPTY;
            } else {
                ItemStack residue = comingStack.copy();
                for (int j = 1; j <= comingStack.getCount(); j++) {
                    residue.shrink(j);
                    if (invStack.getCount() + residue.getCount() <= invStack.getMaxStackSize()) {
                        return new ItemStack(comingStack.getItem(), j, nbt);
                    }
                }
            }
        }
        return comingStack;
    }

    public ConveyorBeltTileEntity() {
        super(ModTileEntities.CONVEYOR_BELT_TE, 3, 1);
    }

    public float[] getProgress() {
        return progress;
    }
}
