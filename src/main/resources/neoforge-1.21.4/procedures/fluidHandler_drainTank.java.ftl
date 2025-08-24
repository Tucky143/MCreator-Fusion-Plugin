{
	if(world instanceof ILevelExtension extension) {
		IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, BlockPos.containing(${input$x}, ${input$y}, ${input$z}), null);
		if(fluidHandler != null) {
			fluidHandler.drain((int) ${input$amount}, IFluidHandler.FluidAction.EXECUTE);
		}
	}
}