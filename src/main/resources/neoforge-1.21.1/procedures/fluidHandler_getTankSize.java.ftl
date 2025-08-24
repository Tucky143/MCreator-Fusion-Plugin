(
	new Object() {
		public int getTankSize(LevelAccessor level, BlockPos pos) {
			if(level instanceof ILevelExtension extension) {
				IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
				if(fluidHandler != null) {
					return fluidHandler.getTankCapacity(0);
				}
			}
			return 0;
		}
	}.getTankSize(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)