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
        <#if data.enableIntList>
            List<ItemStack> stacks = new ArrayList<>();
        </#if>
        <#list data.slotList as slot>
            <#if slot.type == "INPUT">
                <#if data.enableIntList>
                    stacks.clear();
                    for (Item item : recipe.getIngredients().get(${slot.slotid}).items().map(Holder::value).collect(Collectors.toList()))
                        stacks.add(new ItemStack(item, recipe.integers().get(${slot.slotid})));
                    builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y}).addItemStacks(stacks);
                <#else>
                    builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y}).add(recipe.getIngredients().get(${slot.slotid}));
                </#if>
            <#else>
                builder.addSlot(RecipeIngredientRole.OUTPUT, ${slot.x}, ${slot.y}).add(recipe.getResultItem(null));
            </#if>
        </#list>
    }

}