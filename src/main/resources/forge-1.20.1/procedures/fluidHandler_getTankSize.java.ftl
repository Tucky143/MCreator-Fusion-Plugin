(
	new Object() {
		public int getTankSize(LevelAccessor level, BlockPos pos) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity != null) {
				return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null)
					.map(fluidHandler -> fluidHandler.getTankCapacity(0))
					.orElse(0);
			}
			return 0;
		}
	}.getTankSize(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)