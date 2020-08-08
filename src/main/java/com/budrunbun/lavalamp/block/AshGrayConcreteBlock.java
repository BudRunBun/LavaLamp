package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class AshGrayConcreteBlock extends Block {
    public AshGrayConcreteBlock() {
        super(Properties.create(Material.IRON));
        setRegistryName("ash_gray_concrete_block");
    }
}
