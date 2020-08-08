package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

public class GrillCeilingBlock extends Block {

    public GrillCeilingBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("grill_ceiling_block");
    }


    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
