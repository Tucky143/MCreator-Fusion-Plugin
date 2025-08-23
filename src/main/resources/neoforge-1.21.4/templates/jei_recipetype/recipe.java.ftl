package ${package}.jei_recipes;

<#compress>

import javax.annotation.Nullable;

public record ${name}Recipe(ItemStack output, List<Ingredient> recipeItems) implements Recipe<RecipeInput> {
    public ${name}Recipe(ItemStack output, List<Ingredient> recipeItems) {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.recipeItems);
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return false;
    }

    public List<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider holder) {
        return output;
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Type implements RecipeType<${name}Recipe> {
        private Type(){}
        public static final RecipeType<${name}Recipe> INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<${name}Recipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<${name}Recipe> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                        ItemStack.STRICT_CODEC.fieldOf("output").forGetter(${name}Recipe::output),
                        Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(${name}Recipe::recipeItems)
                    )
                    .apply(builder, ${name}Recipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ${name}Recipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<${name}Recipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ${name}Recipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ${name}Recipe fromNetwork(RegistryFriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), EmptyIngredient.create());
            inputs.replaceAll(ingredients -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            return new ${name}Recipe(ItemStack.STREAM_CODEC.decode(buf), inputs);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, ${name}Recipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.items().findFirst().get().value() == Items.AIR)
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, EmptyIngredient.create());
                else
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));
        }
    }

}</#compress>