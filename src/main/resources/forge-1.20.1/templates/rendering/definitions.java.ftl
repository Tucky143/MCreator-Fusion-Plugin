package ${package}.init;

public class ${JavaModName}LayerDefinitions {

	<#list curiosbaubles as bauble>
	<#if bauble.hasModel()>
	public static final ModelLayerLocation ${bauble.getModElement().getRegistryNameUpper()} =
	new ModelLayerLocation(new ResourceLocation("${modid}", "${bauble.getModElement().getRegistryName()}"),
	"${bauble.getModElement().getRegistryName()}");
	</#if>
	</#list>

}