<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
<#if field$permanent?lower_case == "true">
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removePermanentModifier(UUID.fromString(${input$modifierid}));
<#else>
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removeModifier(UUID.fromString(${input$modifierid}));
</#if>