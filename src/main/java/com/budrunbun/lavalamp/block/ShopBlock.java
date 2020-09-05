package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ShopBlock extends HorizontalFacingBlock {
    protected ShopBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        for (BlockPos position : BlockPos.getAllInBoxMutable(pos.add(-10,-10,-10), pos.add(10,10,10))) {
            if (worldIn.getBlockState(position).getBlock() instanceof ShopControllerBlock) {
                ((ShopControllerTileEntity)worldIn.getTileEntity(position)).shopBlockBroken(pos);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void onBlockHarvested(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player) {
        for (BlockPos position : BlockPos.getAllInBoxMutable(pos.add(-10,-10,-10), pos.add(10,10,10))) {
            if (worldIn.getBlockState(position).getBlock() instanceof ShopControllerBlock) {
                ((ShopControllerTileEntity)worldIn.getTileEntity(position)).shopBlockBrokenBy(pos, player);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
