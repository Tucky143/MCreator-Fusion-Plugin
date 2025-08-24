(
    new Object() {
        public FluidStack getFluidFromBlock(LevelAccessor level, BlockPos pos) {
            if (world instanceof Level lvl) {
                FluidState state = lvl.getFluidState(pos);

                if(state != null) {
                    return new FluidStack(state.getType(), 1000);
                }
            }
            return FluidStack.EMPTY;
        }
    }.getFluidFromBlock(world, BlockPos.containing(${input$x}, ${input$y}, ${input$z}))
)