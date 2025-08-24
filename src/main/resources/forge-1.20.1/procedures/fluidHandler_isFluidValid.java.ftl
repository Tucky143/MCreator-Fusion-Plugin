(
	new Object() {
		public boolean isFluidValid(LevelAccessor level, BlockPos pos, FluidStack stack) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity != null) {
				return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null)
					.map(fluidHandler -> fluidHandler.isFluidValid(0, stack))
					.orElse(false);
			}
			return false;
		}
	}.isFluidValid(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}), ${input$fluidstack})
)