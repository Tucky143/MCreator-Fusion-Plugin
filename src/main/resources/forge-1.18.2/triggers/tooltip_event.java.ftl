<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent public static void onItemTooltip(ItemTooltipEvent event) {
		<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
                        "itemstack": "event.getItemStack()",
			"tooltip": "event.getToolTip()",
			"entity": "event.getPlayer()",
			"event": "event"
			}/>
		</#compress></#assign>
		execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
	}