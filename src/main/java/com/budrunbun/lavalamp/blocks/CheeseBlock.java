package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CheeseBlock extends Block {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(3.0d, 0.0d, 3.0d, 14.0d, 1.0d, 13.0d);

    public CheeseBlock() {
        super(Properties.create(Material.WOOL).sound(SoundType.SLIME));
        setRegistryName("cheese_block");
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
