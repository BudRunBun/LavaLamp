package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ProtectWithShieldGoal extends Goal {

    private final GuardEntity guard;
    private LivingEntity attackTarget;

    public ProtectWithShieldGoal(GuardEntity guard) {
        this.guard = guard;
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.guard.getAttackTarget();
        return livingentity != null && this.guard.getDistanceSq(livingentity) < 7.0D;
    }

    @Override
    public void startExecuting() {
        this.attackTarget = guard.getAttackTarget();
    }

    @Override
    public void resetTask() {
        this.guard.setDegrees(0);
        this.attackTarget = null;
    }

    @Override
    public void tick() {
        if (this.attackTarget == null) {
            this.guard.setDegrees(0);
        } else if (this.guard.getDistanceSq(this.attackTarget) > 7.0D) {
            this.guard.setDegrees(0);
        } else {
            this.guard.setDegrees(90);
            System.out.println("set to 90");
        }
    }
}
