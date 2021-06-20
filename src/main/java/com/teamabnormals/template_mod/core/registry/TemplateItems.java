package com.teamabnormals.template_mod.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.template_mod.core.TemplateMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TemplateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TemplateItems {
	public static final ItemSubRegistryHelper HELPER = TemplateMod.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> TEMPLATE_ITEM = HELPER.createItem("template_item", () -> new Item(new Item.Properties().group(ItemGroup.FOOD)));
}