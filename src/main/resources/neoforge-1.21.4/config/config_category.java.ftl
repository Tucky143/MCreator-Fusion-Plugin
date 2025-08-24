<#if !data.defining>BUILDER.push("${field$name}");</#if>
    ${statement$write}
<#if !data.defining>BUILDER.pop();</#if>