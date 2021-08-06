package baguchan.wasabi.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class WasabiDamageSource {
	public static DamageSource stabDamageSource(Entity entity, @Nullable Entity attacker) {
		return (new IndirectEntityDamageSource("stab", entity, attacker)).bypassArmor();
	}
}
