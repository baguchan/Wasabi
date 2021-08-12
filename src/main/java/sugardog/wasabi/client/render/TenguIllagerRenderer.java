package sugardog.wasabi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
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
		this.addLayer(new ItemInHandLayer<T, TenguIllagerModel<T>>(this) {
			public void render(PoseStack p_114989_, MultiBufferSource p_114990_, int p_114991_, T p_114992_, float p_114993_, float p_114994_, float p_114995_, float p_114996_, float p_114997_, float p_114998_) {
				if (p_114992_.isAggressive()) {
					super.render(p_114989_, p_114990_, p_114991_, p_114992_, p_114993_, p_114994_, p_114995_, p_114996_, p_114997_, p_114998_);
				}

			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return ILLAGER;
	}
}