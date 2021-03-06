package sugardog.wasabi.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.item.KatanaItem;

@Mod.EventBusSubscriber(modid = WasabiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WasabiItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WasabiMod.MODID);

	public static final RegistryObject<Item> STEEL = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new KatanaItem(new Item.Properties().durability(346).tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<Item> TENGU_ILLAGER_SPAWNEGG = ITEMS.register("tengu_illager_spawnegg", () -> new SpawnEggItem(WasabiEntityTypes.TENGU_ILLAGER_TYPE, 9804699, 1973274, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


	public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
		if (item instanceof BlockItem) {
			Item.BY_BLOCK.put(((BlockItem) item).getBlock(), item);
		}

		item.setRegistryName(new ResourceLocation(WasabiMod.MODID, id));

		registry.getRegistry().register(item);
	}

	public static void register(RegistryEvent.Register<Item> registry, Item item) {

		if (item instanceof BlockItem && item.getRegistryName() == null) {
			item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

			Item.BY_BLOCK.put(((BlockItem) item).getBlock(), item);
		}

		registry.getRegistry().register(item);
	}
}