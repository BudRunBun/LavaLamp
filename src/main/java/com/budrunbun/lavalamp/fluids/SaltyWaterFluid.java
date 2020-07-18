package com.budrunbun.lavalamp.fluids;

import com.budrunbun.lavalamp.blocks.ModBlocks;
import com.budrunbun.lavalamp.items.ModItems;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Random;

public abstract class SaltyWaterFluid extends ForgeFlowingFluid {
    private static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> ModFluids.SALTY_WATER, () -> ModFluids.FLOWING_SALTY_WATER,
            net.minecraftforge.fluids.FluidAttributes.builder(
                    //using transparent water texture with custom color
                    new net.minecraft.util.ResourceLocation("block/water_still"),
                    new net.minecraft.util.ResourceLocation("block/water_flow"))
                    //aRGB format
                    .color(0xFFfc8803)
                    .translationKey("block.lavalamp.salty_water_block"))
            .bucket(() -> ModItems.SALTY_WATER_BUCKET)
            .block(() -> ModBlocks.SALTY_WATER_BLOCK)
            .slopeFindDistance(4)
            .levelDecreasePerBlock(1)
            .explosionResistance(100F);

    protected SaltyWaterFluid(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(World worldIn, BlockPos pos, IFluidState state, Random random) {
        if (!state.isSource() && !state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        }/* else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.UNDERWATER, (float) pos.getX() + random.nextFloat(), (float) pos.getY() + random.nextFloat(), (float) pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }*/

    }



    public static class Flowing extends ForgeFlowingFluid.Flowing {
        public Flowing() {
            super(properties);
        }
    }

    public static class Source extends ForgeFlowingFluid.Source {
        public Source() {
            super(properties);
        }
    }
}
