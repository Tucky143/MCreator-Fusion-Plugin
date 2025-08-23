{
  "replace": false,
  "values": [
    <#assign baubles = []>
    <#list w.getGElementsOfType("curiosbauble") as bauble>
      <#if bauble.slotType == "${data.getModElement().getName()}">
        <#assign baubles += [bauble]>
      </#if>
    </#list>
    <#list baubles as bauble>
      "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
    </#list>
  ]
}