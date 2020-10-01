package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.screen.ShopControllerScreen;
import com.budrunbun.lavalamp.tileentity.ModTileEntities;
import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShopControllerBlock extends HorizontalFacingBlock {
    public ShopControllerBlock() {
        super(Block.Properties.from(Blocks.IRON_BLOCK).hardnessAndResistance(-1, 3600000.0F).noDrops());
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new ShopControllerScreen((ShopControllerTileEntity) worldIn.getTileEntity(pos)));
            return true;
        }
        return false;
    }
}
