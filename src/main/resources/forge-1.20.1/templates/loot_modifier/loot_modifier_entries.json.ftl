{
  "entries": [
    <#list lootmodifiers as modifier>
        "${modid}:${modifier.getModElement().getRegistryName()}"<#if modifier?has_next>,</#if>
    </#list>
  ],
  "replace": false
}