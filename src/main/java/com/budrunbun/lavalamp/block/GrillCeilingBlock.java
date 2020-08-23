package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class GrillCeilingBlock extends HorizontalFacingBlock {

    public GrillCeilingBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("grill_ceiling_block");
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean propagatesSkylightDown(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
        return true;
    }

    @Override
    public void onBlockHarvested(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player) {
        List<GuardEntity> guards = world.getEntitiesWithinAABB(GuardEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).grow(10));
        for (GuardEntity guard : guards) {
            guard.setAttackTarget(player);
        }
        super.onBlockHarvested(world, pos, state, player);
    }
}
