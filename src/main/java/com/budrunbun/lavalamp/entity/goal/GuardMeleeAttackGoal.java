package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class GuardMeleeAttackGoal extends MeleeAttackGoal {
    GuardEntity guard;

    public GuardMeleeAttackGoal(GuardEntity guard, double speedIn, boolean useLongMemory) {
        super(guard, speedIn, useLongMemory);
        this.guard = guard;
    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute()/* && !guard.isAnimationGoing()*/;
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        //if (!guard.isAnimationGoing()) {
            guard.doSwordAttackAnimation();
            super.checkAndPerformAttack(enemy, distToEnemySqr);
        //}
    }
}
