<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
(((LivingEntity) ${input$entity}).getAttribute(${attr}).getModifier(UUID.fromString(${input$modifierid})) != null)
