<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
@SubscribeEvent public static void addAttributeModifier(ItemAttributeModifierEvent event) {
		<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
            "itemstack": "event.getItemStack()",
            "event": "event"
            }/>
		</#compress></#assign>
		execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
	}