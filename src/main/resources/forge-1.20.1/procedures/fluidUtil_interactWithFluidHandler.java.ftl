(
	new Object() {
		public boolean interactFluidHandler(LevelAccessor level, Entity entity, BlockPos pos, InteractionHand hand) {
			if(entity instanceof Player player) {
				BlockEntity blockEntity = level.getBlockEntity(pos);
				if(blockEntity != null) {
					return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).map(
						fluidHandler -> FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)
					).orElse(false);
				}
			}
			return false;
		}
	}.interactFluidHandler(world, ${input$entity}, BlockPos.containing(${input$x}, ${input$y}, ${input$z}), InteractionHand.${field$hand})
)