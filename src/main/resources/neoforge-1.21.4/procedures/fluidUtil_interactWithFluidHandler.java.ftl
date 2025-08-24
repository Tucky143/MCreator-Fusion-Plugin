(
	new Object() {
		public boolean interactFluidHandler(LevelAccessor level, Entity entity, BlockPos pos, InteractionHand hand) {
			if(entity instanceof Player player) {
				if(level instanceof ILevelExtension extension) {
					IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
					return FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);
				}
				return false;
			}
			return false;
		}
	}.interactFluidHandler(world, ${input$entity}, BlockPos.containing(${input$x}, ${input$y}, ${input$z}), InteractionHand.${field$hand})
)