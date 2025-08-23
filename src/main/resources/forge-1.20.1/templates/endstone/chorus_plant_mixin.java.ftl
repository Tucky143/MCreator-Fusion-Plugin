package ${package}.mixins;

<#include "../mcitems.ftl">

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@Mixin(ChorusPlantBlock.class)
public abstract class ChorusPlantBlockMixin extends Block {
    public ChorusPlantBlockMixin(Properties settings) {
        super(settings);
    }

	@Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
	private void canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState blockstate = world.getBlockState(pos.below());
		if (<#list endstones as endstone>blockstate.is(${mappedBlockToBlock(endstone.block)}) ||</#list> blockstate.is(Blocks.END_STONE)) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "updateShape", at = @At("RETURN"), cancellable = true)
	private void updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> info) {
		BlockState plant = info.getReturnValue();
		BlockState blockstate = world.getBlockState(pos.below());
		if (plant.is(Blocks.CHORUS_PLANT) && (<#list endstones as endstone>blockstate.is(${mappedBlockToBlock(endstone.block)}) ||</#list> blockstate.is(Blocks.END_STONE))) {
			plant = plant.setValue(BlockStateProperties.DOWN, true);
			info.setReturnValue(plant);
		}
	}

	@Inject(method = "getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("RETURN"), cancellable = true)
	private void getStateForPlacement(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> info) {
		BlockPos pos = ctx.getClickedPos();
		Level world = ctx.getLevel();
		BlockState plant = info.getReturnValue();
		BlockState blockstate = world.getBlockState(pos.below());
		if (ctx.canPlace() && plant.is(Blocks.CHORUS_PLANT) && (<#list endstones as endstone>blockstate.is(${mappedBlockToBlock(endstone.block)}) ||</#list> blockstate.is(Blocks.END_STONE))) {
			info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
		}
	}

	@Inject(method = "Lnet/minecraft/world/level/block/ChorusPlantBlock;getStateForPlacement" + "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)" + "Lnet/minecraft/world/level/block/state/BlockState;", at = @At("RETURN"), cancellable = true)
	private void getStateForPlacement(BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<BlockState> info) {
		BlockState plant = info.getReturnValue();
		BlockState blockstate = blockGetter.getBlockState(blockPos.below());
		if (plant.is(Blocks.CHORUS_PLANT) && (<#list endstones as endstone>blockstate.is(${mappedBlockToBlock(endstone.block)}) ||</#list> blockstate.is(Blocks.END_STONE))) {
			info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
		}
	}

}