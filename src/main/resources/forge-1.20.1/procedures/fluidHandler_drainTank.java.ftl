{
	BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(${input$x}, ${input$y}, ${input$z}));
	if(blockEntity != null) {
		blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> capability.drain((int) ${input$amount}, IFluidHandler.FluidAction.EXECUTE));
	}
}