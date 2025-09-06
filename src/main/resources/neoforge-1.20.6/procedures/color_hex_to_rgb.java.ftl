<#if field$redGreenBlue == "red">
Integer.valueOf("${field$hex[0..1]}", 16)
<#elseif field$redGreenBlue == "green">
Integer.valueOf("${field$hex[2..3]}", 16)
<#else>
Integer.valueOf("${field$hex[4..5]}", 16)
</#if>