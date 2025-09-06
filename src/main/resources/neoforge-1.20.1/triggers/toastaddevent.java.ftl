<#include "procedures.java.ftl">
@Mod.EventBusSubscriber
public class ${name}Procedure {
    @SubscribeEvent
    public static void onEventTriggered(ToastAddEvent event) {
        <#assign dependenciesCode><#compress>
            <@procedureDependenciesCode dependencies, {
            "Toast": "event.getToast()",
            "event": "event"
            }/>
        </#compress></#assign>
        execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
    }