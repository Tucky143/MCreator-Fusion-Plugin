<#if data.defining>
List<? extends String>
<#else>
List("${field$name}", List.of(
    <#list field_list$entry as entry>
        "${entry}"<#sep>,
    </#list>
    ), entry -> true)
</#if>