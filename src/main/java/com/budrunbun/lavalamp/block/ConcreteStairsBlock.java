package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class ConcreteStairsBlock extends StairsBlock {

    public ConcreteStairsBlock() {
        super(() -> new AshGrayConcreteBlock().getDefaultState(), Block.Properties.from(new AshGrayConcreteBlock()));
        setRegistryName("concrete_stairs");
    }
}
