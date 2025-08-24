(
	new Object() {
		public boolean doesBlockHaveTank(LevelAccessor level, BlockPos pos) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity != null) {
				return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).isPresent();
			}
			return false;
		}
	}.doesBlockHaveTank(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)