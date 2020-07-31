package com.budrunbun.lavalamp.blocks;

import com.google.common.base.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class GrillCeilingBlock extends Block {

    Supplier s = GrillCeilingBlock::new;

    public GrillCeilingBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("grill_ceiling_block");
    }



    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
