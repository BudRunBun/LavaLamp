package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
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

import javax.annotation.Nonnull;

public class GlassDoorBlock extends DoorBlock {
    //TODO: Smooth movement

    private static final VoxelShape SHAPE_OPEN_NORTH = Block.makeCuboidShape(13, 0, 6, 16, 16, 10);
    private static final VoxelShape SHAPE_OPEN_SOUTH = Block.makeCuboidShape(0, 0, 6, 3, 16, 10);
    private static final VoxelShape SHAPE_OPEN_WEST = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
    private static final VoxelShape SHAPE_OPEN_EAST = Block.makeCuboidShape(6, 0, 13, 10, 16, 16);
    private static final VoxelShape SHAPE_CLOSED_1 = Block.makeCuboidShape(0, 0, 6, 16, 16, 10);
    private static final VoxelShape SHAPE_CLOSED_2 = Block.makeCuboidShape(6, 0, 0, 10, 16, 16);
    private static final VoxelShape SHAPE_BEHIND_SOUTH = Block.makeCuboidShape(0, 16, 10, -13, 0, 6);
    private static final VoxelShape SHAPE_BEHIND_NORTH = Block.makeCuboidShape(16, 0, 6, 29, 16, 10);
    private static final VoxelShape SHAPE_BEHIND_WEST = Block.makeCuboidShape(6, 0, -13, 10, 16, 0);
    private static final VoxelShape SHAPE_BEHIND_EAST = Block.makeCuboidShape(10, 0, 29, 6, 16, 16);

    public GlassDoorBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("glass_door");
    }

    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        if (!state.get(POWERED)) {
            if (direction == Direction.NORTH || direction == Direction.SOUTH)
                return SHAPE_CLOSED_1;
            else
                return SHAPE_CLOSED_2;
        } else {
            if (state.get(HINGE) == DoorHingeSide.RIGHT) {
                switch (direction) {
                    case NORTH:
                        return VoxelShapes.or(SHAPE_BEHIND_NORTH, SHAPE_OPEN_NORTH);
                    case SOUTH:
                        return VoxelShapes.or(SHAPE_BEHIND_SOUTH, SHAPE_OPEN_SOUTH);
                    case EAST:
                        return VoxelShapes.or(SHAPE_BEHIND_EAST, SHAPE_OPEN_EAST);
                    case WEST:
                        return VoxelShapes.or(SHAPE_BEHIND_WEST, SHAPE_OPEN_WEST);
                }
            } else {
                switch (direction) {
                    case NORTH:
                        return VoxelShapes.or(SHAPE_BEHIND_SOUTH, SHAPE_OPEN_SOUTH);
                    case SOUTH:
                        return VoxelShapes.or(SHAPE_BEHIND_NORTH, SHAPE_OPEN_NORTH);
                    case EAST:
                        return VoxelShapes.or(SHAPE_BEHIND_WEST, SHAPE_OPEN_WEST);
                    case WEST:
                        return VoxelShapes.or(SHAPE_BEHIND_EAST, SHAPE_OPEN_EAST);
                }
            }
        }
        return VoxelShapes.empty();

    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}