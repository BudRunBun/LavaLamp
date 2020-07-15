package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.tileEntities.CheeseGeneratorTileEntity;
import com.budrunbun.lavalamp.tileEntities.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class CheeseGenerator extends Block {

    public CheeseGenerator() {
        super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f));
        setRegistryName("cheese_generator");
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        return Direction.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.CHEESE_GENERATOR_TE.create();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
            }
        }
        return true;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (placer != null) {
            worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, placer)), 2);
        }
    }

    @Override
    public void onReplaced(final BlockState state, final World world, final BlockPos pos, final BlockState newState, final boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            final CheeseGeneratorTileEntity tileEntity = (CheeseGeneratorTileEntity) world.getTileEntity(pos);
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
}