package sugardog.wasabi.other;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.registry.WasabiDamageSource;
import sugardog.wasabi.registry.WasabiItems;

@Mod.EventBusSubscriber(modid = WasabiMod.MOD_ID)
public class TemplateEvents {

	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event) {
		LivingEntity livingEntity = event.getEntityLiving();

		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, attacker);

			if (attacker.getMainHandItem().getItem() == WasabiItems.BAMBOO_SPEAR.get() || attacker.getOffhandItem().getItem() == WasabiItems.BAMBOO_SPEAR.get()) {
				livingEntity.hurt(WasabiDamageSource.stabDamageSource(livingEntity, attacker), 1.0F + 1.0F * i);
			}
		}
	}
}
