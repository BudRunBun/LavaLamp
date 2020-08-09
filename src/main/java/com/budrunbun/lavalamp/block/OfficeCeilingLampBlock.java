package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class OfficeCeilingLampBlock extends HorizontalFacingBlock {

    private static final VoxelShape SHAPE_1 = Block.makeCuboidShape(3.5, 13.8, 12, 12.5, 16, 4);
    private static final VoxelShape SHAPE_2 = Block.makeCuboidShape(12, 13.8, 3.5, 4, 16, 12.5);

    public OfficeCeilingLampBlock() {
        super(Block.Properties.from(new AshGrayConcreteBlock()).lightValue(15));
        setRegistryName("office_ceiling_lamp");
    }

    @Nonnull
    @Override
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
