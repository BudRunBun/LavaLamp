package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CoreBlock extends Block {

    public CoreBlock() {
        super(Block.Properties.from(Blocks.IRON_BLOCK));
        setRegistryName("core");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.CORE_TE.create();
    }
}
