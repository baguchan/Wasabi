package sugardog.wasabi.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.entity.TenguIllagerEntity;

@Mod.EventBusSubscriber(modid = WasabiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WasabiEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WasabiMod.MODID);
	public static final EntityType<TenguIllagerEntity> TENGU_ILLAGER_TYPE = EntityType.Builder.of(TenguIllagerEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build(prefix("tengu_illager"));
	public static final RegistryObject<EntityType<TenguIllagerEntity>> TENGU_ILLAGER = ENTITIES.register("tengu_illager", () -> TENGU_ILLAGER_TYPE);


	private static String prefix(String path) {
		return WasabiMod.MODID + "." + path;
	}

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		Raid.RaiderType.create("tengu_illager", TENGU_ILLAGER.get(), new int[]{0, 0, 1, 0, 1, 1, 2, 1});
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(TENGU_ILLAGER.get(), TenguIllagerEntity.createAttributeMap().build());
	}
}
