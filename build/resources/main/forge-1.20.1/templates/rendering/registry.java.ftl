package ${package}.init;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}CuriosRenderers {

	@SubscribeEvent
	public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
	<#list curiosbaubles as bauble>
	<#if bauble.hasModel()>
		evt.registerLayerDefinition(${JavaModName}LayerDefinitions.${bauble.getModElement().getRegistryNameUpper()}, 				${bauble.baubleModel}::createBodyLayer);
	</#if>
	</#list>
	}

	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent evt) {
	<#list curiosbaubles as bauble>
	<#if bauble.hasModel()>
		CuriosRendererRegistry.register(${JavaModName}Items.${bauble.getModElement().getRegistryNameUpper()}.get(), 				${bauble.getModElement().getName()}Renderer::new);
	</#if>
	</#list>
	}	

}