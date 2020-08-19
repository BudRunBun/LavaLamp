package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class LogoBlock extends HorizontalFacingBlock {
    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(16, 0, 1, 0, 16, 0);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(15, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(1, 0, 16, 0, 16, 0);

    public LogoBlock() {
        super(Block.Properties.from(Blocks.OAK_WOOD));
        setRegistryName("logo");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            default:
                return SHAPE_WEST;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return calculateFacing(context, true);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
}
