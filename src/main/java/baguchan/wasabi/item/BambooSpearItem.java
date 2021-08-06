package baguchan.wasabi.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class BambooSpearItem extends Item {
	private final float attackDamageBaseline;
	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	protected static final UUID BASE_REACH_UUID = UUID.fromString("b1e90661-bfe4-4a78-ad00-186a8adf5193");

	public BambooSpearItem(Item.Properties properties) {
		super(properties);
		this.attackDamageBaseline = 4.0F;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.PIERCING || super.canApplyAtEnchantingTable(stack, enchantment);
	}

	public boolean hurtEnemy(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
		p_77644_1_.hurtAndBreak(1, p_77644_3_, (p_220039_0_) -> {
			p_220039_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});

		return true;
	}

	public boolean mineBlock(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
		if (!p_179218_2_.isClientSide && p_179218_3_.getDestroySpeed(p_179218_2_, p_179218_4_) != 0.0F) {
			p_179218_1_.hurtAndBreak(2, p_179218_5_, (p_220038_0_) -> {
				p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
		if (defaultModifiers == null) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamageBaseline, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -2.5F, AttributeModifier.Operation.ADDITION));
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(BASE_REACH_UUID, "Weapon modifier", (double) 1.0F, AttributeModifier.Operation.ADDITION));
			this.defaultModifiers = builder.build();
		}

		return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_111205_1_);
	}

	public float getAttackDamage() {
		return this.attackDamageBaseline;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack) > 0;
	}

	public int getItemEnchantability(ItemStack stack) {
		return 1;
	}
}
