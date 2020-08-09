package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CheeseBlock extends Block {

    //TODO: smaller model and ability to eat

    public CheeseBlock() {
        super(Properties.create(Material.WOOL).sound(SoundType.SLIME));
        setRegistryName("cheese_block");
    }
}
