package com.budrunbun.lavalamp.tileentity;

import com.budrunbun.lavalamp.block.PlayerSensorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class PlayerSensorTileEntity extends TileEntity implements ITickableTileEntity {
    public PlayerSensorTileEntity() {
        super(ModTileEntities.PLAYER_SENSOR_TE);
    }

    @Override
    public void tick() {
        PlayerEntity playerentity = this.world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 3.0D, false);
        world.setBlockState(getPos(), world.getBlockState(pos).with(PlayerSensorBlock.POWERED, playerentity != null && playerentity.getPosition().getY() < this.pos.getY()), 3);
        ((PlayerSensorBlock) world.getBlockState(pos).getBlock()).updateNeighbors(this.world, pos);
    }
}
