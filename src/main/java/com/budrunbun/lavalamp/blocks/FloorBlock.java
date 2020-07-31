package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;
import java.util.Random;

public class FloorBlock extends Block {

    private static final IProperty<Integer> VARIANT = IntegerProperty.create("variant", 0, 1);

    private final Random rand = new Random();
    private int n = 0;

    public FloorBlock() {
        super(Block.Properties.create(Material.ROCK));
        setRegistryName("floor_block");
        this.setDefaultState(this.stateContainer.getBaseState().with(VARIANT, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        n = rand.nextInt(2);
        return this.getDefaultState().with(VARIANT, n);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}