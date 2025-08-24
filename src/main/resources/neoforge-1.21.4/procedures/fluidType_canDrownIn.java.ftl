(
    new Object() {
	    public boolean canDrownIn(FluidStack stack, Entity entity) {
		    if(entity instanceof LivingEntity livingEntity) {
			    return stack.getFluidType().canDrownIn(livingEntity);
		    }
		    return false;
	    }
    }.canDrownIn(${input$fluidstack}, ${input$entity})
)