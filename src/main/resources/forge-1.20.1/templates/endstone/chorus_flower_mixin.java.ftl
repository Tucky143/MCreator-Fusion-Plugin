package ${package}.mixins;

<#include "../mcitems.ftl">

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin extends Block {
    public ChorusFlowerBlockMixin(Properties settings) {
        super(settings);
    }

	@Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
	private void canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockState blockstate = world.getBlockState(pos.below());
		if (<#list endstones as endstone>blockstate.is(${mappedBlockToBlock(endstone.block)}) <#sep>||</#list>) {
			info.setReturnValue(true);
			info.cancel();
		}
	}

}