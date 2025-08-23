<#include "../mcitems.ftl">

package ${package}.jei_recipes;

public class ${name}RecipeCategory implements IRecipeCategory<${name}Recipe> {
    public final static ResourceLocation UID = new ResourceLocation("${modid}", "${data.getModElement().getRegistryName()}");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation("${modid}", "textures/screens/${data.textureSelector}");

    private final IDrawable background;
    private final IDrawable icon;

    public ${name}RecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, ${data.width}, ${data.height});
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(${mappedMCItemToItem(data.icon)}));
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<${name}Recipe> getRecipeType() {
        return ${JavaModName}JeiPlugin.${name}_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("${data.title}");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ${name}Recipe recipe, IFocusGroup focuses) {
        <#list data.slotList as slot>
            <#if slot.type == "INPUT">
                builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y}).addIngredients(recipe.getIngredients().get(${slot.slotid}));
            <#else>
                builder.addSlot(RecipeIngredientRole.OUTPUT, ${slot.x}, ${slot.y}).addItemStack(recipe.getResultItem(null));
            </#if>
        </#list>
    }

}