package ${package}.init;

public class ${JavaModName}RecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ${JavaModName}.MODID);
    public static final RegistryObject<SimpleCraftingRecipeSerializer<DyeItemRecipe>> DYEABLE_ITEM =REGISTRY.register("dyeable_item", () -> new SimpleCraftingRecipeSerializer<>(DyeItemRecipe::new));
}
