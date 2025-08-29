<#include "../mcitems.ftl">

package ${package}.init;

@JeiPlugin
public class ${JavaModName}BrewingRecipes implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
    	return new ResourceLocation("${modid}:brewing_recipes");
    }

    <#assign brewingRecipes = []>
    <#list recipes as recipe>
        <#if recipe.recipeType == "Brewing">
            <#assign brewingRecipes += [recipe]>
        </#if>
    </#list>

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        <#if brewingRecipes??>
            IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
            List<IJeiBrewingRecipe> brewingRecipes = new ArrayList<>();
            ItemStack potion = new ItemStack(Items.POTION);
            ItemStack potion2 = new ItemStack(Items.POTION);
            List<ItemStack> ingredientStack = new ArrayList<>();
            List<ItemStack> inputStack = new ArrayList<>();
		    <#list brewingRecipes as recipe>
                <#if recipe.brewingIngredientStack.getUnmappedValue().startsWith("TAG:")>
                    ingredientStack = new ArrayList<ItemStack>(ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("${recipe.brewingIngredientStack?replace("TAG:","")}"))).stream().map(item -> new ItemStack((Item) item)).collect(Collectors.toCollection(ArrayList::new)));
                <#else>
                    ingredientStack.add(${mappedMCItemToItemStackCode(recipe.brewingIngredientStack)});
                </#if>
		        <#if recipe.brewingReturnStack?starts_with("POTION:") || recipe.brewingInputStack?starts_with("POTION:")>
		                <#if recipe.brewingReturnStack?starts_with("POTION:") && recipe.brewingInputStack?starts_with("POTION:")>
                            PotionUtils.setPotion(potion, ${generator.map(recipe.brewingInputStack?replace("POTION:",""), "potions")});
                            PotionUtils.setPotion(potion2, ${generator.map(recipe.brewingReturnStack?replace("POTION:",""), "potions")});
                            brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), potion.copy(), potion2.copy()));
                            ingredientStack.clear();
		                <#elseif recipe.brewingReturnStack?starts_with("POTION:")>
                            <#if recipe.brewingInputStack.getUnmappedValue().startsWith("TAG:")>
                                inputStack = new ArrayList<ItemStack>(ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("${recipe.brewingInputStack?replace("TAG:","")}"))).stream().map(item -> new ItemStack((Item) item)).collect(Collectors.toCollection(ArrayList::new)));
                            <#else>
                                inputStack.add(${mappedMCItemToItemStackCode(recipe.brewingInputStack)});
                            </#if>
		                    PotionUtils.setPotion(potion, ${generator.map(recipe.brewingReturnStack?replace("POTION:",""), "potions")});
		                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), List.copyOf(inputStack), potion.copy()));
		                    ingredientStack.clear();
		                    inputStack.clear();
		                <#else>
		                    PotionUtils.setPotion(potion, ${generator.map(recipe.brewingInputStack?replace("POTION:",""), "potions")});
		                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), potion.copy(), ${mappedMCItemToItemStackCode(recipe.brewingReturnStack)}));
		                    ingredientStack.clear();
		                </#if>
		        <#else>
                    <#if recipe.brewingInputStack.getUnmappedValue().startsWith("TAG:")>
                        inputStack = new ArrayList<ItemStack>(ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("${recipe.brewingInputStack?replace("TAG:","")}"))).stream().map(item -> new ItemStack((Item) item)).collect(Collectors.toCollection(ArrayList::new)));
                    <#else>
                        inputStack.add(${mappedMCItemToItemStackCode(recipe.brewingInputStack)});
                    </#if>
                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), List.copyOf(inputStack), ${mappedMCItemToItemStackCode(recipe.brewingReturnStack)}));
		            inputStack.clear();
		            ingredientStack.clear();
		        </#if>
		    </#list>
		    registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
		</#if>
    }

}