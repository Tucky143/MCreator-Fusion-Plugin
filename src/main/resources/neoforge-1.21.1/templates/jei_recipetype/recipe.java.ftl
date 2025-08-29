package ${package}.jei_recipes;

<#compress>

import javax.annotation.Nullable;

public class ${name}Recipe implements Recipe<RecipeInput> {
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    <#if data.enableIntList>
    private final List<Integer> integers;
    </#if>
    <#if data.enableIntList>
    private final List<String> strings;
    </#if>

    public ${name}Recipe(ItemStack output, NonNullList<Ingredient> recipeItems<#if data.enableIntList>, List<Integer> integers</#if><#if data.enableStringList>, List<String> strings</#if>) {
        this.output = output;
        this.recipeItems = recipeItems;
        <#if data.enableIntList>
            this.integers = integers;
        </#if>
        <#if data.enableStringList>
            this.strings = strings;
        </#if>
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return false;
    }

    <#if data.enableIntList>
    public List<Integer> integers() {
        return this.integers;
    }
    </#if>

    <#if data.enableStringList>
    public List<String> strings() {
        return this.strings;
    }
    </#if>

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider holder) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
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
                        ItemStack.STRICT_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                        Ingredient.CODEC_NONEMPTY
                            .listOf()
                            .fieldOf("ingredients")
                            .flatXmap(
                                ingredients -> {
                                    Ingredient[] aingredient = ingredients
                                              .toArray(Ingredient[]::new); // Skip the empty check and create the array.
                                    if (aingredient.length == 0) {
                                        return DataResult.error(() -> "No ingredients found in custom recipe");
                                    } else {
                                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                    }
                                },
                                DataResult::success
                            )
                            .forGetter(recipe -> recipe.recipeItems)
                        <#if data.enableIntList>,
                            Codec.INT.listOf().fieldOf("integers").forGetter(recipe -> recipe.integers)
                        </#if>
                        <#if data.enableStringList>,
                            Codec.STRING.listOf().fieldOf("strings").forGetter(recipe -> recipe.strings)
                        </#if>
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
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
            inputs.replaceAll(ingredients -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            <#if data.enableIntList>
            List<Integer> numbers = NonNullList.withSize(buf.readVarInt(), 0);
            numbers.replaceAll(num -> buf.readVarInt());
            </#if>
            <#if data.enableStringList>
            List<String> strings = NonNullList.withSize(buf.readVarInt(), "");
            strings.replaceAll(string -> buf.readUtf());
            </#if>
            return new ${name}Recipe(ItemStack.STREAM_CODEC.decode(buf), inputs<#if data.enableIntList>, numbers</#if><#if data.enableStringList>, strings</#if>);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, ${name}Recipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.getItems()[0].getItem() == Items.AIR)
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, Ingredient.EMPTY);
                else
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
            }
            <#if data.enableIntList>
            buf.writeVarInt(recipe.integers().size());
            for (Integer num : recipe.integers()) {
            	buf.writeVarInt(num);
            }
            </#if>
            <#if data.enableStringList>
            buf.writeVarInt(recipe.strings().size());
            for (String string : recipe.strings()) {
            	buf.writeUtf(string);
            }
            </#if>
            ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));
        }
    }

}</#compress>