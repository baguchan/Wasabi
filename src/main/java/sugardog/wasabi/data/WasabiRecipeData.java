package sugardog.wasabi.data;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import sugardog.wasabi.registry.WasabiBlocks;
import sugardog.wasabi.registry.WasabiItems;

import java.nio.file.Path;
import java.util.function.Consumer;

public class WasabiRecipeData extends RecipeProvider {
	public WasabiRecipeData(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void saveAdvancement(HashCache p_126014_, JsonObject p_126015_, Path p_126016_) {
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
		ShapedRecipeBuilder.shaped(WasabiBlocks.BAMBOO_TORCH.get(), 1)
				.pattern("BTB")
				.pattern("BBB")
				.define('B', Blocks.BAMBOO)
				.define('T', Blocks.TORCH)
				.unlockedBy("has_bamboo", has(Blocks.BAMBOO))
				.save(p_176532_);

		ShapedRecipeBuilder.shaped(WasabiBlocks.BAMBOO_SOUL_TORCH.get(), 1)
				.pattern("BTB")
				.pattern("BBB")
				.define('B', Blocks.BAMBOO)
				.define('T', Blocks.SOUL_TORCH)
				.unlockedBy("has_bamboo", has(Blocks.BAMBOO))
				.save(p_176532_);

		ShapedRecipeBuilder.shaped(WasabiItems.KATANA.get(), 1)
				.pattern("  I")
				.pattern(" I ")
				.pattern("S  ")
				.define('I', WasabiItems.STEEL.get())
				.define('S', Items.STICK)
				.unlockedBy("has_bamboo", has(WasabiItems.STEEL.get()))
				.save(p_176532_);
	}

}
