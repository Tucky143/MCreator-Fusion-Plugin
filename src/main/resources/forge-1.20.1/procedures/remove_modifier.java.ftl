<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
<#if field$permanent?lower_case == "true">
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removePermanentModifier(${input$modifier}.getId());
<#else>
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removeModifier(${input$modifier});
</#if>