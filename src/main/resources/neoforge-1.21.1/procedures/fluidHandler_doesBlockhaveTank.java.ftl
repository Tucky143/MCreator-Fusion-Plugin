(
	new Object() {
		public boolean doesBlockHaveTank(LevelAccessor level, BlockPos pos) {
			if(level instanceof ILevelExtension extension) {
				IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
				if (fluidHandler != null) {
					return true;
				}
			}
			return false;
		}
	}.doesBlockHaveTank(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)