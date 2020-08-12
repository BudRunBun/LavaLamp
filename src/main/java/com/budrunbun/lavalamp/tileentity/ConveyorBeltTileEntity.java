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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

public class ConveyorBeltTileEntity extends TileEntity implements ITickableTileEntity {

    private final float[] progress = new float[]{2, 2, 2};

    private final boolean[] isWaiting = new boolean[]{true, true, true};

    private ItemStackHandler handler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    @Override
    public void tick() {
        List list = this.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.getPos().getX(), this.getPos().getY() + 3 / 16.0, this.getPos().getZ(), this.getPos().getX() + 1, this.getPos().getY() + 1, this.getPos().getZ() + 1));

        for (Object o : list) {
            a:
            if (o != null) {
                for (int i = 0; i < 3; i++) {
                    if (handler.getStackInSlot(i).isEmpty()) {
                        handler.setStackInSlot(i, ((ItemEntity) o).getItem());
                        ((ItemEntity) o).remove();
                        isWaiting[i] = true;
                        break a;
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if (progress[i] > 14F) {
                progress[i] = 2F;
                isWaiting[i] = true;
                dropItemStack(world, pos, handler.getStackInSlot(i));
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }

            if (handler.getStackInSlot(i).isEmpty()) {
                progress[i] = 2F;
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
            progress[0] += 0.2F;
        }

        if (!isWaiting[1]) {
            progress[1] += 0.2F;
        }

        if (!isWaiting[2]) {
            progress[2] += 0.2F;
        }

        //this.updateHopper();

        this.markDirty();
    }

    private static ItemStack insertStack(@Nullable IInventory source, IInventory destination, ItemStack stack, int index, @Nullable Direction direction) {
        ItemStack itemstack = destination.getStackInSlot(index);
        if (canInsertItemInSlot(destination, stack, index, direction)) {
            boolean flag = false;
            boolean flag1 = destination.isEmpty();
            if (itemstack.isEmpty()) {
                destination.setInventorySlotContents(index, stack);
                stack = ItemStack.EMPTY;
                flag = true;
            } else if (canCombine(itemstack, stack)) {
                int i = stack.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                if (flag1 && destination instanceof HopperTileEntity) {
                    HopperTileEntity hoppertileentity1 = (HopperTileEntity) destination;
                    if (!hoppertileentity1.mayTransfer()) {
                        int k = 0;
                        if (source instanceof HopperTileEntity) {
                            HopperTileEntity hoppertileentity = (HopperTileEntity) source;
                            if (hoppertileentity1.getLastUpdateTime() >= hoppertileentity.getLastUpdateTime()) {
                                k = 1;
                            }
                        }

                        hoppertileentity1.setTransferCooldown(8 - k);
                    }
                }

                destination.markDirty();
            }
        }

        return stack;
    }

    private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, @Nullable Direction side) {
        if (!inventoryIn.isItemValidForSlot(index, stack)) {
            return false;
        } else {
            return !(inventoryIn instanceof ISidedInventory) || ((ISidedInventory) inventoryIn).canInsertItem(index, stack, side);
        }
    }

    private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != stack2.getItem()) {
            return false;
        } else if (stack1.getDamage() != stack2.getDamage()) {
            return false;
        } else if (stack1.getCount() > stack1.getMaxStackSize()) {
            return false;
        } else {
            return ItemStack.areItemStackTagsEqual(stack1, stack2);
        }
    }

    private boolean transferItemsOut() {
        IInventory iinventory = this.getInventoryForHopperTransfer();
        if (iinventory != null) {
            Direction direction = this.getBlockState().get(HorizontalFacingBlock.FACING).getOpposite();
            if (!this.isInventoryFull(iinventory, direction)) {
                for (int i = 0; i < 3; ++i) {
                    if (!this.handler.getStackInSlot(i).isEmpty()) {
                        ItemStack itemstack = this.handler.getStackInSlot(i);
                        ItemStack itemStack1 = itemstack;
                        itemStack1.shrink(1);
                        ItemStack itemStack2 = putStackInInventoryAllSlots(this.getInventoryForHopperTransfer(), iinventory, itemStack1, direction);
                        if (itemStack2.isEmpty()) {
                            iinventory.markDirty();
                            return true;
                        }
                        this.setInventorySlotContents(i, itemstack);
                    }
                }

            }
        }
        return false;
    }

    private boolean updateHopper() {
        if (this.world != null && !this.world.isRemote) {
            boolean flag = false;

            flag = this.transferItemsOut();

            /*if (!this.isFull()) {
                flag |= p_200109_1_.get();
            }*/

            if (flag) {
                this.markDirty();
                return true;
            }

        }
        return false;
    }

    private boolean isInventoryEmpty() {
        for (int i = 0; i < 3; i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++) {
            if (handler.getStackInSlot(i).isEmpty() || handler.getStackInSlot(i).getCount() != handler.getStackInSlot(i).getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        this.getHandler().setStackInSlot(index, stack);
        if (stack.getCount() > 1) {
            stack.setCount(1);
        }
    }

    private IInventory getInventoryForHopperTransfer() {
        Direction direction = this.getBlockState().get(HorizontalFacingBlock.FACING);
        return getInventoryAtPosition(this.getWorld(), this.pos.offset(direction));
    }

    private boolean isInventoryFull(IInventory inventoryIn, Direction side) {
        return func_213972_a(inventoryIn, side).allMatch((p_213970_1_) -> {
            ItemStack itemstack = inventoryIn.getStackInSlot(p_213970_1_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }

    private static IntStream func_213972_a(IInventory p_213972_0_, Direction p_213972_1_) {
        return p_213972_0_ instanceof ISidedInventory ? IntStream.of(((ISidedInventory) p_213972_0_).getSlotsForFace(p_213972_1_)) : IntStream.range(0, p_213972_0_.getSizeInventory());
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World p_195484_0_, BlockPos p_195484_1_) {
        return getInventoryAtPosition(p_195484_0_, (double) p_195484_1_.getX() + 0.5D, (double) p_195484_1_.getY() + 0.5D, (double) p_195484_1_.getZ() + 0.5D);
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
        IInventory iinventory = null;
        BlockPos blockpos = new BlockPos(x, y, z);
        BlockState blockstate = worldIn.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iinventory = ((ISidedInventoryProvider) block).createInventory(blockstate, worldIn, blockpos);
        } else if (blockstate.hasTileEntity()) {
            TileEntity tileentity = worldIn.getTileEntity(blockpos);
            if (tileentity instanceof IInventory) {
                iinventory = (IInventory) tileentity;
                if (iinventory instanceof ChestTileEntity && block instanceof ChestBlock) {
                    iinventory = ChestBlock.getInventory(blockstate, worldIn, blockpos, true);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = worldIn.getEntitiesInAABBexcluding((Entity) null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntityPredicates.HAS_INVENTORY);
            if (!list.isEmpty()) {
                iinventory = (IInventory) list.get(worldIn.rand.nextInt(list.size()));
            }
        }

        return iinventory;
    }

    public static ItemStack putStackInInventoryAllSlots(@Nullable IInventory source, IInventory destination, ItemStack stack, @Nullable Direction direction) {
        if (destination instanceof ISidedInventory && direction != null) {
            ISidedInventory isidedinventory = (ISidedInventory) destination;
            int[] aint = isidedinventory.getSlotsForFace(direction);

            for (int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
                stack = insertStack(source, destination, stack, aint[k], direction);
            }
        } else {
            int i = destination.getSizeInventory();

            for (int j = 0; j < i && !stack.isEmpty(); ++j) {
                stack = insertStack(source, destination, stack, j, direction);
            }
        }

        return stack;
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

    public ItemStackHandler getHandler() {
        return handler;
    }

    public void setHandler(ItemStackHandler handlerIn) {
        handler = handlerIn;
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

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        write(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }

    @Nonnull
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getNbtCompound();
        handleUpdateTag(tag);
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
    }

    public ConveyorBeltTileEntity() {
        super(ModTileEntities.CONVEYOR_BELT_TE);
    }

    public float[] getProgress() {
        return progress;
    }


}
