package com.budrunbun.lavalamp.entity;

import com.budrunbun.lavalamp.entity.goal.FixShopGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuardEntity extends CreatureEntity implements IShopEmployee{
    public BlockPos targetBlockPos;
    public BlockState targetBlockState;

    public GuardEntity(EntityType<? extends GuardEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FixShopGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23F);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.world.setEntityState(this, (byte) 4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (2.5 + this.rand.nextInt(4)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }
}