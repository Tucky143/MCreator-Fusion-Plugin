<#if data.defining>
String
<#else>
("${field$name}", "<#if field$value??>${field$value}</#if>")
</#if>