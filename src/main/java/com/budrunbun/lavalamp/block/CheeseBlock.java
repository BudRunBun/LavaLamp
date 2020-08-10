package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class CheeseBlock extends CakeBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 2.5D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 5, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 7.5, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 10, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 12.5, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 13.5D, 16.0D, 8.0D, 16.0D)};

    public CheeseBlock() {
        super(Properties.create(Material.CAKE).hardnessAndResistance(0.5F));
        setRegistryName("cheese_block");
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPES[state.get(BITES)];
    }
}
