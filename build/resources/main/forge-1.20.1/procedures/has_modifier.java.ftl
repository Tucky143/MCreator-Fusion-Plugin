<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
((LivingEntity) ${input$entity}).getAttribute(${attr}).hasModifier(${input$modifier})
