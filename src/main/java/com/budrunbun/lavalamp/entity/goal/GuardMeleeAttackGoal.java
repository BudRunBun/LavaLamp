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
        if (this.guard.isChoppingAnimationGoing(Hand.MAIN_HAND)) {
            if (!this.guard.isAnimationReversed(Hand.MAIN_HAND)) {
                this.guard.chop(Hand.MAIN_HAND);
            } else {
                this.guard.raiseArm(Hand.MAIN_HAND);
            }
        }


        if (this.guard.isChoppingAnimationGoing(Hand.OFF_HAND)) {
            if (!this.guard.isAnimationReversed(Hand.OFF_HAND)) {
                this.guard.chop(Hand.OFF_HAND);
            } else {
                this.guard.raiseArm(Hand.OFF_HAND);
            }
        }

        System.out.println(this.guard.getAnimationProgress(Hand.OFF_HAND));

        super.tick();
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        if (!this.guard.isChoppingAnimationGoing(Hand.MAIN_HAND) && distToEnemySqr <= this.getAttackReachSqr(enemy) && this.attackTick <= 0) {
            this.guard.startChoppingAnimation(Hand.MAIN_HAND);
        }

        if (this.guard.canChop(Hand.MAIN_HAND)) {
            this.attackTick = (int) this.guard.CHOP_DURATION;
            this.attacker.swingArm(Hand.MAIN_HAND);
            this.attacker.attackEntityAsMob(enemy);

            this.guard.reverseAnimation(Hand.MAIN_HAND);

            if (!this.guard.hasShield()) {
                this.guard.startChoppingAnimation(Hand.OFF_HAND);
            }
        }

        if (this.guard.canChop(Hand.OFF_HAND) && !this.guard.hasShield()) {
            this.attacker.swingArm(Hand.OFF_HAND);
            this.attacker.attackEntityAsMob(enemy);

            this.guard.reverseAnimation(Hand.OFF_HAND);
        }
    }
}
