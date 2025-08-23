<#include "../mcitems.ftl">

package ${package}.init;

@JeiPlugin
public class ${JavaModName}BrewingRecipes implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
    	return ResourceLocation.parse("${modid}:brewing_recipes");
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
                    ingredientStack = (ArrayList<ItemStack>) StreamSupport.stream(
                        BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.create(ResourceLocation.parse("${recipe.brewingIngredientStack?replace("TAG:","")}"))).spliterator(),
                        false
                    ).map(item -> new ItemStack(item.value()))
                    .collect(Collectors.toCollection(ArrayList::new));
                <#else>
                    ingredientStack.add(${mappedMCItemToItemStackCode(recipe.brewingIngredientStack)});
                </#if>
		        <#if recipe.brewingReturnStack?starts_with("POTION:") || recipe.brewingInputStack?starts_with("POTION:")>
		                <#if recipe.brewingReturnStack?starts_with("POTION:") && recipe.brewingInputStack?starts_with("POTION:")>
                            potion.set(DataComponents.POTION_CONTENTS, new PotionContents(${generator.map(recipe.brewingInputStack?replace("POTION:",""), "potions")}));
                            potion2.set(DataComponents.POTION_CONTENTS, new PotionContents(${generator.map(recipe.brewingReturnStack?replace("POTION:",""), "potions")}));
                            brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), potion.copy(), potion2.copy(), ResourceLocation.parse("${modid}:${recipe.getModElement().getRegistryName()}")));
                            ingredientStack.clear();
		                <#elseif recipe.brewingReturnStack?starts_with("POTION:")>
                            <#if recipe.brewingInputStack.getUnmappedValue().startsWith("TAG:")>
                                inputStack = (ArrayList<ItemStack>) StreamSupport.stream(
                                    BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.create(ResourceLocation.parse("${recipe.brewingInputStack?replace("TAG:","")}"))).spliterator(),
                                    false
                                    ).map(item -> new ItemStack(item.value()))
                                    .collect(Collectors.toCollection(ArrayList::new));
                            <#else>
                                inputStack.add(${mappedMCItemToItemStackCode(recipe.brewingInputStack)});
                            </#if>
		                    potion.set(DataComponents.POTION_CONTENTS, new PotionContents(${generator.map(recipe.brewingInputStack?replace("POTION:",""), "potions")}));
		                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), List.copyOf(inputStack), potion.copy(), ResourceLocation.parse("${modid}:${recipe.getModElement().getRegistryName()}")));
		                    ingredientStack.clear();
		                    inputStack.clear();
		                <#else>
		                    potion.set(DataComponents.POTION_CONTENTS, new PotionContents(${generator.map(recipe.brewingInputStack?replace("POTION:",""), "potions")}));
		                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), potion.copy(), ${mappedMCItemToItemStackCode(recipe.brewingReturnStack)}, ResourceLocation.parse("${modid}:${recipe.getModElement().getRegistryName()}")));
		                    ingredientStack.clear();
		                </#if>
		        <#else>
                    <#if recipe.brewingInputStack.getUnmappedValue().startsWith("TAG:")>
                        inputStack = (ArrayList<ItemStack>) StreamSupport.stream(
                        BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.create(ResourceLocation.parse("${recipe.brewingInputStack?replace("TAG:","")}"))).spliterator(),
                        false
                        ).map(item -> new ItemStack(item.value()))
                        .collect(Collectors.toCollection(ArrayList::new));
                    <#else>
                        inputStack.add(${mappedMCItemToItemStackCode(recipe.brewingInputStack)});
                    </#if>
                    brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), List.copyOf(inputStack), ${mappedMCItemToItemStackCode(recipe.brewingReturnStack)}, ResourceLocation.parse("${modid}:${recipe.getModElement().getRegistryName()}")));
		            inputStack.clear();
		            ingredientStack.clear();
		        </#if>
		    </#list>
		    registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
		</#if>
    }

}