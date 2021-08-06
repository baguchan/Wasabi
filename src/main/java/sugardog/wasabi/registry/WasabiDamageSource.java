package sugardog.wasabi.registry;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class WasabiDamageSource {
	public static DamageSource stabDamageSource(Entity entity, @Nullable Entity attacker) {
		return (new IndirectEntityDamageSource("stab", entity, attacker)).bypassArmor();
	}
}
