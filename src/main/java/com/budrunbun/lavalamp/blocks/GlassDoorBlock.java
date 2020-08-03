package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GlassDoorBlock extends DoorBlock {
    //TODO: Smooth movement

    private static final VoxelShape SHAPE_OPEN_NORTH = Block.makeCuboidShape(13, 0, 6, 16, 16, 10);
    private static final VoxelShape SHAPE_OPEN_SOUTH = Block.makeCuboidShape(0, 0, 6, 3, 16, 10);
    private static final VoxelShape SHAPE_OPEN_WEST = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
    private static final VoxelShape SHAPE_OPEN_EAST = Block.makeCuboidShape(6, 0, 13, 10, 16, 16);
    private static final VoxelShape SHAPE_CLOSED_1 = Block.makeCuboidShape(0, 0, 6, 16, 16, 10);
    private static final VoxelShape SHAPE_CLOSED_2 = Block.makeCuboidShape(6, 0, 0, 10, 16, 16);
    private static final VoxelShape TEST_SOUTH = Block.makeCuboidShape(0, 16, 10, -13, 0, 6);
    private static final VoxelShape TEST_NORTH = Block.makeCuboidShape(16, 0, 6, 29, 16, 10);
    private static final VoxelShape TEST_WEST = Block.makeCuboidShape(6, 0, -13, 10, 16, 0);
    private static final VoxelShape TEST_EAST = Block.makeCuboidShape(10, 0, 29, 6, 16, 16);

    public GlassDoorBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("glass_door");
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        if (!state.get(OPEN)) {
            if (direction == Direction.NORTH || direction == Direction.SOUTH)
                return SHAPE_CLOSED_1;
            else
                return SHAPE_CLOSED_2;
        } else {
            if (state.get(HINGE) == DoorHingeSide.RIGHT) {
                switch (direction) {
                    case NORTH:
                        return VoxelShapes.or(TEST_NORTH, SHAPE_OPEN_NORTH);
                    case SOUTH:
                        return VoxelShapes.or(TEST_SOUTH, SHAPE_OPEN_SOUTH);
                    case EAST:
                        return VoxelShapes.or(TEST_EAST, SHAPE_OPEN_EAST);
                    case WEST:
                        return VoxelShapes.or(TEST_WEST,SHAPE_OPEN_WEST);
                }
            } else {
                switch (direction) {
                    case NORTH:
                        return VoxelShapes.or(TEST_SOUTH, SHAPE_OPEN_SOUTH);
                    case SOUTH:
                        return VoxelShapes.or(TEST_NORTH, SHAPE_OPEN_NORTH);
                    case EAST:
                        return VoxelShapes.or(TEST_WEST,SHAPE_OPEN_WEST);
                    case WEST:
                        return VoxelShapes.or(TEST_EAST, SHAPE_OPEN_EAST);
                }
            }
        }
        return VoxelShapes.empty();

    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    /*  @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockPos pos = currentPos;
        BlockState blockState = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        boolean right_sided = worldIn.getBlockState(pos).get(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.RIGHT;
        if (right_sided) {
            switch (facing) {
                case NORTH:
                    if (isDoorOpen(worldIn, pos.west())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case EAST:
                    if (isDoorOpen(worldIn, pos.north())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case WEST:
                    if (isDoorOpen(worldIn, pos.south())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case SOUTH:
                    if (isDoorOpen(worldIn, pos.east())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
            }
        } else {
            switch (facing) {
                case NORTH:
                    if (isDoorOpen(worldIn, pos.east())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case EAST:
                    if (isDoorOpen(worldIn, pos.south())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case WEST:
                    if (isDoorOpen(worldIn, pos.north())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
                case SOUTH:
                    if (isDoorOpen(worldIn, pos.west())) {
                        return blockState.with(OPEN, true);
                    }
                    break;
            }
        }
        return blockState;
    }

    private boolean isDoorOpen(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).get(OPEN);
    }*/
}