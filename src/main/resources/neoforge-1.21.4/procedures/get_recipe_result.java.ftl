<#include "mcitems.ftl">
<#assign recipeName = field$recipe?replace("CUSTOM:", "")>
/*@ItemStack*/(new Object() {

    public ItemStack getResult() {
        if (world instanceof ServerLevel _lvl) {
            List<${recipeName}Recipe> recipes = _lvl.recipeAccess().recipeMap().byType(${recipeName}Recipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
            for (${recipeName}Recipe recipe : recipes) {
                List<Ingredient> ingredients = recipe.getIngredients();
                <#list input_list$entry as entry>
                    if (!ingredients.get(${entry?index}).test(${mappedMCItemToItemStackCode(entry)}))
                        continue;
                </#list>
                return recipe.getResultItem(null);
            }
        } else if (world instanceof ClientLevel _lvl) {
            List<${recipeName}Recipe> recipes = ${JavaModName}RecipeTypes.recipes.byType(${recipeName}Recipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
            for (${recipeName}Recipe recipe : recipes) {
                List<Ingredient> ingredients = recipe.getIngredients();
                <#list input_list$entry as entry>
                    if (!ingredients.get(${entry?index}).test(${mappedMCItemToItemStackCode(entry)}))
                        continue;
                </#list>
                return recipe.getResultItem(null);
            }
        }
        return ItemStack.EMPTY;
    }

}.getResult())