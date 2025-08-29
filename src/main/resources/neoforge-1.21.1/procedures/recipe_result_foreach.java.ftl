<#include "mcitems.ftl">
<#assign recipeName = field$recipe?replace("CUSTOM:", "")>
{
    if (world instanceof Level _lvl) {
        net.minecraft.world.item.crafting.RecipeManager rm = _lvl.getRecipeManager();
        List<${recipeName}Recipe> recipes = rm.getAllRecipesFor(${recipeName}Recipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        for (${recipeName}Recipe recipe : recipes) {
            List<Ingredient> ingredients = recipe.getIngredients();
            <#list input_list$entry as entry>
                if (!ingredients.get(${entry?index}).test(${mappedMCItemToItemStackCode(entry)}))
                    continue;
            </#list>
            ItemStack reciperesult = recipe.getResultItem(null);
            ${statement$foreach}
            break;
        }
    }
}