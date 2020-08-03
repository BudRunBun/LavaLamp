package com.budrunbun.lavalamp.blocks;

import com.budrunbun.lavalamp.fluids.SaltyWaterFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SaltyWaterBlock extends FlowingFluidBlock {

    public SaltyWaterBlock() {
        super(SaltyWaterFluid.Source::new, Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
        setRegistryName("salty_water_block");
    }

    @Override
    public void onEntityCollision(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.onEnterBubbleColumn(false);
        }
    }
}
