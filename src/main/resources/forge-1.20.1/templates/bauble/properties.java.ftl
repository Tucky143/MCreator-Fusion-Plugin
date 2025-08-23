package ${package}.init;

<#include "../procedures.java.ftl">

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ${JavaModName}CuriosProperties {

	<#compress>
	@SubscribeEvent public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
		<#list curiosbaubles as item>
			<#if item.getModElement().getTypeString() == "curiosbauble">
				<#list item.customProperties.entrySet() as property>
				ItemProperties.register(${JavaModName}Items.${item.getModElement().getRegistryNameUpper()}.get(),
					new ResourceLocation("${modid}:${item.getModElement().getRegistryName()}_${property.getKey()}"),
					(itemStackToRender, clientWorld, entity, itemEntityId) ->
						<#if hasProcedure(property.getValue())>
							(float) <@procedureCode property.getValue(), {
								"x": "entity != null ? entity.getX() : 0",
								"y": "entity != null ? entity.getY() : 0",
								"z": "entity != null ? entity.getZ() : 0",
								"world": "entity != null ? entity.level() : clientWorld",
								"entity": "entity",
								"itemstack": "itemStackToRender"
							}, false/>
						<#else>0</#if>
				);
				</#list>
			</#if>
		</#list>
		});
	}
	</#compress>

}