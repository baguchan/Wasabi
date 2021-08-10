package sugardog.wasabi.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.WeighedRandom;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import sugardog.wasabi.entity.TenguIllagerEntity;

import java.util.*;

public class LongJumpGoal extends Goal {
	private static final int FIND_JUMP_TRIES = 20;
	private static final int PREPARE_JUMP_DURATION = 40;
	private static final int MIN_PATHFIND_DISTANCE_TO_VALID_JUMP = 8;
	public static final int TIME_OUT_DURATION = 200;
	private final TenguIllagerEntity tenguIllagerEntity;
	private final int timeBetweenLongJumps;
	private final int maxLongJumpHeight;
	private final int maxLongJumpWidth;
	private final float maxJumpVelocity;
	private final List<PossibleJump> jumpCandidates = new ArrayList();
	private Optional<Vec3> initialPosition = Optional.empty();
	private Optional<PossibleJump> chosenJump = Optional.empty();
	private int findJumpTries;
	private int prepareJumpStart;
	private int timeJumpCooldown;
	protected boolean forceTrigger;
	private LivingEntity target;

	public LongJumpGoal(TenguIllagerEntity tenguIllagerEntity, int timeBetweenLongJumps, int maxLongJumpHeight, int maxLongJumpWidth, float maxJumpVelocity) {
		this.tenguIllagerEntity = tenguIllagerEntity;
		this.timeBetweenLongJumps = timeBetweenLongJumps;
		this.maxLongJumpHeight = maxLongJumpHeight;
		this.maxLongJumpWidth = maxLongJumpWidth;
		this.maxJumpVelocity = maxJumpVelocity;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		if (this.forceTrigger) {
			this.forceTrigger = false;

			this.timeJumpCooldown = this.timeBetweenLongJumps;

			return this.tenguIllagerEntity.isOnGround();
		}

		if (this.timeJumpCooldown < 0) {
			this.timeJumpCooldown = this.timeBetweenLongJumps;

			return this.tenguIllagerEntity.isOnGround() && this.tenguIllagerEntity.getRandom().nextInt(5) == 0 && this.tenguIllagerEntity.getNavigation().getPath() == null && this.tenguIllagerEntity.getTarget() == null;
		} else {
			--this.timeJumpCooldown;
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		boolean var5 = this.initialPosition.isPresent() && ((Vec3) this.initialPosition.get()).equals(this.tenguIllagerEntity.position()) && this.findJumpTries > 0 && (this.chosenJump.isPresent() || !this.jumpCandidates.isEmpty());

		return var5;
	}

	public void trigger() {
		this.forceTrigger = true;
	}

	public void triggerTarget(LivingEntity target) {
		this.forceTrigger = true;
		this.target = target;
	}

	@Override
	public void start() {
		this.chosenJump = Optional.empty();
		this.findJumpTries = 20;
		this.jumpCandidates.clear();
		this.initialPosition = Optional.of(this.tenguIllagerEntity.position());
		BlockPos var5 = this.tenguIllagerEntity.blockPosition();
		PathNavigation var10 = this.tenguIllagerEntity.getNavigation();
		if (target == null) {
			int var6 = var5.getX();
			int var7 = var5.getY();
			int var8 = var5.getZ();
			Iterable<BlockPos> var9 = BlockPos.betweenClosed(var6 - this.maxLongJumpWidth, var7 - this.maxLongJumpHeight, var8 - this.maxLongJumpWidth, var6 + this.maxLongJumpWidth, var7 + this.maxLongJumpHeight, var8 + this.maxLongJumpWidth);
			Iterator var11 = var9.iterator();

			while (true) {
				BlockPos var12;
				double var13;
				do {
					if (!var11.hasNext()) {
						return;
					}

					var12 = (BlockPos) var11.next();
					var13 = var12.distSqr(var5);
				} while (var6 == var12.getX() && var8 == var12.getZ());

				if (var10.isStableDestination(var12) && this.tenguIllagerEntity.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(this.tenguIllagerEntity.level, var12.mutable())) == 0.0F) {
					Optional<Vec3> var15 = this.calculateOptimalJumpVector(this.tenguIllagerEntity, Vec3.atCenterOf(var12));
					BlockPos finalVar1 = var12;
					double finalVar11 = var13;
					var15.ifPresent((p_147670_) -> {
						this.jumpCandidates.add(new PossibleJump(new BlockPos(finalVar1), p_147670_, Mth.ceil(finalVar11)));
					});
				}
			}
		} else {
			if (var10.isStableDestination(target.blockPosition()) && this.tenguIllagerEntity.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(this.tenguIllagerEntity.level, target.blockPosition().mutable())) == 0.0F) {
				Optional<Vec3> var15 = this.calculateOptimalJumpVector(this.tenguIllagerEntity, Vec3.atCenterOf(target.blockPosition()));
				double finalVar11 = target.blockPosition().distSqr(var5);
				var15.ifPresent((p_147670_) -> {
					this.jumpCandidates.add(new PossibleJump(target.blockPosition(), p_147670_, Mth.ceil(finalVar11)));
				});
			}
		}
	}

	@Override
	public void stop() {
		super.stop();
		this.target = null;
	}

	public void tick() {
		if (this.chosenJump.isPresent()) {
			if (this.prepareJumpStart >= 20) {
				this.tenguIllagerEntity.setYRot(this.tenguIllagerEntity.yBodyRot);
				this.tenguIllagerEntity.setDiscardFriction(true);
				this.tenguIllagerEntity.setLongJumping(true);
				Vec3 var5 = ((PossibleJump) this.chosenJump.get()).getJumpVector();
				double var6 = var5.length();
				double var8 = var6 + this.tenguIllagerEntity.getJumpBoostPower();
				this.tenguIllagerEntity.setDeltaMovement(var5.scale(var8 / var6));
				this.tenguIllagerEntity.playSound(SoundEvents.GENERIC_SMALL_FALL, 1.0F, 1.0F);
			} else {
				++this.prepareJumpStart;
			}
		} else {
			--this.findJumpTries;
			Optional<PossibleJump> var10 = WeighedRandom.getRandomItem(this.tenguIllagerEntity.level.random, this.jumpCandidates);
			if (var10.isPresent()) {
				this.jumpCandidates.remove(var10.get());
				PathNavigation var11 = this.tenguIllagerEntity.getNavigation();
				Path var7 = var11.createPath(((PossibleJump) var10.get()).getJumpTarget(), 0, 8);
				if (var7 == null || !var7.canReach()) {
					this.chosenJump = var10;
					this.prepareJumpStart = 0;
				}
			}
		}

	}

	private Optional<Vec3> calculateOptimalJumpVector(Mob p_147657_, Vec3 p_147658_) {
		Optional<Vec3> var3 = Optional.empty();

		for (int var4 = 65; var4 < 85; var4 += 5) {
			Optional<Vec3> var5 = this.calculateJumpVectorForAngle(p_147657_, p_147658_, var4);
			if (!var3.isPresent() || var5.isPresent() && ((Vec3) var5.get()).lengthSqr() < ((Vec3) var3.get()).lengthSqr()) {
				var3 = var5;
			}
		}

		return var3;
	}

	private Optional<Vec3> calculateJumpVectorForAngle(Mob p_147660_, Vec3 p_147661_, int p_147662_) {
		Vec3 var4 = p_147660_.position();
		Vec3 var5 = (new Vec3(p_147661_.x - var4.x, 0.0D, p_147661_.z - var4.z)).normalize().scale(0.5D);
		p_147661_ = p_147661_.subtract(var5);
		Vec3 var6 = p_147661_.subtract(var4);
		float var7 = (float) p_147662_ * 3.1415927F / 180.0F;
		double var8 = Math.atan2(var6.z, var6.x);
		double var10 = var6.subtract(0.0D, var6.y, 0.0D).lengthSqr();
		double var12 = Math.sqrt(var10);
		double var14 = var6.y;
		double var16 = Math.sin((double) (2.0F * var7));
		double var18 = 0.08D;
		double var20 = Math.pow(Math.cos((double) var7), 2.0D);
		double var22 = Math.sin((double) var7);
		double var24 = Math.cos((double) var7);
		double var26 = Math.sin(var8);
		double var28 = Math.cos(var8);
		double var30 = var10 * 0.08D / (var12 * var16 - 2.0D * var14 * var20);
		if (var30 < 0.0D) {
			return Optional.empty();
		} else {
			double var32 = Math.sqrt(var30);
			if (var32 > (double) this.maxJumpVelocity) {
				return Optional.empty();
			} else {
				double var34 = var32 * var24;
				double var36 = var32 * var22;
				int var38 = Mth.ceil(var12 / var34) * 2;
				double var39 = 0.0D;
				Vec3 var41 = null;

				for (int var42 = 0; var42 < var38 - 1; ++var42) {
					var39 += var12 / (double) var38;
					double var43 = var22 / var24 * var39 - Math.pow(var39, 2.0D) * 0.08D / (2.0D * var30 * Math.pow(var24, 2.0D));
					double var45 = var39 * var28;
					double var47 = var39 * var26;
					Vec3 var49 = new Vec3(var4.x + var45, var4.y + var43, var4.z + var47);
					if (var41 != null && !this.isClearTransition(p_147660_, var41, var49)) {
						return Optional.empty();
					}

					var41 = var49;
				}

				return Optional.of((new Vec3(var34 * var28, var36, var34 * var26)).scale(0.949999988079071D));
			}
		}
	}

	private boolean isClearTransition(Mob p_147664_, Vec3 p_147665_, Vec3 p_147666_) {
		EntityDimensions var4 = p_147664_.getDimensions(Pose.LONG_JUMPING);
		Vec3 var5 = p_147666_.subtract(p_147665_);
		double var6 = (double) Math.min(var4.width, var4.height);
		int var8 = Mth.ceil(var5.length() / var6);
		Vec3 var9 = var5.normalize();
		Vec3 var10 = p_147665_;

		for (int var11 = 0; var11 < var8; ++var11) {
			var10 = var11 == var8 - 1 ? p_147666_ : var10.add(var9.scale(var6 * 0.8999999761581421D));
			AABB var12 = var4.makeBoundingBox(var10);
			if (!p_147664_.level.noCollision(p_147664_, var12)) {
				return false;
			}
		}

		return true;
	}

	public static class PossibleJump extends WeighedRandom.WeighedRandomItem {
		private final BlockPos jumpTarget;
		private final Vec3 jumpVector;

		public PossibleJump(BlockPos p_147690_, Vec3 p_147691_, int p_147692_) {
			super(p_147692_);
			this.jumpTarget = p_147690_;
			this.jumpVector = p_147691_;
		}

		public BlockPos getJumpTarget() {
			return this.jumpTarget;
		}

		public Vec3 getJumpVector() {
			return this.jumpVector;
		}
	}
}
