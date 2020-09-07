package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ShopControllerBlock extends HorizontalFacingBlock{
    public ShopControllerBlock() {
        super(Block.Properties.from(Blocks.IRON_BLOCK).hardnessAndResistance(-1,3600000.0F).noDrops());
        setRegistryName("shop_controller");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.SHOP_CONTROLLER_TE.create();
    }

}
