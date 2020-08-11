package com.budrunbun.lavalamp.tileentity;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ConveyorBeltTileEntity extends TileEntity implements ITickableTileEntity {

    private final float[] progress = new float[]{2, 2, 2};

    private ItemStackHandler handler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };

    @Override
    public void tick() {
        List list = this.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.getPos().getX(), this.getPos().getY() + 3 / 16.0, this.getPos().getZ(), this.getPos().getX() + 1, this.getPos().getY() + 1, this.getPos().getZ() + 1));

        for (Object o : list) {
            if (o != null) {
                if (handler.getStackInSlot(0).isEmpty()) {
                    handler.setStackInSlot(0, ((ItemEntity) o).getItem());
                    ((ItemEntity) o).remove();
                    progress[0] = 2F;
                    break;
                }

                if (handler.getStackInSlot(1).isEmpty()) {
                    handler.setStackInSlot(1, ((ItemEntity) o).getItem());
                    ((ItemEntity) o).remove();
                    progress[1] = 2F;
                    break;
                }

                if (handler.getStackInSlot(2).isEmpty()) {
                    handler.setStackInSlot(2, ((ItemEntity) o).getItem());
                    ((ItemEntity) o).remove();
                    progress[2] = 2F;
                    break;
                }
            }
        }

        if ((progress[1] >= 8F || handler.getStackInSlot(1).isEmpty()) && ((progress[2] >= 8F && !handler.getStackInSlot(2).isEmpty()) || handler.getStackInSlot(2).isEmpty()) && progress[0] < 14F) {
            progress[0] += 0.1F;
        }

        if ((progress[0] >= 8F || handler.getStackInSlot(0).isEmpty()) && (progress[2] >= 8F || handler.getStackInSlot(2).isEmpty()) && progress[1] < 14F) {
            progress[1] += 0.1F;
        }

        if ((progress[1] >= 8F || handler.getStackInSlot(1).isEmpty()) && (progress[0] >= 8F ||handler.getStackInSlot(0).isEmpty()) && progress[2] < 14F) {
            progress[2] += 0.1F;
        }

        for (int i = 0; i < 3; i++) {
            if (progress[i] > 14F) {
                progress[i] = 2F;
                dropItemStack(this.world, this.getPos(), handler.getStackInSlot(i));
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    private void dropItemStack(World world, BlockPos pos, @Nonnull ItemStack stack) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .3f, pos.getZ() + 1.5f, stack);
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

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.handler.deserializeNBT(compound.getCompound("inv"));
        progress[0] = compound.getFloat("progress0");
        progress[1] = compound.getFloat("progress1");
        progress[2] = compound.getFloat("progress2");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.put("inv", handler.serializeNBT());
        compound.putFloat("progress0", progress[0]);
        compound.putFloat("progress1", progress[1]);
        compound.putFloat("progress2", progress[2]);
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
