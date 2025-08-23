<#include "attributes.ftl">
<#assign attr = getAttribute(field$attribute)>
_event.addModifier(${attr}, ${input$modifier});