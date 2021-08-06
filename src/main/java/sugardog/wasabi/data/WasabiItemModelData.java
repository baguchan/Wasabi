package sugardog.wasabi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import sugardog.wasabi.registry.WasabiItems;

public class WasabiItemModelData extends WasabiItemModelProvider {
	public WasabiItemModelData(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, fileHelper);
	}

	@Override
	public String getName() {
		return "Wasabi Item Models";
	}

	@Override
	protected void registerModels() {
		item(WasabiItems.STEEL.get(), )
	}
}