<#function getSource attribute="">
    <#if attribute.startsWith("CUSTOM:")>
        <#return "custom">
    <#elseif generator.map(field$attribute, "attribute").startsWith("FORGE:")>
        <#return "forge">
    <#else>
        <#return "minecraft">
    </#if>
</#function>

<#function getAttributeResource attribute="">
    <#assign attr = getSource(field$attribute)>
    <#if attr == "custom">
        <#return JavaModName + "Attributes." + attribute?replace("CUSTOM:", "")?upper_case>
    <#elseif attr == "forge">
        <#return "NeoForgeMod." + generator.map(attribute, "attribute")?replace("FORGE:", "")>
    <#else>
        <#return "net.minecraft.world.entity.ai.attributes.Attributes." + generator.map(attribute, "attribute")>
    </#if>
</#function>

<#function getAttribute attribute="">
    <#assign attr = getSource(field$attribute)>
    <#if attr == "custom">
        <#return JavaModName + "Attributes." + attribute?replace("CUSTOM:", "")?upper_case + ".getDelegate()">
    <#elseif attr == "forge">
        <#return "NeoForgeMod." + generator.map(attribute, "attribute")?replace("FORGE:", "") + ".getDelegate()">
    <#else>
        <#return "net.minecraft.world.entity.ai.attributes.Attributes." + generator.map(attribute, "attribute")>
    </#if>
</#function>