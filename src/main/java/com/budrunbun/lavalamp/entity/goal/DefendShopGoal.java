package com.budrunbun.lavalamp.entity.goal;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;
import java.util.UUID;

public class DefendShopGoal extends TargetGoal {
    private final GuardEntity guard;
    private UUID criminalID;

    public DefendShopGoal(MobEntity mobIn) {
        super(mobIn, false, false);
        this.guard = (GuardEntity) mobIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean shouldExecute() {
        return criminalID != null;
    }

    private void setCriminalID(PlayerEntity criminalIn) {
        this.criminalID = criminalIn.getUniqueID();
    }

    public void startExecuting() {
        this.guard.setAttackTarget(this.guard.world.getPlayerByUuid(criminalID));
        super.startExecuting();
    }
}
