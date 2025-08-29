<#include "../mcitems.ftl">

package ${package}.jei_recipes;

public class ${name}RecipeCategory implements IRecipeCategory<${name}Recipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("${modid}:${data.getModElement().getRegistryName()}");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("${modid}:textures/screens/${data.textureSelector}");

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
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public void draw(${name}Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ${name}Recipe recipe, IFocusGroup focuses) {
        <#list data.slotList as slot>
            <#if slot.type == "ITEM_INPUT">
                builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y}).addIngredients(VanillaTypes.ITEM_STACK, Arrays.asList(recipe.${slot.name}ItemInput().getItems()));
            <#elseif slot.type == "FLUID_INPUT">
                builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y}).addFluidStack(recipe.${slot.name}FluidInput().getFluid(), 1000)
                    .addRichTooltipCallback((slot, tooltip) ->
                        tooltip.add(Component.literal(recipe.${slot.name}FluidInput().getAmount() + " mb")));
            <#else>
                builder.addSlot(RecipeIngredientRole.OUTPUT, ${slot.x}, ${slot.y}).addItemStack(recipe.getResult(0));
            </#if>
        </#list>
    }

}