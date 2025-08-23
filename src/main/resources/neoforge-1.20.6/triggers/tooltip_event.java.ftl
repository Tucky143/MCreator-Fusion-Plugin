<#include "procedures.java.ftl">
@EventBusSubscriber(value = {Dist.CLIENT}) public class ${name}Procedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent public static void onItemTooltip(ItemTooltipEvent event) {
		<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
                        "itemstack": "event.getItemStack()",
			"tooltip": "event.getToolTip()",
			"entity": "event.getEntity()",
			"event": "event"
			}/>
		</#compress></#assign>
		execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
	}