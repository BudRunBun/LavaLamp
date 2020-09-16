package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class ReturnToShopGoal extends Goal {

    private final GuardEntity guard;

    public ReturnToShopGoal(GuardEntity guard) {
        this.guard = guard;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return !(this.guard.getPosition().getX() == this.guard.getPostPosition().getX() &&
                this.guard.getPosition().getY() == this.guard.getPostPosition().getY() &&
                this.guard.getPosition().getZ() == this.guard.getPostPosition().getZ());
    }

    @Override
    public void startExecuting() {
        BlockPos pos = this.guard.getPostPosition();
        this.guard.getNavigator().tryMoveToXYZ(pos.getX() + 1, pos.getY(), pos.getZ() + 1, 1);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.guard.getAttackTarget() == null;
    }
}