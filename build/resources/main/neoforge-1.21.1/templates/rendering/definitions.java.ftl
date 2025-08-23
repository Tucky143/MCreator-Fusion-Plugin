package ${package}.init;

public class ${JavaModName}LayerDefinitions {

	<#list curiosbaubles as bauble>
	<#if bauble.hasModel()>
	public static final ModelLayerLocation ${bauble.getModElement().getRegistryNameUpper()} =
	new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("${modid}", "${bauble.getModElement().getRegistryName()}"),
	"${bauble.getModElement().getRegistryName()}");
	</#if>
	</#list>

}