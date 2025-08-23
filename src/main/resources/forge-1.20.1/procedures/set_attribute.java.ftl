<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
((LivingEntity) ${input$entity}).getAttribute(${attr}).setBaseValue(${input$value});