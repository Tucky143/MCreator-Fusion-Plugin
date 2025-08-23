<#include "../mcitems.ftl">

package ${package}.init;

@JeiPlugin
public class ${JavaModName}JeiInformation implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
    	return ResourceLocation.parse("${modid}:information");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        <#list jeiinformations as info>
            registration.addIngredientInfo(
                List.of(
                    <#list info.items as item>
                        ${mappedMCItemToItemStackCode(item)}<#sep>,
                    </#list>
                ),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.${modid}.${info.getModElement().getRegistryName()}")
            );
        </#list>
    }

}