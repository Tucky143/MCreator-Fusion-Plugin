(
	new Object() {
		public FluidStack getFluidFromBucket(Item item) {
			if(item instanceof BucketItem bucket) {
				return new FluidStack(bucket.getFluid(), 1000);
			}
			return FluidStack.EMPTY;
		}
	}.getFluidFromBucket(${input$bucket}.getItem())
)