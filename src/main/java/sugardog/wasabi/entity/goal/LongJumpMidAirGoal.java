package sugardog.wasabi.entity.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import sugardog.wasabi.entity.TenguIllagerEntity;

public class LongJumpMidAirGoal extends Goal {
	private final TenguIllagerEntity tenguIllagerEntity;

	public LongJumpMidAirGoal(TenguIllagerEntity tenguIllagerEntity) {
		super();
		this.tenguIllagerEntity = tenguIllagerEntity;
	}

	@Override
	public boolean canUse() {
		return this.tenguIllagerEntity.isLongJumping() && !this.tenguIllagerEntity.isOnGround();
	}

	@Override
	public void stop() {
		super.stop();
		this.tenguIllagerEntity.setLongJumping(false);
		this.tenguIllagerEntity.setDiscardFriction(false);
	}
}
