(
	new Object() {
		public FluidStack getFluidInTank(LevelAccessor level, BlockPos pos) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity != null) {
				return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null)
					.map(fluidHandler -> fluidHandler.getFluidInTank(0).copy())
					.orElse(FluidStack.EMPTY);
			}
			return FluidStack.EMPTY;
		}
	}.getFluidInTank(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)