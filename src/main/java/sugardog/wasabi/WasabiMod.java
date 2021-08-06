package sugardog.wasabi;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import sugardog.wasabi.data.*;
import sugardog.wasabi.registry.WasabiBlocks;
import sugardog.wasabi.registry.WasabiItems;

@Mod(WasabiMod.MODID)
public class WasabiMod {
	public static final String MODID = "wasabi";

	public WasabiMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

		WasabiBlocks.BLOCKS.register(modbus);
		WasabiItems.ITEMS.register(modbus);

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		if (event.includeClient()) {
			generator.addProvider(new WasabiBlockStateData(generator, helper));
			generator.addProvider(new WasabiItemModelData(generator, helper));
			generator.addProvider(new WasabiLangData(generator));
		}
		if (event.includeServer()) {
			generator.addProvider(new WasabiRecipeData(generator));
			generator.addProvider(new WasabiLootTableData(generator));
		}
	}
}