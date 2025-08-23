<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
<#if field$checkExiting?lower_case == "true">
    if(!(((LivingEntity) ${input$entity}).getAttribute(${attr}).hasModifier(${input$modifier})))
</#if>
<#if field$permanent?lower_case == "true">
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).addPermanentModifier(${input$modifier});
<#else>
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).addTransientModifier(${input$modifier});
</#if>