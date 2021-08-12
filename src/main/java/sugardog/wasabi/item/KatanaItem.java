package sugardog.wasabi.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import sugardog.wasabi.registry.WasabiItems;

public class KatanaItem extends Item {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;


	public KatanaItem(Item.Properties properties) {
		super(properties);
		this.attackDamage = 5.0F;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> var5 = ImmutableMultimap.builder();
		var5.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
		var5.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.5F, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = var5.build();
	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return !p_43294_.isCreative();
	}

	public float getDestroySpeed(ItemStack p_43288_, BlockState p_43289_) {
		if (p_43289_.is(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			Material var3 = p_43289_.getMaterial();
			return var3 != Material.PLANT && var3 != Material.REPLACEABLE_PLANT && !p_43289_.is(BlockTags.LEAVES) && var3 != Material.VEGETABLE ? 1.0F : 1.5F;
		}
	}

	public boolean hurtEnemy(ItemStack p_43278_, LivingEntity hurtMob, LivingEntity attacker) {
		p_43278_.hurtAndBreak(1, attacker, (p_43296_) -> {
			p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});

		double d0 = (double) (-Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)));
		double d1 = (double) Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F));

		float f = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);

		if (attacker instanceof Player && attacker.isOnGround()) {
			float f3 = 2.0F * (1.0F + 0.15F * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, p_43278_)) + EnchantmentHelper.getSweepingDamageRatio(attacker) * f;

			for (LivingEntity livingentity : attacker.level.getEntitiesOfClass(LivingEntity.class, hurtMob.getBoundingBox().inflate(1.0D, 0.25D, 1.0D))) {
				if (livingentity != attacker && livingentity != hurtMob && !attacker.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && attacker.distanceToSqr(livingentity) < 10.0D) {
					livingentity.knockback((double) 0.4F, (double) Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
					livingentity.hurt(DamageSource.playerAttack((Player) attacker), f3);
				}
			}
			if (attacker.level instanceof ServerLevel) {
				((ServerLevel) attacker.level).sendParticles(ParticleTypes.SWEEP_ATTACK, attacker.getX() + d0, attacker.getY(0.5D), attacker.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
			}
			attacker.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
		}

		return true;
	}

	public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
		if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
			p_43282_.hurtAndBreak(2, p_43286_, (p_43276_) -> {
				p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}

		return true;
	}

	public boolean isCorrectToolForDrops(BlockState p_43298_) {
		return p_43298_.is(Blocks.COBWEB);
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
		return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_43105_) {
		return UseAnim.BLOCK;
	}

	public int getUseDuration(ItemStack p_43107_) {
		return 72000;
	}

	public InteractionResultHolder<ItemStack> use(Level p_43099_, Player p_43100_, InteractionHand p_43101_) {
		ItemStack var4 = p_43100_.getItemInHand(p_43101_);
		p_43100_.startUsingItem(p_43101_);
		return InteractionResultHolder.consume(var4);
	}

	public boolean isValidRepairItem(ItemStack p_43091_, ItemStack p_43092_) {
		return p_43092_.is(WasabiItems.STEEL.get()) || super.isValidRepairItem(p_43091_, p_43092_);
	}
}
