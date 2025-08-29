<#assign mixins = []>
<#list w.getWorkspace().getModElements() as element>
  <#assign providedmixins = []>
  <#if element.getGeneratableElement().mixins??>
    <#assign providedmixins = element.getGeneratableElement().mixins>
  </#if>
  <#if providedmixins?has_content>
    <#list providedmixins as mixin>
       <#if !mixins?seq_contains(mixin)>
         <#assign mixins += [mixin]>
       </#if>
    </#list>
  </#if>
</#list>
{
  "required": true,
  "package": "${package}.mixins",
  "compatibilityLevel": "JAVA_17",
  "refmap": "mixins.${modid}.refmap.json",
  "mixins": [
    <#if mixins?has_content>
      <#list mixins as mixin>
        "${mixin}"<#sep>,
      </#list>
    </#if>
  ],
  "client": [],
  "minVersion": "0.8"
}