package sugardog.wasabi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import sugardog.wasabi.data.provider.WasabiBlockStateProvider;

public class WasabiBlockStateData extends WasabiBlockStateProvider {
	public WasabiBlockStateData(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, fileHelper);
	}

	@Override
	public String getName() {
		return "Wasabi Block States";
	}

	@Override
	protected void registerStatesAndModels() {

	}
}
