package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ProtectWithShieldGoal extends Goal {
    private final GuardEntity guard;
    private LivingEntity attackTarget;

    public ProtectWithShieldGoal(GuardEntity guard) {
        this.guard = guard;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.guard.getAttackTarget();
        return livingentity != null && this.guard.getDistanceSq(livingentity) < 3.5D && !this.guard.isShieldEquipped;
    }

    @Override
    public void startExecuting() {
        guard.unequipShield();
        this.attackTarget = this.guard.getAttackTarget();
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.guard.unequipShield();
    }

    @Override
    public void tick() {
        if (this.attackTarget == null)
            this.guard.unequipShield();
        else if (this.guard.getDistanceSq(this.attackTarget) > 3.5D)
            this.guard.unequipShield();
        else
            this.guard.equipShield();
    }
}