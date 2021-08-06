package baguchan.wasabi.registry;

import baguchan.wasabi.WasabiMod;
import baguchan.wasabi.item.BambooSpearItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = WasabiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WasabiItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WasabiMod.MOD_ID);

	public static final RegistryObject<Item> STEEL = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
	public static final RegistryObject<Item> BAMBOO_SPEAR = ITEMS.register("bamboo_spear", () -> new BambooSpearItem(new Item.Properties().durability(120).tab(ItemGroup.TAB_COMBAT)));
}