package sugardog.wasabi.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import sugardog.wasabi.entity.goal.LongJumpGoal;
import sugardog.wasabi.entity.goal.LongJumpMidAirGoal;
import sugardog.wasabi.entity.goal.MeleeAttackAndJumpGoal;

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

	@Override
	public void applyRaidBuffs(int i, boolean b) {

	}

	protected int calculateFallDamage(float p_149389_, float p_149390_) {
		return super.calculateFallDamage(p_149389_, p_149390_) - 10;
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return null;
	}

	public static AttributeSupplier.Builder createAttributeMap() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ATTACK_DAMAGE, 4.0F);
	}

	@Override
	public IllagerArmPose getArmPose() {
		return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.CROSSED;
	}
}
