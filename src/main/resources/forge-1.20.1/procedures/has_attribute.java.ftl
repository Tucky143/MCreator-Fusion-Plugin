<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
(entity instanceof LivingEntity && ((LivingEntity) ${input$entity}).getAttribute(${attr}) != null)