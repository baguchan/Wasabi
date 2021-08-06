package sugardog.wasabi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class BambooTorchBlock extends Block {
	protected static final VoxelShape AABB;

	public BambooTorchBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public void animateTick(BlockState p_57494_, Level p_57495_, BlockPos p_57496_, Random p_57497_) {
		double var5 = (double) p_57496_.getX() + 0.5D;
		double var7 = (double) p_57496_.getY() + 0.45D;
		double var9 = (double) p_57496_.getZ() + 0.5D;
		p_57495_.addParticle(ParticleTypes.SMOKE, var5, var7, var9, 0.0D, 0.0D, 0.0D);
		p_57495_.addParticle(ParticleTypes.FLAME, var5, var7, var9, 0.0D, 0.0D, 0.0D);
	}

	public boolean canSurvive(BlockState p_153479_, LevelReader p_153480_, BlockPos p_153481_) {
		Direction var4 = getConnectedDirection(p_153479_).getOpposite();
		return Block.canSupportCenter(p_153480_, p_153481_.relative(var4), var4.getOpposite());
	}

	protected static Direction getConnectedDirection(BlockState p_153496_) {
		return Direction.UP;
	}

	public PushReaction getPistonPushReaction(BlockState p_153494_) {
		return PushReaction.DESTROY;
	}

	public BlockState updateShape(BlockState p_153483_, Direction p_153484_, BlockState p_153485_, LevelAccessor p_153486_, BlockPos p_153487_, BlockPos p_153488_) {
		return getConnectedDirection(p_153483_).getOpposite() == p_153484_ && !p_153483_.canSurvive(p_153486_, p_153487_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_153483_, p_153484_, p_153485_, p_153486_, p_153487_, p_153488_);
	}

	public boolean isPathfindable(BlockState p_153469_, BlockGetter p_153470_, BlockPos p_153471_, PathComputationType p_153472_) {
		return false;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
		return AABB;
	}

	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return AABB;
	}

	static {
		AABB = Shapes.or(Block.box(3.5D, 0.0D, 3.5D, 11.5D, 8.0D, 11.5D));
	}
}
