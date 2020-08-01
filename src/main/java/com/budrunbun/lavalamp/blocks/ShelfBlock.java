package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.tileEntities.ModTileEntities;
import com.budrunbun.lavalamp.tileEntities.ShelfTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShelfBlock extends HorizontalFacingBlock {
    /*
        |1|3|
        |0|2|
    */
    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0, 0, 0, 16, 16, 8);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0, 0, 8, 16, 16, 16);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(8, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0, 0, 0, 8, 16, 16);
    private static final VoxelShape SHAPE = VoxelShapes.create(0.5625, 0.0625, 0.0625, 0.625, 0.4375, 0.5);

    public ShelfBlock() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F));
        setRegistryName("shelf_block");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(HorizontalFacingBlock.FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            default:
                return SHAPE_WEST;
        }
        //return SHAPE;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            if (!player.getHeldItemMainhand().isEmpty()) {
                int slot = getSlot(hit, state, pos);

                System.out.println("Slot: " + slot);

                ShelfTileEntity shelf = (ShelfTileEntity) worldIn.getTileEntity(pos);
                ItemStackHandler handler = shelf.getHandler();

                if (slot != -1 && handler.getStackInSlot(slot).isEmpty()) {
                    Item item = player.getHeldItemMainhand().getItem();
                    if (!player.isCreative()) {
                        ItemStack stack = player.getHeldItemMainhand();
                        stack.shrink(1);
                        player.setHeldItem(Hand.MAIN_HAND, stack);
                    }
                    handler.setStackInSlot(slot, new ItemStack(item, 1));
                    shelf.setHandler(handler);
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (!worldIn.isRemote()) {
            BlockRayTraceResult hit = rayTraceEyes(worldIn, player, player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue() + 1);
            int slot = getSlot(hit, state, pos);
            ShelfTileEntity shelf = (ShelfTileEntity) worldIn.getTileEntity(pos);
            ItemStackHandler handler = shelf.getHandler();

            if (slot != -1 && !handler.getStackInSlot(slot).isEmpty()) {
                ItemStack item = handler.getStackInSlot(slot);

                if (!player.inventory.addItemStackToInventory(item)) {
                    dropItemStack(worldIn, pos, item);

                    handler.setStackInSlot(slot, ItemStack.EMPTY);
                    shelf.setHandler(handler);
                }
            }
            worldIn.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    private void dropItemStack(World world, BlockPos pos, @Nonnull ItemStack stack) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .3f, pos.getZ() + .5f, stack);
        Vec3d motion = entity.getMotion();
        entity.addVelocity(-motion.x, -motion.y, -motion.z);
        world.addEntity(entity);
    }

    private int getSlot(BlockRayTraceResult hit, BlockState state, BlockPos pos) {
        double hitX = hit.getHitVec().getX() - pos.getX();
        double hitY = hit.getHitVec().getY() - pos.getY();
        double hitZ = hit.getHitVec().getZ() - pos.getZ();

        switch (state.get(HorizontalFacingBlock.FACING)) {
            case NORTH:
                if (hitX >= 0.0625 && hitY >= 0.0625 && hitZ >= 0.4375 && hitX <= 0.4375 && hitY <= 0.4375 && hitZ <= 0.5) {
                    return 0;
                } else if (hitX >= 0.0625 && hitY >= 0.0625 + 0.5 && hitZ >= 0.4375 && hitX <= 0.4375 && hitY <= 0.4375 + 0.5 && hitZ <= 0.5) {
                    return 1;

                } else if (hitX >= 0.0625 + 0.5 && hitY >= 0.0625 && hitZ >= 0.4375 && hitX <= 0.4375 + 0.5 && hitY <= 0.4375 && hitZ <= 0.5) {
                    return 2;
                } else if (hitX >= 0.0625 + 0.5 && hitY >= 0.0625 + 0.5 && hitZ >= 0.4375 && hitX <= 0.4375 + 0.5 && hitY <= 0.4375 + 0.5 && hitZ <= 0.5)
                    return 3;
                break;
            case SOUTH:
                if (hitX >= 0.5625 && hitY >= 0.0625 && hitZ >= 0.5 && hitX <= 0.9375 && hitY <= 0.4375 && hitZ <= 0.625) {
                    return 0;
                } else if (hitX >= 0.5625 && hitY >= 0.0625 + 0.5 && hitZ >= 0.5 && hitX <= 0.9375 && hitY <= 0.4375 + 0.5 && hitZ <= 0.625) {
                    return 1;
                } else if (hitX >= 0.5625 - 0.5 && hitY >= 0.0625 && hitZ >= 0.5 && hitX <= 0.9375 - 0.5 && hitY <= 0.4375 && hitZ <= 0.625) {
                    return 2;
                } else if (hitX >= 0.5625 - 0.5 && hitY >= 0.0625 + 0.5 && hitZ >= 0.5 && hitX <= 0.9375 - 0.5 && hitY <= 0.4375 + 0.5 && hitZ <= 0.625)
                    return 3;
                break;
            case EAST:
                if (hitX >= 0.5 && hitY >= 0.0625 && hitZ >= 0.0625 && hitX <= 0.625 && hitY <= 0.4375 && hitZ <= 0.4375) {
                    return 0;
                } else if (hitX >= 0.5 && hitY >= 0.0625 + 0.5 && hitZ >= 0.0625 && hitX <= 0.625 && hitY <= 0.4375 + 0.5 && hitZ <= 0.4375) {
                    return 1;
                } else if (hitX >= 0.5 && hitY >= 0.0625 && hitZ >= 0.0625 + 0.5 && hitX <= 0.625 && hitY <= 0.4375 && hitZ <= 0.4375 + 0.5) {
                    return 2;
                } else if (hitX >= 0.5 && hitY >= 0.0625 + 0.5 && hitZ >= 0.0625 + 0.5 && hitX <= 0.625 && hitY <= 0.4375 + 0.5 && hitZ <= 0.4375 + 0.5)
                    return 3;
                break;
            case WEST:
                if (hitX >= 0.4375 && hitY >= 0.0625 && hitZ >= 0.5625 && hitX <= 0.5 && hitY <= 0.4375 && hitZ <= 0.9375) {
                    return 0;
                } else if (hitX >= 0.4375 && hitY >= 0.0625 + 0.5 && hitZ >= 0.5625 && hitX <= 0.5 && hitY <= 0.4375 + 0.5 && hitZ <= 0.9375) {
                    return 1;
                } else if (hitX >= 0.4375 && hitY >= 0.0625 && hitZ >= 0.5625 - 0.5 && hitX <= 0.5 && hitY <= 0.4375 && hitZ <= 0.9375 - 0.5) {
                    return 2;
                } else if (hitX >= 0.4375 && hitY >= 0.0625 + 0.5 && hitZ >= 0.5625 - 0.5 && hitX <= 0.5 && hitY <= 0.4375 + 0.5 && hitZ <= 0.9375 - 0.5)
                    return 3;
                break;
        }
        return -1;
    }

    private BlockRayTraceResult rayTraceEyes(World world, PlayerEntity player, double length) {
        Vec3d eyePos = player.getEyePosition(1);
        Vec3d lookPos = player.getLook(1);
        Vec3d endPos = eyePos.add(lookPos.x * length, lookPos.y * length, lookPos.z * length);
        RayTraceContext context = new RayTraceContext(eyePos, endPos, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        return world.rayTraceBlocks(context);
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
    public void onReplaced(final BlockState state, final World world, final BlockPos pos,
                           final BlockState newState, final boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            final ShelfTileEntity tileEntity = (ShelfTileEntity) world.getTileEntity(pos);
            if (tileEntity != null) {
                dropItemHandlerContents(world, pos, tileEntity.getHandler());
                world.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    public static void dropItemHandlerContents(final World world, final BlockPos pos,
                                               final IItemHandler itemHandler) {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            final ItemStack stack = itemHandler.extractItem(slot, Integer.MAX_VALUE, false);
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return calculateFacing(context, true);
    }

    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }
}