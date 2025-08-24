(
	new Object() {
		public FluidStack getFluidInTank(LevelAccessor level, BlockPos pos) {
			if (level instanceof ILevelExtension extension) {
				IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
				if (fluidHandler != null) {
					return fluidHandler.getFluidInTank(0).copy();
				} else {
					return FluidStack.EMPTY;
				}
			}
			return FluidStack.EMPTY;
		}
	}.getFluidInTank(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)