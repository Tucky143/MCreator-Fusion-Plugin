{
  "entities": ["player"],
  "slots": [
    <#assign slots = []>

    <#list w.getGElementsOfType("curiosslot") as slot>
      <#assign slots += ["${slot.getModElement().getRegistryName()}"]>
    </#list>

    <#list w.getGElementsOfType("curiosbauble") as bauble>
      <#if bauble??>
        <#if bauble.slotType == "CURIO">
          <#assign slots += ["curio"]>
        </#if>
        <#if bauble.slotType == "HEAD">
          <#assign slots += ["head"]>
        </#if>
        <#if bauble.slotType == "BACK">
          <#assign slots += ["back"]>
        </#if>
        <#if bauble.slotType == "BELT">
          <#assign slots += ["belt"]>
        </#if>
        <#if bauble.slotType == "BODY">
          <#assign slots += ["body"]>
        </#if>
        <#if bauble.slotType == "BRACELET">
          <#assign slots += ["bracelet"]>
        </#if>
        <#if bauble.slotType == "CHARM">
          <#assign slots += ["charm"]>
        </#if>
        <#if bauble.slotType == "HANDS">
          <#assign slots += ["hands"]>
        </#if>
        <#if bauble.slotType == "RING">
          <#assign slots += ["ring"]>
        </#if>
        <#if bauble.slotType == "NECKLACE">
          <#assign slots += ["necklace"]>
        </#if>
      </#if>
    </#list>
    <#list slots as v>
      "${v}"<#if v?has_next>, </#if>
    </#list>
  ]
}
