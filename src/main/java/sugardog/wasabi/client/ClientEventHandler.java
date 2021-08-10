package sugardog.wasabi.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.client.model.TenguIllagerModel;
import sugardog.wasabi.client.render.TenguIllagerRenderer;
import sugardog.wasabi.registry.WasabiEntityTypes;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = WasabiMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(WasabiEntityTypes.TENGU_ILLAGER.get(), TenguIllagerRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(WasabiModelLayers.TENGU_ILLAGER, TenguIllagerModel::createBodyLayer);
	}
}
