package sugardog.wasabi.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import sugardog.wasabi.registry.WasabiBlocks;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WasabiLootTableData extends LootTableProvider {
	public WasabiLootTableData(DataGenerator generator) {
		super(generator);
	}


	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return ImmutableList.of(
				Pair.of(WasabiLootTableData.WasabiBlockLoot::new, LootContextParamSets.BLOCK));
	}


	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
	}

	public static class WasabiBlockLoot extends BlockLoot {
		private final Set<Block> knownBlocks = new HashSet<>();

		@Override
		protected void addTables() {
			dropSelf(WasabiBlocks.BAMBOO_TORCH);
		}

		@Override
		protected void add(Block p_124166_, LootTable.Builder p_124167_) {
			super.add(p_124166_, p_124167_);
			knownBlocks.add(p_124166_);
		}

		public void dropNone(Supplier<? extends Block> block) {
			super.add(block.get(), noDrop());
		}

		public void dropSelf(Supplier<? extends Block> block) {
			super.dropSelf(block.get());
		}

		public void dropWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
			super.add(block.get(), (result) -> createOreDrop(result, drop.get()));
		}

		public void dropSilk(Supplier<? extends Block> block) {
			super.dropWhenSilkTouch(block.get());
		}

		public void drop(Supplier<? extends Block> block, Supplier<? extends Block> drop) {
			this.dropOther(block.get(), drop.get());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			// todo 1.15 once all blockitems are ported, change this to all TF blocks, so an error will be thrown if we're missing any tables
			return knownBlocks;
		}
	}
}
