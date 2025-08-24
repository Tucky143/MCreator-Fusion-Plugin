<#include "mcitems.ftl">
<#if data.defining>
String
<#else>
("${field$name}", <#if field$value??>BuiltInRegistries.ITEM.getKey(${mappedMCItemToItem(generator.map(field$value, "blocksitems"))}).toString()</#if>)
</#if>