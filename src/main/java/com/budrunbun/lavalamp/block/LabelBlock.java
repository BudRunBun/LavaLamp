package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LabelBlock extends HorizontalFacingBlock {
    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(16, 0, 1, 0, 16, 0);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(15, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(1, 0, 16, 0, 16, 0);

    private static final IProperty<Integer> PART = IntegerProperty.create("part", 1, 3);

    public LabelBlock() {
        super(Block.Properties.from(Blocks.OAK_WOOD));
        setRegistryName("label");
        this.setDefaultState(this.getDefaultState().with(PART, 1));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            default:
                return SHAPE_WEST;
        }
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART);
        super.fillStateContainer(builder);
    }

    /*@SuppressWarnings("deprecation")
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = Minecraft.getInstance().player.getHorizontalFacing().getOpposite();
        switch (state.get(PART)) {
            case 1:
                if (!worldIn.getBlockState(pos.offset(direction.rotateY())).isAir() || worldIn.getBlockState(pos.offset(direction.rotateY(), 2)).isAir()) {
                    return false;
                }
                break;
            case 2:
                if (!worldIn.getBlockState(pos.offset(direction.rotateY())).isAir() || worldIn.getBlockState(pos.offset(direction.rotateY(), -1)).isAir()) {
                    return false;
                }
                break;
            case 3:
                if (!worldIn.getBlockState(pos.offset(direction.rotateY(), -1)).isAir() || worldIn.getBlockState(pos.offset(direction.rotateY(), -2)).isAir()) {
                    return false;
                }
                break;
        }
        return true;
    }*/

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY()), worldIn.getBlockState(pos).with(PART, 2));
        worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY(), 2), worldIn.getBlockState(pos).with(PART, 3));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        switch (state.get(PART)) {
            case 1:
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY()), Blocks.AIR.getDefaultState());
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY(), 2), Blocks.AIR.getDefaultState());
                break;
            case 2:
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY()), Blocks.AIR.getDefaultState());
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY(), -1), Blocks.AIR.getDefaultState());
                break;
            case 3:
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY(), -1), Blocks.AIR.getDefaultState());
                worldIn.setBlockState(pos.offset(worldIn.getBlockState(pos).get(FACING).rotateY(), -2), Blocks.AIR.getDefaultState());
                break;
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return calculateFacing(context, true);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
}
