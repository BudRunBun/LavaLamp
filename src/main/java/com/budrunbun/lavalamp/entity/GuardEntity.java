package com.budrunbun.lavalamp.entity;

import com.budrunbun.lavalamp.entity.goal.GuardMeleeAttackGoal;
import com.budrunbun.lavalamp.entity.goal.ProtectWithShieldGoal;
import com.budrunbun.lavalamp.entity.goal.ReturnToShopGoal;
import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class GuardEntity extends CreatureEntity implements IShopEmployee {
    public float CHOP_DURATION = this.hasShield() ? 20F : 10F;

    public BlockPos targetBlockPos;
    public BlockState targetBlockState;
    private static final DataParameter<BlockPos> CONTROLLER_POSITION = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> SHOP_BOUND_1 = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> SHOP_BOUND_2 = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> POST_POSITION = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<ItemStack> SHIELD = EntityDataManager.createKey(GuardEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<ItemStack> MELEE_WEAPON = EntityDataManager.createKey(GuardEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Boolean> SHIELD_EQUIPPED = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> RIGHT_ARM_ANIMATION_PROGRESS = EntityDataManager.createKey(GuardEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> LEFT_ARM_ANIMATION_PROGRESS = EntityDataManager.createKey(GuardEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> RIGHT_HAND_REVERSE_ANIMATION = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEFT_HAND_REVERSE_ANIMATION = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);

    private final Predicate<LivingEntity> isInShop = entity -> this.getShopBounds().contains(entity.getPositionVec());

    @Override
    protected void registerData() {
        this.dataManager.register(CONTROLLER_POSITION, new BlockPos(0, 0, 0));
        this.dataManager.register(POST_POSITION, new BlockPos(1, 0, 0));
        this.dataManager.register(SHOP_BOUND_1, new BlockPos(0, 1, 0));
        this.dataManager.register(SHOP_BOUND_2, new BlockPos(0, 0, 1));
        this.dataManager.register(SHIELD, /*new ItemStack(Items.SHIELD)*/ ItemStack.EMPTY);
        this.dataManager.register(MELEE_WEAPON, new ItemStack(Items.DIAMOND_AXE));
        this.dataManager.register(SHIELD_EQUIPPED, false);
        this.dataManager.register(RIGHT_ARM_ANIMATION_PROGRESS, 0F);
        this.dataManager.register(LEFT_ARM_ANIMATION_PROGRESS, 0F);
        this.dataManager.register(RIGHT_HAND_REVERSE_ANIMATION, false);
        this.dataManager.register(LEFT_HAND_REVERSE_ANIMATION, false);

        super.registerData();
    }

    public GuardEntity(EntityType<? extends GuardEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new GuardMeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new ProtectWithShieldGoal(this));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new ReturnToShopGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 228, false, false, isInShop));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, false));
    }

    public ItemStack getMeleeWeapon() {
        return this.dataManager.get(MELEE_WEAPON);
    }

    public ItemStack getShield() {
        return this.dataManager.get(SHIELD);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5000);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.world.setEntityState(this, (byte) 4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 9F + this.rand.nextInt(4));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        Entity attacker = source.getTrueSource();

        if (attacker instanceof LivingEntity) {
            if (!this.isShieldEquipped()) {
                return super.attackEntityFrom(source, amount);
            } else if (attacker.getHorizontalFacing() != this.getHorizontalFacing().getOpposite()) {
                return super.attackEntityFrom(source, amount);
            }
        } else {
            return super.attackEntityFrom(source, amount);
        }

        this.damageShield(amount);

        return false;
    }

    @Override
    protected void damageShield(float damage) {
        if (damage >= 3.0F && this.isAggressive()) {
            int i = 1 + MathHelper.floor(damage);

            ItemStack shield = this.getShield();
            shield.damageItem(i, this, entity -> entity.sendBreakAnimation(Hand.OFF_HAND));
            this.dataManager.set(SHIELD, shield);

            if (!this.hasShield()) {
                this.dataManager.set(SHIELD, ItemStack.EMPTY);
                this.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
                this.activeItemStack = ItemStack.EMPTY;
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
            }
        }
    }

    @Override
    protected void blockUsingShield(@Nonnull LivingEntity entity) {
        super.blockUsingShield(entity);

        if (entity.getHeldItemMainhand().canDisableShield(this.getShield(), this, entity)) {
            this.disableShield(true);
        }
    }

    public void disableShield(boolean p_190777_1_) {
        float f = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
        if (p_190777_1_) {
            f += 0.75F;
        }

        if (this.rand.nextFloat() < f) {
            this.resetActiveHand();
            this.world.setEntityState(this, (byte) 30);
        }

    }

    public void setControllerPosition(BlockPos blockpos) {
        this.dataManager.set(CONTROLLER_POSITION, blockpos);
    }

    public BlockPos getControllerPosition() {
        return this.dataManager.get(CONTROLLER_POSITION);
    }

    public void setPostPosition(BlockPos pos) {
        this.dataManager.set(POST_POSITION, pos);
    }

    public BlockPos getPostPosition() {
        return this.dataManager.get(POST_POSITION);
    }

    public boolean isAnimationReversed(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return this.dataManager.get(RIGHT_HAND_REVERSE_ANIMATION);
        }

        return this.dataManager.get(LEFT_HAND_REVERSE_ANIMATION);
    }

    public void reverseAnimation(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.dataManager.set(RIGHT_HAND_REVERSE_ANIMATION, true);
        } else {
            this.dataManager.set(LEFT_HAND_REVERSE_ANIMATION, true);
        }
    }

    public void undoAnimationReverse(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.dataManager.set(RIGHT_HAND_REVERSE_ANIMATION, false);
        } else {
            this.dataManager.set(LEFT_HAND_REVERSE_ANIMATION, false);
        }
    }

    public void equipShield() {
        this.dataManager.set(SHIELD_EQUIPPED, true);
    }

    public void unequipShield() {
        this.dataManager.set(SHIELD_EQUIPPED, false);
    }

    public boolean isShieldEquipped() {
        return this.dataManager.get(SHIELD_EQUIPPED) && this.hasShield();
    }

    public float getAnimationProgress(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return this.dataManager.get(RIGHT_ARM_ANIMATION_PROGRESS);
        }
        return this.dataManager.get(LEFT_ARM_ANIMATION_PROGRESS);
    }

    public void startChoppingAnimation(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (this.getAnimationProgress(hand) == 0) {
                this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, 1E-6F);
            }
        } else {
            if (!this.hasShield() && this.getAnimationProgress(hand) == 0) {
                this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, 1E-6F);
            }
        }
    }

    public boolean canChop(Hand hand) {
        return this.getAnimationProgress(hand) == 1;
    }

    public boolean isChoppingAnimationGoing(Hand hand) {
        return this.getAnimationProgress(hand) > 0;
    }

    public void chop(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, this.getAnimationProgress(hand) + 1 / CHOP_DURATION);

            if (this.getAnimationProgress(hand) > 1) {
                this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, 1F);
            }
        } else {
            this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, this.getAnimationProgress(hand) + 1 / CHOP_DURATION);

            if (this.getAnimationProgress(hand) > 1) {
                this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, 1F);
            }

        }
    }

    public void raiseArm(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, this.getAnimationProgress(hand) - 1 / (2 * CHOP_DURATION));

            if (this.getAnimationProgress(hand) < 0) {
                this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, 0F);
                this.undoAnimationReverse(hand);
            }
        } else {
            this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, this.getAnimationProgress(hand) - 1 / (2 * CHOP_DURATION));

            if (this.getAnimationProgress(hand) < 0) {
                this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, 0F);
                this.undoAnimationReverse(hand);
            }
        }
    }

    public boolean hasShield() {
        return !this.getShield().isEmpty();
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
        TileEntity tile = this.world.getTileEntity(getControllerPosition());

        if (tile instanceof ShopControllerTileEntity) {
            ((ShopControllerTileEntity) tile).decreaseGuardAmount();
        }

        super.onDeath(cause);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.MUSIC_MENU;
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putInt("x_c", this.dataManager.get(CONTROLLER_POSITION).getX());
        compound.putInt("y_c", this.dataManager.get(CONTROLLER_POSITION).getY());
        compound.putInt("z_c", this.dataManager.get(CONTROLLER_POSITION).getZ());

        compound.putInt("x_1", this.dataManager.get(SHOP_BOUND_1).getX());
        compound.putInt("y_1", this.dataManager.get(SHOP_BOUND_1).getY());
        compound.putInt("z_1", this.dataManager.get(SHOP_BOUND_1).getZ());

        compound.putInt("x_2", this.dataManager.get(SHOP_BOUND_2).getX());
        compound.putInt("y_2", this.dataManager.get(SHOP_BOUND_2).getY());
        compound.putInt("z_2", this.dataManager.get(SHOP_BOUND_2).getZ());

        compound.putInt("x_3", this.dataManager.get(POST_POSITION).getX());
        compound.putInt("y_3", this.dataManager.get(POST_POSITION).getY());
        compound.putInt("z_3", this.dataManager.get(POST_POSITION).getZ());

        compound.putBoolean("equip", this.isShieldEquipped());

        compound.put("shield", this.dataManager.get(SHIELD).write(new CompoundNBT()));
        compound.put("melee", this.dataManager.get(MELEE_WEAPON).write(new CompoundNBT()));

        compound.putFloat("animation_progress_right", this.getAnimationProgress(Hand.MAIN_HAND));
        compound.putFloat("animation_progress_left", this.getAnimationProgress(Hand.OFF_HAND));

        compound.putBoolean("reverse_right", this.isAnimationReversed(Hand.MAIN_HAND));
        compound.putBoolean("reverse_left", this.isAnimationReversed(Hand.OFF_HAND));

        super.writeAdditional(compound);
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        this.dataManager.set(CONTROLLER_POSITION, new BlockPos(compound.getInt("x_c"), compound.getInt("y_c"), compound.getInt("z_c")));
        this.dataManager.set(SHOP_BOUND_1, new BlockPos(compound.getInt("x_1"), compound.getInt("y_1"), compound.getInt("z_1")));
        this.dataManager.set(SHOP_BOUND_2, new BlockPos(compound.getInt("x_2"), compound.getInt("y_2"), compound.getInt("z_2")));
        this.dataManager.set(POST_POSITION, new BlockPos(compound.getInt("x_3"), compound.getInt("y_3"), compound.getInt("z_3")));

        CompoundNBT melee = compound.getCompound("melee");
        if (!melee.isEmpty()) {
            this.dataManager.set(MELEE_WEAPON, ItemStack.read(melee));
        }

        CompoundNBT shield = compound.getCompound("shield");
        if (!shield.isEmpty()) {
            this.dataManager.set(SHIELD, ItemStack.read(shield));
        }

        this.dataManager.set(SHIELD_EQUIPPED, compound.getBoolean("equip"));

        this.dataManager.set(RIGHT_ARM_ANIMATION_PROGRESS, compound.getFloat("animation_progress_right"));
        this.dataManager.set(LEFT_ARM_ANIMATION_PROGRESS, compound.getFloat("animation_progress_left"));

        this.dataManager.set(RIGHT_HAND_REVERSE_ANIMATION, compound.getBoolean("reverse_right"));
        this.dataManager.set(LEFT_HAND_REVERSE_ANIMATION, compound.getBoolean("reverse_left"));

        super.readAdditional(compound);
    }
}