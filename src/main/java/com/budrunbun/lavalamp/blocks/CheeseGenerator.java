package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import com.budrunbun.lavalamp.tileEntities.CheeseGeneratorTE;
import com.budrunbun.lavalamp.tileEntities.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

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

/*
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        return Direction.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }*/


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
            return true;
        } else {
            INamedContainerProvider iNamedContainerProvider = this.getContainer(state, worldIn, pos);
            if (iNamedContainerProvider instanceof CheeseGeneratorContainer) {
                player.openContainer(iNamedContainerProvider);
                player.addStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
            }
        }
        return true;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CheeseGeneratorTE) {
                ((CheeseGeneratorTE)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }
}


