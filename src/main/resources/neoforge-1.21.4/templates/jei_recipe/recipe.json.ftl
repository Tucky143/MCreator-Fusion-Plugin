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
  "output": {
    ${mappedMCItemToItemObjectJSON(data.result, "id")},
    "count": ${data.count}
  }
}