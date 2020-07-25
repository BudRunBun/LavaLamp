package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.tileEntities.ModTileEntities;
import com.budrunbun.lavalamp.tileEntities.ShelfTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ShelfBlock extends FacingBlock {

    private static final VoxelShape SHAPE_BOTTOM_SHELF = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
    private static final VoxelShape SHAPE_MIDDLE_SHELF = Block.makeCuboidShape(0, 7, 0, 16, 9, 16);
    private static final VoxelShape SHAPE_TOP_SHELF = Block.makeCuboidShape(0, 15, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(0, 0, 0, 16, 16, 1);
    private static final VoxelShape SHAPE = VoxelShapes.or(SHAPE_BOTTOM, SHAPE_BOTTOM_SHELF, SHAPE_MIDDLE_SHELF, SHAPE_TOP_SHELF);

    public ShelfBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("shelf_block");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            if (player.isSneaking()) {
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof INamedContainerProvider) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.SHELF_TE.create();
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void onReplaced(final BlockState state, final World world, final BlockPos pos, final BlockState newState, final boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            final ShelfTileEntity tileEntity = (ShelfTileEntity) world.getTileEntity(pos);
            if (tileEntity != null) {
                dropItemHandlerContents(world, pos, tileEntity.getHandler());
                world.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    public static void dropItemHandlerContents(final World world, final BlockPos pos, final IItemHandler itemHandler) {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            final ItemStack stack = itemHandler.extractItem(slot, Integer.MAX_VALUE, false);
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return calculateFacing(context, true);
    }
}
