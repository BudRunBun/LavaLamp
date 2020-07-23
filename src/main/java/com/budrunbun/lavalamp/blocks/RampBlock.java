package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RampBlock extends FacingBlock {

    private static VoxelShape SHAPE_NORTH = VoxelShapes.empty();
    private static VoxelShape SHAPE_EAST = VoxelShapes.empty();
    private static VoxelShape SHAPE_SOUTH = VoxelShapes.empty();
    private static VoxelShape SHAPE_WEST =VoxelShapes.empty();

    static {
        for (int p = 0; p <= 8; p++) {
            SHAPE_NORTH = VoxelShapes.or(SHAPE_NORTH, Block.makeCuboidShape(16, p, 16 - p * 2, 0, p + 1, 0));
            SHAPE_EAST = VoxelShapes.or(SHAPE_EAST, Block.makeCuboidShape(p * 2, p, 0, 16, p + 1, 16));
            SHAPE_SOUTH = VoxelShapes.or(SHAPE_SOUTH, Block.makeCuboidShape(0, p, p * 2, 16, p + 1, 16));
            SHAPE_WEST = VoxelShapes.or(SHAPE_WEST, Block.makeCuboidShape(16 - p * 2, p, 16, 0, p + 1, 0));
        }
    }
    // TODO: Convex Hull

    public RampBlock() {
        super(Properties.create(Material.IRON));
        setRegistryName("ramp_block");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }

/*  @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }*/

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction.getOpposite()));
        return blockstate.getBlock() == this && blockstate.get(FACING) == direction ? this.getDefaultState().with(FACING, direction) : this.getDefaultState().with(FACING, direction.getOpposite());
    }
/*  @OnlyIn(Dist.CLIENT)
    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        //Makes the ground stay bright
        return 1.0F;
    }*/
}
