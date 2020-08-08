package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class IronFloorBlock extends Block {
    public IronFloorBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("iron_floor_block");
    }
}
