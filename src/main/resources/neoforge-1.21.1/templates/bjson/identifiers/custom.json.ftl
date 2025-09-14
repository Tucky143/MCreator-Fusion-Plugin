<#include "../../mcitems.ftl">
{
  "replace": false,
  "values": [
    <#assign values = []>
    <#list w.getGElementsOfType("curiosbauble") as bauble>
      <#if bauble.slotType == "${data.getModElement().getName()}">
        <#assign values += ["${modid}:${bauble.getModElement().getRegistryName()}"]>
      </#if>
    </#list>
    <#if data.items?? && data.items?has_content>
      <#list data.items as item>
        <#assign values += ["${mappedMCItemToRegistryName(item, false)}"]>
      </#list>
    </#if>

    <#list values as v>
      "${v}"<#if v?has_next>,</#if>
    </#list>
  ]
}