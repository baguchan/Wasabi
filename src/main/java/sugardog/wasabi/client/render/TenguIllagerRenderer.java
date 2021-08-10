package sugardog.wasabi.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.client.WasabiModelLayers;
import sugardog.wasabi.client.model.TenguIllagerModel;
import sugardog.wasabi.entity.TenguIllagerEntity;

@OnlyIn(Dist.CLIENT)
public class TenguIllagerRenderer<T extends TenguIllagerEntity> extends MobRenderer<T, TenguIllagerModel<T>> {
	private static final ResourceLocation ILLAGER = new ResourceLocation(WasabiMod.MODID, "textures/entity/tengu_illager.png");

	public TenguIllagerRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new TenguIllagerModel<>(p_173952_.bakeLayer(WasabiModelLayers.TENGU_ILLAGER)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return ILLAGER;
	}
}