package com.budrunbun.lavalamp.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CheeseBlock extends Block {

    public CheeseBlock() {
        super(Properties.create(Material.WOOL).sound(SoundType.SLIME));
        setRegistryName("cheese_block");
    }
}
