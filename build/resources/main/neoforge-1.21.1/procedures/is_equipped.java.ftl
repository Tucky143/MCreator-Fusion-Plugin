<#include "mcitems.ftl">
${input$entity} instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(${mappedMCItemToItem(input$item)}, lv).isPresent()
: false