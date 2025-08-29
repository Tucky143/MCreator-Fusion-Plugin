<#include "../mcitems.ftl">
{
  "type": "${modid}:${data.category}",
  "ingredients": [
    <#assign items = "">
    <#list data.ingredients as item>
        <#if item.getUnmappedValue() == "Blocks.AIR">
            <#assign items += "\"#${modid}:jei_empty_tag\",">
        <#else>
            <#assign items += "\"${mappedMCItemToRegistryName(item, true)}\",">
        </#if>
    </#list>
    ${items[0..(items?last_index_of(',') - 1)]}
  ],
  <#if w.getWorkspace().getModElementByName(data.recipetype).getGeneratableElement().enableIntList>
    "integers": [
        <#list data.integers as integer>
            ${integer}<#sep>,
        </#list>
    ],
  </#if>
  <#if w.getWorkspace().getModElementByName(data.recipetype).getGeneratableElement().enableStringList>
    "strings": [
        <#list data.strings as string>
            "${string}"<#sep>,
        </#list>
    ],
  </#if>
  "output": {
    ${mappedMCItemToItemObjectJSON(data.result, "id")},
    "count": ${data.count}
  }
}