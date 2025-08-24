<#if data.defining>
public static final ForgeConfigSpec.ConfigValue<${input$value}> ${field$name?upper_case};
<#else>
${field$name?upper_case} = BUILDER<#if field$comment??>.comment("${field$comment}")</#if>.define${input$value};
</#if>