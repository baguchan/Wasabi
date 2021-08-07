package sugardog.wasabi.registry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.block.BambooTorchBlock;

@Mod.EventBusSubscriber(modid = WasabiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WasabiBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WasabiMod.MODID);

	public static final RegistryObject<Block> BAMBOO_TORCH = BLOCKS.register("bamboo_torch", () -> new BambooTorchBlock(BlockBehaviour.Properties.of(Material.BAMBOO).noOcclusion().strength(1.0F, 2.0F).lightLevel((p_50886_) -> {
		return 14;
	}).sound(SoundType.BAMBOO), ParticleTypes.FLAME));
	public static final RegistryObject<Block> BAMBOO_SOUL_TORCH = BLOCKS.register("bamboo_soul_torch", () -> new BambooTorchBlock(BlockBehaviour.Properties.of(Material.BAMBOO).noOcclusion().strength(1.0F, 2.0F).lightLevel((p_50886_) -> {
		return 10;
	}).sound(SoundType.BAMBOO), ParticleTypes.SOUL_FIRE_FLAME));

	@SubscribeEvent
	public static void registerBlockItems(RegistryEvent.Register<Item> event) {
		WasabiItems.register(event, new BlockItem(BAMBOO_TORCH.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
		WasabiItems.register(event, new BlockItem(BAMBOO_SOUL_TORCH.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
	}
}