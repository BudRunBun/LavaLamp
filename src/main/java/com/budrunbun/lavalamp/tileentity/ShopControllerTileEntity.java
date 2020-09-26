package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.entity.GuardEntity;
import com.budrunbun.lavalamp.entity.ModEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.List;

public class ShopControllerTileEntity extends TileEntity implements ITickableTileEntity {
    private int guardCount;
    private static final int MAX_GUARD_COUNT = 1;

    public ShopControllerTileEntity() {
        super(ModTileEntities.SHOP_CONTROLLER_TE);
    }

    public void shopBlockBrokenBy(BlockPos pos, PlayerEntity criminal) {
        if (criminal != null) {
            List<GuardEntity> guards = world.getEntitiesWithinAABB(GuardEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).grow(10));
            for (GuardEntity guard : guards) {
                guard.setAttackTarget(criminal);
            }
        }
    }

    public void shopBlockBroken(BlockPos pos) {
        shopBlockBrokenBy(pos, null);
    }

    @Override
    public void read(CompoundNBT compound) {
        this.guardCount = compound.getInt("guards");
        super.read(compound);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("guards", guardCount);
        return super.write(compound);
    }

    @SuppressWarnings("all")
    @Override
    public void tick() {
        if (guardCount < MAX_GUARD_COUNT) {
            GuardEntity guard = (GuardEntity) ModEntities.GUARD_ENTITY.spawn(this.world, null, null, this.pos.offset(Direction.NORTH), SpawnReason.MOB_SUMMONED, false, false);
            guard.setControllerPosition(this.pos);
            AxisAlignedBB aabb = (new AxisAlignedBB(getPos(), getPos().add(1, 1, 1))).grow(10);
            guard.setShopBounds(new BlockPos(aabb.minX, aabb.minY, aabb.minZ), new BlockPos(aabb.maxX, aabb.maxY, aabb.maxZ));
            guard.setPostPosition(this.pos.add(0, 0, 2));
            guardCount++;
        }
    }

    public void decreaseGuardAmount() {
        this.guardCount--;
    }
}
