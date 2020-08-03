package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class GlassDoorBlock extends DoorBlock {
    //TODO: Smooth movement

    public GlassDoorBlock() {
        super(Block.Properties.create(Material.ROCK));
        setRegistryName("glass_door");
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return super.getShape(state, worldIn, pos, context);
    }
}