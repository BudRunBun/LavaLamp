package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FixShopGoal extends Goal {
    private final GuardEntity guard;

    public FixShopGoal(GuardEntity guard) {
        this.guard = guard;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return guard.getTargetBlockPos() != null && guard.getTargetBlockState() != null;
    }

    @Override
    public void startExecuting() {
        this.guard.getNavigator().tryMoveToXYZ(guard.getTargetBlockPos().getX(), guard.getTargetBlockPos().getY(), guard.getTargetBlockPos().getZ(), 1);
    }

    @Override
    public void tick() {
        if (guard.getTargetBlockPos() != null && guard.getTargetBlockState() != null) {
            if (isNearby()) {
                this.guard.world.setBlockState(guard.getTargetBlockPos(), guard.getTargetBlockState());
            }

            if (this.guard.world.getBlockState(guard.getTargetBlockPos()).getBlock() == guard.getTargetBlockState().getBlock()) {
                this.guard.setTargetBlockPos(null);
                this.guard.setTargetBlockState(null);
            }
        }
    }

    private boolean isNearby() {
        return Math.abs(this.guard.getPosition().getX() - guard.getTargetBlockPos().getX()) <= 1 && Math.abs(this.guard.getPosition().getY() - guard.getTargetBlockPos().getY()) <= 1 && Math.abs(this.guard.getPosition().getZ() - guard.getTargetBlockPos().getZ()) <= 1;
    }
}