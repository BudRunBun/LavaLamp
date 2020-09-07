package com.budrunbun.lavalamp.entity;

import com.budrunbun.lavalamp.entity.goal.ReturnToShopGoal;
import com.budrunbun.lavalamp.entity.goal.FixShopGoal;
import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GuardEntity extends CreatureEntity implements IShopEmployee {
    public BlockPos targetBlockPos;
    public BlockState targetBlockState;
    private static final DataParameter<BlockPos> CONTROLLER_POSITION = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> SHOP_BOUND_1 = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> SHOP_BOUND_2 = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);


    @Override
    protected void registerData() {
        this.dataManager.register(CONTROLLER_POSITION, new BlockPos(1, 0, 0));
        this.dataManager.register(SHOP_BOUND_1, new BlockPos(0, 1, 0));
        this.dataManager.register(SHOP_BOUND_2, new BlockPos(0, 0, 1));
        super.registerData();
    }

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
        this.goalSelector.addGoal(10, new ReturnToShopGoal(this));

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

    public void setControllerPosition(BlockPos blockpos) {
        this.dataManager.set(CONTROLLER_POSITION, blockpos);
    }

    public void setShopBounds(BlockPos firstBound, BlockPos secondBound) {
        this.dataManager.set(SHOP_BOUND_1, firstBound);
        this.dataManager.set(SHOP_BOUND_2, secondBound);
    }

    public AxisAlignedBB getShopBounds() {
        return new AxisAlignedBB(this.dataManager.get(SHOP_BOUND_1), this.dataManager.get(SHOP_BOUND_2));
    }

    @Override
    public void onDeath(@Nonnull DamageSource cause) {
        TileEntity tile = this.world.getTileEntity(this.dataManager.get(CONTROLLER_POSITION));

        if (tile != null && tile instanceof ShopControllerTileEntity) {
            ((ShopControllerTileEntity) tile).decreaseGuardAmount();
        }

        super.onDeath(cause);
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putInt("x", this.dataManager.get(CONTROLLER_POSITION).getX());
        compound.putInt("y", this.dataManager.get(CONTROLLER_POSITION).getY());
        compound.putInt("z", this.dataManager.get(CONTROLLER_POSITION).getZ());

        super.writeAdditional(compound);
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        this.dataManager.set(CONTROLLER_POSITION, new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z")));

        super.readAdditional(compound);
    }


}