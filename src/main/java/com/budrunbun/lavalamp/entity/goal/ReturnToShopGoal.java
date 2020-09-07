package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class ReturnToShopGoal extends Goal {

    private final GuardEntity guard;

    public ReturnToShopGoal(GuardEntity guard) {
        this.guard = guard;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return !guard.getShopBounds().contains(guard.getPositionVec());
    }

    @Override
    public void startExecuting() {
        AxisAlignedBB aabb = guard.getShopBounds();
        this.guard.getNavigator().tryMoveToXYZ(MathHelper.average(new long[]{(long) aabb.maxX, (long) aabb.minX}), MathHelper.average(new long[]{(long) aabb.maxY, (long) aabb.minY}), MathHelper.average(new long[]{(long) aabb.maxZ, (long) aabb.minZ}), 1);
    }


}
