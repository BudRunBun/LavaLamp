package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class PlayerSensorBlock extends HorizontalFacingBlock {
    public PlayerSensorBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("player_sensor");
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
