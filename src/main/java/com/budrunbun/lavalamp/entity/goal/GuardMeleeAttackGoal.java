package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public class GuardMeleeAttackGoal extends MeleeAttackGoal {
    private final GuardEntity guard;

    public GuardMeleeAttackGoal(GuardEntity guard, double speedIn, boolean useLongMemory) {
        super(guard, speedIn, useLongMemory);
        this.guard = guard;
    }

    @Override
    public void tick() {
        if (this.guard.isChoppingAnimationGoing()) {
            if (!this.guard.isAnimationReversed()) {
                this.guard.chop();
            } else {
                this.guard.raiseArm();
            }
        }

        //System.out.println(this.guard.getAnimationProgress());

        super.tick();
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        if (!this.guard.isChoppingAnimationGoing() && distToEnemySqr <= this.getAttackReachSqr(enemy) && this.attackTick <= 0) {
            this.guard.startChoppingAnimation();
        }

        if (this.guard.canChop()) {
            this.attackTick = (int) GuardEntity.CHOP_DURATION;
            this.attacker.swingArm(Hand.MAIN_HAND);
            this.attacker.attackEntityAsMob(enemy);

            this.guard.reverseAnimation();
        }
    }
}
