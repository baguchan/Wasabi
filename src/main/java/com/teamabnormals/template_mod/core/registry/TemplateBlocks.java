package com.teamabnormals.template_mod.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.template_mod.core.TemplateMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TemplateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TemplateBlocks {
	public static final BlockSubRegistryHelper HELPER = TemplateMod.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> TEMPLATE_BLOCK = HELPER.createBlock("template_block", () -> new Block(Block.Properties.from(Blocks.STONE)), ItemGroup.BUILDING_BLOCKS);
}
