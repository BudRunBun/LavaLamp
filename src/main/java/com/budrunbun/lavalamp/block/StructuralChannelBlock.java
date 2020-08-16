package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class StructuralChannelBlock extends HorizontalFacingBlock {

    private static final VoxelShape SHAPE_1 = Block.makeCuboidShape(9, 0, 16, 7, 1.5, 0);
    private static final VoxelShape SHAPE_2 = Block.makeCuboidShape(16, 1.5, 7, 0, 0, 9);

    public StructuralChannelBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("structural_channel");
    }

    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return SHAPE_1;
        } else {
            return SHAPE_2;
        }
    }
}
