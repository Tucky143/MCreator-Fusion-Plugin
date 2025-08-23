<#assign attr = "">
<#if field$attribute.startsWith("CUSTOM:")>
<#assign attr = JavaModName + "Attributes." + field$attribute?replace("CUSTOM:", "")?upper_case + ".getDelegate()">
<#elseif generator.map(field$attribute, "attribute").startsWith("FORGE:")>
<#assign attr = "NeoForgeMod." + generator.map(field$attribute, "attribute")?replace("FORGE:", "") + ".getDelegate()">
<#else>
<#assign attr = "net.minecraft.world.entity.ai.attributes.Attributes." + generator.map(field$attribute, "attribute")>
</#if>
<#if field$permanent?lower_case == "true">
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removePermanentModifier(UUID.fromString(${input$modifierid}));
<#else>
    ((LivingEntity) ${input$entity}).getAttribute(${attr}).removeModifier(UUID.fromString(${input$modifierid}));
</#if>