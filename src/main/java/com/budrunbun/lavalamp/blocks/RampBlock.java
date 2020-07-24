package com.budrunbun.lavalamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RampBlock extends FacingBlock {

    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;

    private static VoxelShape SHAPE_NORTH_DOWN = VoxelShapes.empty();
    private static VoxelShape SHAPE_EAST_DOWN = VoxelShapes.empty();
    private static VoxelShape SHAPE_SOUTH_DOWN = VoxelShapes.empty();
    private static VoxelShape SHAPE_WEST_DOWN = VoxelShapes.empty();

    private static VoxelShape SHAPE_NORTH_UP = VoxelShapes.empty();
    private static VoxelShape SHAPE_EAST_UP = VoxelShapes.empty();
    private static VoxelShape SHAPE_SOUTH_UP = VoxelShapes.empty();
    private static VoxelShape SHAPE_WEST_UP = VoxelShapes.empty();

    private static final VoxelShape SHAPE_NORTH_DOUBLE;
    private static final VoxelShape SHAPE_EAST_DOUBLE;
    private static final VoxelShape SHAPE_SOUTH_DOUBLE;
    private static final VoxelShape SHAPE_WEST_DOUBLE;

    private static final VoxelShape BOTTOM_PART = Block.makeCuboidShape(0, 0, 0, 16, 8, 16);

    static {
        for (int p = 0; p <= 8; p++) {
            SHAPE_NORTH_DOWN = VoxelShapes.or(SHAPE_NORTH_DOWN, Block.makeCuboidShape(16, p, 16 - p * 2, 0, p + 1, 0));
            SHAPE_EAST_DOWN = VoxelShapes.or(SHAPE_EAST_DOWN, Block.makeCuboidShape(p * 2, p, 0, 16, p + 1, 16));
            SHAPE_SOUTH_DOWN = VoxelShapes.or(SHAPE_SOUTH_DOWN, Block.makeCuboidShape(0, p, p * 2, 16, p + 1, 16));
            SHAPE_WEST_DOWN = VoxelShapes.or(SHAPE_WEST_DOWN, Block.makeCuboidShape(16 - p * 2, p, 16, 0, p + 1, 0));

            SHAPE_NORTH_UP = VoxelShapes.or(SHAPE_NORTH_UP, Block.makeCuboidShape(16, p + 8, 16 - p * 2, 0, p + 1 + 8, 0));
            SHAPE_EAST_UP = VoxelShapes.or(SHAPE_EAST_UP, Block.makeCuboidShape(p * 2, p + 8, 0, 16, p + 1 + 8, 16));
            SHAPE_SOUTH_UP = VoxelShapes.or(SHAPE_SOUTH_UP, Block.makeCuboidShape(0, p + 8, p * 2, 16, p + 1 + 8, 16));
            SHAPE_WEST_UP = VoxelShapes.or(SHAPE_WEST_UP, Block.makeCuboidShape(16 - p * 2, p + 8, 16, 0, p + 1 + 8, 0));
        }

        SHAPE_EAST_DOUBLE = VoxelShapes.or(SHAPE_EAST_UP, BOTTOM_PART);
        SHAPE_WEST_DOUBLE = VoxelShapes.or(SHAPE_WEST_UP, BOTTOM_PART);
        SHAPE_SOUTH_DOUBLE = VoxelShapes.or(SHAPE_SOUTH_UP, BOTTOM_PART);
        SHAPE_NORTH_DOUBLE = VoxelShapes.or(SHAPE_NORTH_UP, BOTTOM_PART);
    }
    // TODO: Convex Hull

    public RampBlock() {
        super(Properties.create(Material.IRON));
        setRegistryName("ramp_block");
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(TYPE) == SlabType.BOTTOM) {
            switch (state.get(FACING)) {
                case SOUTH:
                    return SHAPE_SOUTH_DOWN;
                case EAST:
                    return SHAPE_EAST_DOWN;
                case WEST:
                    return SHAPE_WEST_DOWN;
                default:
                    return SHAPE_NORTH_DOWN;
            }
        } else if (state.get(TYPE) == SlabType.TOP) {
            switch (state.get(FACING)) {
                case SOUTH:
                    return SHAPE_SOUTH_UP;
                case EAST:
                    return SHAPE_EAST_UP;
                case WEST:
                    return SHAPE_WEST_UP;
                default:
                    return SHAPE_NORTH_UP;
            }
        } else {
            switch (state.get(FACING)) {
                case SOUTH:
                    return SHAPE_SOUTH_DOUBLE;
                case EAST:
                    return SHAPE_EAST_DOUBLE;
                case WEST:
                    return SHAPE_WEST_DOUBLE;
                default:
                    return SHAPE_NORTH_DOUBLE;
            }
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean func_220074_n(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItem();
        SlabType slabtype = state.get(TYPE);
        if (slabtype != SlabType.DOUBLE && itemstack.getItem() == this.asItem()) {
            if (useContext.replacingClickedOnBlock()) {
                boolean flag = useContext.getHitVec().y - (double) useContext.getPos().getY() > 0.5D;
                Direction direction = useContext.getFace();
                if (slabtype == SlabType.BOTTOM) {
                    return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        BlockState blockState2;
        Direction direction = context.getFace();
        if (blockstate.getBlock() == this) {
            blockState2 = blockstate.with(TYPE, SlabType.DOUBLE);
        } else {
            BlockState blockState1 = this.getDefaultState().with(TYPE, SlabType.BOTTOM);
            blockState2 = direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double) blockpos.getY() > 0.5D)) ? blockState1 : blockState1.with(TYPE, SlabType.TOP);
        }
        return blockstate.getBlock() == this && blockstate.get(FACING) == direction ? blockState2.with(FACING, direction) : blockState2.with(FACING, direction.getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
        super.fillStateContainer(builder);
    }

    /*
    @Override
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
        //Makes the ground stay bright
        return 1.0F;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    */
}