package sugardog.wasabi.data.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import sugardog.wasabi.WasabiMod;

import java.util.function.Supplier;

public abstract class WasabiItemModelProvider extends ItemModelProvider {
	public WasabiItemModelProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, WasabiMod.MODID, fileHelper);
	}

	public String blockName(Supplier<? extends Block> block) {
		return block.get().getRegistryName().getPath();
	}

	protected ResourceLocation texture(String name) {
		return modLoc("block/" + name);
	}

	protected ResourceLocation texture(String name, String location) {
		return modLoc("block/" + location + name);
	}

	public ItemModelBuilder item(Supplier<? extends Item> item, String location) {
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/generated"))
				.texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath()));
	}

	public ItemModelBuilder item(Supplier<? extends Item> item) {
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/generated"))
				.texture("layer0", modLoc("item/" + item.get().getRegistryName().getPath()));
	}

	public ItemModelBuilder handheldItem(Supplier<? extends Item> item) {
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/handheld"))
				.texture("layer0", modLoc("item/" + item.get().getRegistryName().getPath()));
	}

	public ItemModelBuilder bowItem(Supplier<? extends Item> item, String location) {
		withExistingParent(item.get().getRegistryName().getPath() + "_pulling_0", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath() + "_pulling_0"));
		withExistingParent(item.get().getRegistryName().getPath() + "_pulling_1", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath() + "_pulling_1"));
		withExistingParent(item.get().getRegistryName().getPath() + "_pulling_2", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath() + "_pulling_2"));
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/bow"))
				.texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath()))
				.override().predicate(new ResourceLocation("pulling"), 1).model(getExistingFile(modLoc("item/" + item.get().getRegistryName().getPath() + "_pulling_0"))).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(getExistingFile(modLoc("item/" + item.get().getRegistryName().getPath() + "_pulling_1"))).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(getExistingFile(modLoc("item/" + item.get().getRegistryName().getPath() + "_pulling_2"))).end();
	}

	public ItemModelBuilder dyedItem(Supplier<? extends Item> item) {
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/generated"))
				.texture("layer0", modLoc("item/" + item.get().getRegistryName().getPath()))
				.texture("layer1", modLoc("item/" + item.get().getRegistryName().getPath() + "_overlay"));
	}

	public ItemModelBuilder eggItem(Supplier<? extends Item> item) {
		return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
	}

	public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
		return withExistingParent(blockName(block), texture(blockName(block)));
	}

	public ItemModelBuilder itemBlock(Supplier<? extends Block> block, String suffix) {
		return withExistingParent(blockName(block), texture(blockName(block) + suffix));
	}

	public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", texture(blockName(block), location));
	}

	public ItemModelBuilder itemTorch(Supplier<? extends Block> block, String location) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", texture(blockName(block), location));
	}

	public ItemModelBuilder itemFence(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
		return withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
				.texture("texture", texture(blockName(baseBlock), location));
	}

	public ItemModelBuilder itemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
		return wallInventory(blockName(block), texture(blockName(baseBlock), location));
	}
}