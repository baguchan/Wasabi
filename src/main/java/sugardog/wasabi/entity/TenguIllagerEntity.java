package sugardog.wasabi.entity;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import sugardog.wasabi.entity.goal.LongJumpGoal;
import sugardog.wasabi.entity.goal.LongJumpMidAirGoal;
import sugardog.wasabi.entity.goal.MeleeAttackAndJumpGoal;
import sugardog.wasabi.registry.WasabiItems;

import javax.annotation.Nullable;
import java.util.Map;

public class TenguIllagerEntity extends AbstractIllager {
	protected LongJumpGoal longJumpGoal;
	private boolean longJumping;

	public TenguIllagerEntity(EntityType<? extends TenguIllagerEntity> p_32105_, Level p_32106_) {
		super(p_32105_, p_32106_);
		this.xpReward = 8;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new LongJumpMidAirGoal(this));
		this.longJumpGoal = new LongJumpGoal(this, 100, 5, 5, 1.5F);
		this.goalSelector.addGoal(2, this.longJumpGoal);
		this.goalSelector.addGoal(4, new MeleeAttackAndJumpGoal(this, 1.0F, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_) {
		SpawnGroupData spawngroupdata = super.finalizeSpawn(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
		((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
		this.populateDefaultEquipmentSlots(p_34089_);
		this.populateDefaultEquipmentEnchantments(p_34089_);
		return spawngroupdata;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_34084_) {
		if (this.getCurrentRaid() == null) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(WasabiItems.KATANA.get()));
		}
	}

	public boolean isAlliedTo(Entity p_34110_) {
		if (super.isAlliedTo(p_34110_)) {
			return true;
		} else if (p_34110_ instanceof LivingEntity && ((LivingEntity) p_34110_).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && p_34110_.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
		ItemStack itemstack = new ItemStack(WasabiItems.KATANA.get());
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (p_34079_ > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
		}

		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
	}

	public void setLongJumping(boolean longJumping) {
		this.longJumping = longJumping;
	}

	public boolean isLongJumping() {
		return longJumping;
	}

	public void longJumpTrigger() {
		if (this.longJumpGoal != null) {
			this.longJumpGoal.trigger();
		}
	}

	public void longJumpTriggerByTarget(LivingEntity livingEntity) {
		if (this.longJumpGoal != null) {
			this.longJumpGoal.triggerTarget(livingEntity);
		}
	}

	protected int calculateFallDamage(float p_149389_, float p_149390_) {
		return super.calculateFallDamage(p_149389_, p_149390_) - 10;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATOR_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) {
		return SoundEvents.VINDICATOR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.VINDICATOR_DEATH;
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 4.0F);
	}

	@Override
	public IllagerArmPose getArmPose() {
		return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.CROSSED;
	}
}
