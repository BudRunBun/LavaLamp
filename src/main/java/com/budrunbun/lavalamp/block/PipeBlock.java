package com.budrunbun.lavalamp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PipeBlock extends Block {
    //TODO: Does this need to stay only as a decoration?

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    private static VoxelShape SHAPE = Block.makeCuboidShape(5, 5, 5, 11, 11, 11);

    private static final VoxelShape SHAPE_UP = Block.makeCuboidShape(4, 16, 4, 12, 11, 12);
    private static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(12, 0, 12, 4, 5, 4);

    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(12, 4, 0, 4, 12, 5);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(4, 4, 16, 12, 12, 11);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(16, 4, 12, 11, 12, 4);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0, 4, 4, 5, 12, 12);

    public PipeBlock() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
        setRegistryName("pipe");
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(NORTH)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_NORTH);
        }

        if (state.get(SOUTH)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_SOUTH);
        }

        if (state.get(EAST)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_EAST);
        }

        if (state.get(WEST)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_WEST);
        }

        if (state.get(UP)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_UP);
        }

        if (state.get(DOWN)) {
            SHAPE = VoxelShapes.or(SHAPE, SHAPE_DOWN);
        }

        return SHAPE;
    }

    public BlockState makeConnections(IBlockReader world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        Block block1 = world.getBlockState(pos.up()).getBlock();
        Block block2 = world.getBlockState(pos.north()).getBlock();
        Block block3 = world.getBlockState(pos.east()).getBlock();
        Block block4 = world.getBlockState(pos.south()).getBlock();
        Block block5 = world.getBlockState(pos.west()).getBlock();
        return this.getDefaultState().with(DOWN, block == this).with(UP, block1 == this).with(NORTH, block2 == this).with(EAST, block3 == this).with(SOUTH, block4 == this).with(WEST, block5 == this);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos north = pos.north();
        if (worldIn.getBlockState(north).getBlock() == this) {
            worldIn.setBlockState(north, worldIn.getBlockState(north).with(SOUTH, true));
        }

        BlockPos south = pos.south();
        if (worldIn.getBlockState(south).getBlock() == this) {
            worldIn.setBlockState(south, worldIn.getBlockState(south).with(NORTH, true));
        }

        BlockPos east = pos.east();
        if (worldIn.getBlockState(east).getBlock() == this) {
            worldIn.setBlockState(east, worldIn.getBlockState(east).with(WEST, true));
        }

        BlockPos west = pos.west();
        if (worldIn.getBlockState(west).getBlock() == this) {
            worldIn.setBlockState(west, worldIn.getBlockState(west).with(EAST, true));
        }

        BlockPos up = pos.up();
        if (worldIn.getBlockState(up).getBlock() == this) {
            worldIn.setBlockState(up, worldIn.getBlockState(up).with(DOWN, true));
        }

        BlockPos down = pos.down();
        if (worldIn.getBlockState(down).getBlock() == this) {
            worldIn.setBlockState(down, worldIn.getBlockState(down).with(UP, true));
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos north = pos.north();
        if (worldIn.getBlockState(north).getBlock() == this) {
            worldIn.setBlockState(north, worldIn.getBlockState(north).with(SOUTH, false));
        }

        BlockPos south = pos.south();
        if (worldIn.getBlockState(south).getBlock() == this) {
            worldIn.setBlockState(south, worldIn.getBlockState(south).with(NORTH, false));
        }

        BlockPos east = pos.east();
        if (worldIn.getBlockState(east).getBlock() == this) {
            worldIn.setBlockState(east, worldIn.getBlockState(east).with(WEST, false));
        }

        BlockPos west = pos.west();
        if (worldIn.getBlockState(west).getBlock() == this) {
            worldIn.setBlockState(west, worldIn.getBlockState(west).with(EAST, false));
        }

        BlockPos up = pos.up();
        if (worldIn.getBlockState(up).getBlock() == this) {
            worldIn.setBlockState(up, worldIn.getBlockState(up).with(DOWN, false));
        }

        BlockPos down = pos.down();
        if (worldIn.getBlockState(down).getBlock() == this) {
            worldIn.setBlockState(down, worldIn.getBlockState(down).with(UP, false));
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
