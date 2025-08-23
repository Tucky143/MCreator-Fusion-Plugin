/*@BlockState*/new Object() {
    public BlockState getBlockState(String _nbt) {
	    try {
		    return NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), TagParser.parseTag(_nbt));
		} catch (CommandSyntaxException e) {
		    ${JavaModName}.LOGGER.error(e);
			return Blocks.AIR.defaultBlockState();
		}
	}
}.getBlockState(${input$string})