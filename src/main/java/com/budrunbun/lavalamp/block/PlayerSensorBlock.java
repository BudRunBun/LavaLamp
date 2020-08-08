package com.budrunbun.lavalamp.block;

import com.budrunbun.lavalamp.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerSensorBlock extends HorizontalFacingBlock {
    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(3.5, 3, 0, 13, 5, 2);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(12.5, 3, 16, 3, 5, 14);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0, 3, 3, 2, 5, 12.5);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(16, 3, 13, 14, 5, 3.5);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public PlayerSensorBlock() {
        super(Block.Properties.create(Material.IRON));
        setRegistryName("player_sensor");
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case WEST:
                return SHAPE_WEST;
            case EAST:
                return SHAPE_EAST;
            default:
                return VoxelShapes.empty();
        }
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return calculateFacing(context, true);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.PLAYER_SENSOR_TE.create();
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(POWERED);
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 15 : 0;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.get(POWERED)) {
                this.updateNeighbors(worldIn, pos);
            }

            super.onReplaced(state, worldIn, pos, newState, false);
        }
    }

    public void updateNeighbors(World world, BlockPos pos) {
        world.notifyNeighborsOfStateChange(pos, this);
        if (world.getBlockState(pos).getBlock() instanceof HorizontalFacingBlock) {
            world.notifyNeighborsOfStateChange(pos.offset(world.getBlockState(pos).get(FACING)), this);
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        //Makes the ground stay bright
        return 1.0F;
    }
}
