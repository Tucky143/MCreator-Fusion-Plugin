{
	if (world instanceof ServerLevel srvlvl_) {
		class Output implements PackResources.ResourceOutput {
			private List<com.google.gson.JsonObject> jsonObjects;
			private PackResources packResources;

			public Output(List<com.google.gson.JsonObject> jsonObjects) {
			this.jsonObjects = jsonObjects;
			}

			public void setPackResources(PackResources packResources) {
				this.packResources = packResources;
			}

			@Override
			public void accept(ResourceLocation resourceLocation, IoSupplier<InputStream> ioSupplier) {
				try {
					com.google.gson.JsonObject jsonObject = new com.google.gson.Gson().fromJson(new java.io.BufferedReader(new java.io.InputStreamReader(ioSupplier.get(), java.nio.charset.StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n")), com.google.gson.JsonObject.class);
					this.jsonObjects.add(jsonObject);
				} catch (Exception e) {}
			}
		}
		
		List<com.google.gson.JsonObject> jsons = new ArrayList<>();
		Output output = new Output(jsons);
		ResourceManager rm = srvlvl_.getServer().getResourceManager();
		rm.listPacks().forEach(resource -> {
			for (String namespace : resource.getNamespaces(PackType.SERVER_DATA)) {
				output.setPackResources(resource);
				resource.listResources(PackType.SERVER_DATA, namespace, ${input$directory}, output);
			}
		});
		for (com.google.gson.JsonObject jsoniterator : jsons) {
			${statement$foreach}
		}
	}
}