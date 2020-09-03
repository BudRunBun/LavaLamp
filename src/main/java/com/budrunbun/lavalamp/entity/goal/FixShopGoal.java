package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class FixShopGoal extends Goal {
    private final GuardEntity guard;

    public FixShopGoal(GuardEntity guard) {
        this.guard = guard;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return guard.targetBlockPos != null && guard.targetBlockState != null;
    }

    @Override
    public void startExecuting() {
        this.guard.getNavigator().tryMoveToXYZ(guard.targetBlockPos.getX(), guard.targetBlockPos.getY(), guard.targetBlockPos.getZ(), 1);
    }

    @Override
    public void tick() {
        if (guard.targetBlockPos != null && guard.targetBlockState != null) {
            if (isNearby()) {
                this.guard.world.setBlockState(guard.targetBlockPos, guard.targetBlockState);
            }

            if (this.guard.world.getBlockState(guard.targetBlockPos).getBlock() == guard.targetBlockState.getBlock()) {
                this.guard.targetBlockPos = null;
                this.guard.targetBlockState = null;
                this.guard.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
    }

    private boolean isNearby() {
        return Math.abs(this.guard.getPosition().getX() - guard.targetBlockPos.getX()) <= 1 && Math.abs(this.guard.getPosition().getY() - guard.targetBlockPos.getY()) <= 1 && Math.abs(this.guard.getPosition().getZ() - guard.targetBlockPos.getZ()) <= 1;
    }
}