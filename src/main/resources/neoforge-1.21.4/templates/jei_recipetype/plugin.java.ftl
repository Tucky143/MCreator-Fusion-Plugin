<#include "../mcitems.ftl">

package ${package}.init;

<#compress>
@JeiPlugin
public class ${JavaModName}JeiPlugin implements IModPlugin {
    <#list jeirecipetypes as type>
        public static mezz.jei.api.recipe.RecipeType<${type.getModElement().getName()}Recipe> ${type.getModElement().getName()}_Type =
            new mezz.jei.api.recipe.RecipeType<>(${type.getModElement().getName()}RecipeCategory.UID, ${type.getModElement().getName()}Recipe.class);
    </#list>

    @Override
    public ResourceLocation getPluginUid() {
    	return ResourceLocation.parse("${modid}:jei_plugin");
    }

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
	    <#list jeirecipetypes as type>
	        registration.addRecipeCategories(new
	            ${type.getModElement().getName()}RecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	    </#list>
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		<#list jeirecipetypes as type>
            List<${type.getModElement().getName()}Recipe> ${type.getModElement().getName()}Recipes = ${JavaModName}RecipeTypes.recipes.byType(${type.getModElement().getName()}Recipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
            registration.addRecipes(${type.getModElement().getName()}_Type, ${type.getModElement().getName()}Recipes);
		</#list>
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
	    <#list jeirecipetypes as type>
	        <#if type.enableCraftingtable>
	            <#list type.craftingtables as block>
	                registration.addRecipeCatalyst(new ItemStack(${mappedMCItemToItem(block)}), ${type.getModElement().getName()}_Type);
	            </#list>
	        </#if>
	    </#list>
	}

}</#compress>
