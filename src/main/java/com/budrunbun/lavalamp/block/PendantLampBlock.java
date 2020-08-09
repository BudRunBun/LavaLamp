package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class PendantLampBlock extends Block {
    public PendantLampBlock() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F).lightValue(15));
        setRegistryName("pendant_lamp");
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    @Override
    public float func_220080_a(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return 1.0F;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return Block.makeCuboidShape(5.5, 8, 11.5, 11.5, 16, 5.5);
    }
}
