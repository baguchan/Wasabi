package baguchan.wasabi;

import baguchan.wasabi.registry.WasabiItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WasabiMod.MOD_ID)
public class WasabiMod {
	public static final String MOD_ID = "wasabi";

	public WasabiMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

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

	}
}