package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.block.PlayerSensorBlock;
import com.budrunbun.lavalamp.entity.IShopEmployee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class PlayerSensorTileEntity extends TileEntity implements ITickableTileEntity {
    public PlayerSensorTileEntity() {
        super(ModTileEntities.PLAYER_SENSOR_TE);
    }

    @Override
    public void tick() {
        List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1).grow(1.5));

        if (entities.isEmpty()) {
            world.setBlockState(getPos(), world.getBlockState(pos).with(PlayerSensorBlock.POWERED, false), 3);
            ((PlayerSensorBlock) world.getBlockState(pos).getBlock()).updateNeighbors(this.world, pos);
        }

        for (Entity entity : entities) {
            boolean flag = (entity instanceof IShopEmployee  || entity instanceof PlayerEntity) && entity.getPosition().getY() <= this.pos.getY();
            if (flag) {
                world.setBlockState(getPos(), world.getBlockState(pos).with(PlayerSensorBlock.POWERED, true), 3);
                ((PlayerSensorBlock) world.getBlockState(pos).getBlock()).updateNeighbors(this.world, pos);
            }
        }
    }
}
