package sugardog.wasabi.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import sugardog.wasabi.WasabiMod;
import sugardog.wasabi.registry.WasabiBlocks;
import sugardog.wasabi.registry.WasabiEntityTypes;
import sugardog.wasabi.registry.WasabiItems;

import java.util.function.Supplier;

public class WasabiLangData extends LanguageProvider {
	public WasabiLangData(DataGenerator gen) {
		super(gen, WasabiMod.MODID, "en_us");
	}

	private void addDiscDesc(Supplier<? extends Item> key, String name) {
		add(key.get().getDescriptionId() + ".desc", name);
	}

	public void addAdvancement(String key, String name) {
		add("advancement.wasabi." + key, name);
	}

	public void addAdvancementDesc(String key, String name) {
		add("advancement.wasabi." + key + ".desc", name);
	}

	public void addGuiText(String key, String name) {
		add("gui.wasabi." + key, name);
	}

	public void addMenuText(String key, String name) {
		addGuiText("menu." + key, name);
	}

	public void addLoreBookText(String key, String name) {
		addGuiText("book_of_lore." + key, name);
	}

	public void addMessage(String key, String name) {
		add("wasabi." + key, name);
	}

	public void addKeyInfo(String key, String name) {
		add("key.wasabi." + key, name);
	}


	@Override
	protected void addTranslations() {
		addBlock(WasabiBlocks.BAMBOO_TORCH, "Bamboo Torch");
		addBlock(WasabiBlocks.BAMBOO_SOUL_TORCH, "Bamboo Soul Torch");
		addItem(WasabiItems.KATANA, "Katana");
		addItem(WasabiItems.STEEL, "Steel Ingot");

		addItem(WasabiItems.TENGU_ILLAGER_SPAWNEGG, "TenguIllager SpawnEgg");

		addEntityType(WasabiEntityTypes.TENGU_ILLAGER, "Tengu Illager");
	}
}
