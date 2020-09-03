package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class FixShopGoal extends Goal {
    private final GuardEntity guard;
    private BlockPos targetBlockPos;
    private BlockState targetBlockState;

    public FixShopGoal(GuardEntity guard) {
        this.guard = guard;

        if (guard.getTargetBlockPos() != null && guard.getTargetBlockState() != null) {
            this.targetBlockPos = guard.getTargetBlockPos();
            this.targetBlockState = guard.getTargetBlockState();
        }

        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return guard.world.getBlockState(targetBlockPos).getBlock() != targetBlockState.getBlock();
    }

    @Override
    public void tick() {
        if (isNearby()) {
            this.guard.world.setBlockState(targetBlockPos, targetBlockState);
        }

        if (this.guard.world.getBlockState(targetBlockPos).getBlock() == targetBlockState.getBlock()) {
            this.guard.setTargetBlockPos(null);
            this.guard.setTargetBlockState(null);
        }
    }

    private boolean isNearby() {
        return this.guard.getPosition().getX() - targetBlockPos.getX() <= 1 && this.guard.getPosition().getY() - targetBlockPos.getY() <= 1 && this.guard.getPosition().getZ() - targetBlockPos.getZ() <= 1;
    }
}