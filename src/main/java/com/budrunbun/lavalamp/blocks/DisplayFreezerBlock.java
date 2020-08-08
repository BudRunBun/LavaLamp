package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.tileEntities.ModTileEntities;
import com.budrunbun.lavalamp.tileEntities.DisplayFreezerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisplayFreezerBlock extends HorizontalFacingBlock {
    /*
    |1|2|
    |0|3|
*/
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public DisplayFreezerBlock() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F));
        setRegistryName("display_freezer");
        this.setDefaultState(this.stateContainer.getBaseState().with(OPEN, false).with(BOTTOM, true));
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(OPEN, BOTTOM);
        super.fillStateContainer(builder);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), state.with(BOTTOM, false), 3);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean bottom = state.get(BOTTOM);
        BlockPos blockpos = bottom ? pos.up() : pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.get(BOTTOM) != bottom) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            ItemStack itemstack = player.getHeldItemMainhand();
            if (!worldIn.isRemote && !player.isCreative()) {
                Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
                Block.spawnDrops(blockstate, worldIn, blockpos, null, player, itemstack);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        if (blockpos.getY() < 255 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)) {
            return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(OPEN, false).with(BOTTOM, true);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.DISPLAY_FREEZER_TE.create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            if (state.get(OPEN)) {
                if (!player.getHeldItemMainhand().isEmpty()) {
                    int slot = getSlot(hit, state, pos);

                    if (slot == -1) {
                        return false;
                    }

                    DisplayFreezerTileEntity freezer = (DisplayFreezerTileEntity) worldIn.getTileEntity(pos);
                    ItemStackHandler handler = freezer.getHandler();

                    if (handler.getStackInSlot(slot).isEmpty()) {
                        Item item = player.getHeldItemMainhand().getItem();
                        if (!player.isCreative()) {
                            ItemStack stack = player.getHeldItemMainhand();
                            stack.shrink(1);
                            player.setHeldItem(Hand.MAIN_HAND, stack);
                        }
                        handler.setStackInSlot(slot, new ItemStack(item, 1));
                        freezer.setHandler(handler);
                        worldIn.notifyBlockUpdate(pos, state, state, 3);
                    }
                    return true;
                }
                return false;
            }
            state = state.cycle(OPEN);
            boolean bottom = state.get(BOTTOM);
            worldIn.setBlockState(pos, state, 10);
            worldIn.setBlockState(bottom ? pos.up() : pos.down(), state.cycle(BOTTOM), 10);
            return true;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockClicked(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player) {
        BlockRayTraceResult hit = rayTraceEyes(worldIn, player, player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue() + 1);
        int slot = getSlot(hit, state, pos);
        DisplayFreezerTileEntity freezer = (DisplayFreezerTileEntity) worldIn.getTileEntity(pos);
        ItemStackHandler handler = freezer.getHandler();

        if (slot != -1 && !handler.getStackInSlot(slot).isEmpty()) {
            ItemStack item = handler.getStackInSlot(slot);

            if (!player.isCreative()) {
                if (!player.inventory.addItemStackToInventory(item)) {
                    dropItemStack(worldIn, pos, item);
                }
            }

            handler.setStackInSlot(slot, ItemStack.EMPTY);
            worldIn.notifyBlockUpdate(pos, state, state, 3);
            freezer.setHandler(handler);
        }
    }

    private int getSlot(BlockRayTraceResult hit, BlockState state, BlockPos pos) {
        double hitX = hit.getHitVec().getX() - pos.getX();
        double hitY = hit.getHitVec().getY() - pos.getY();
        double hitZ = hit.getHitVec().getZ() - pos.getZ();

        if (!state.get(BOTTOM)) {
            switch (state.get(HorizontalFacingBlock.FACING)) {
                case NORTH:
                    if (hitX >= 1 / 16.0 && hitY >= 0.5 / 16.0 && hitZ >= 15 / 16.0 && hitX <= 8 / 16.0 && hitY <= 7 / 16.0 && hitZ <= 1) {
                        return 0;
                    } else if (hitX >= 1 / 16.0 && hitY >= 0.5 && hitZ >= 15 / 16.0 && hitX <= 0.5 && hitY <= 14 / 16.0 && hitZ <= 1) {
                        return 1;
                    } else if (hitX >= 0.5 && hitY >= 0.5 && hitZ >= 15 / 16.0 && hitX <= 15 / 16.0 && hitY <= 14 / 16.0 && hitZ <= 1) {
                        return 2;
                    } else if (hitX >= 0.5 && hitY >= 0.5 / 16.0 && hitZ >= 15 / 16.0 && hitX <= 15 / 16.0 && hitY <= 7 / 16.0 && hitZ <= 1)
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
        } else {
            switch (state.get(HorizontalFacingBlock.FACING)) {
                case NORTH:
                    if (hitX >= 1 / 16.0 && hitY >= 2 / 16.0 && hitZ >= 15 / 16.0 && hitX <= 0.5 && hitY <= 0.5 && hitZ <= 1) {
                        return 0;
                    } else if (hitX >= 1 / 16.0 && hitY >= 9 / 16.0 && hitZ >= 15 / 16.0 && hitX <= 0.5 && hitY <= 15.5 / 16.0 && hitZ <= 1) {
                        return 1;
                    } else if (hitX >= 0.5 && hitY >= 9/16.0 && hitZ >= 15/16.0 && hitX <= 15/16.0 && hitY <= 15.5/16 && hitZ <= 1) {
                        return 2;
                    } else if (hitX >= 0.5 && hitY >= 2/16.0 && hitZ >= 15/16.0 && hitX <= 15/16.0 && hitY <= 0.5 && hitZ <= 1)
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

    private void dropItemStack(World world, BlockPos pos, @Nonnull ItemStack stack) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .3f, pos.getZ() + .5f, stack);
        Vec3d motion = entity.getMotion();
        entity.addVelocity(-motion.x, -motion.y, -motion.z);
        world.addEntity(entity);
    }
}