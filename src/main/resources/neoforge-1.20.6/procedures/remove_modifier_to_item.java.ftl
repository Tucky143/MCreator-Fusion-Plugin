<#assign attr = "">
<#if field$attribute.startsWith("CUSTOM:")>
<#assign attr = JavaModName + "Attributes." + field$attribute?replace("CUSTOM:", "")?upper_case + ".getDelegate()">
<#elseif generator.map(field$attribute, "attribute").startsWith("FORGE:")>
<#assign attr = "NeoForgeMod." + generator.map(field$attribute, "attribute")?replace("FORGE:", "") + ".getDelegate()">
<#else>
<#assign attr = "net.minecraft.world.entity.ai.attributes.Attributes." + generator.map(field$attribute, "attribute")>
</#if>
_event.removeModifier(${attr}, ${input$modifier});