package ${package}.jei_recipes;

@EventBusSubscriber(modid = "${modid}", bus = EventBusSubscriber.Bus.MOD)
public class EmptyIngredient implements ICustomIngredient {
    public static final EmptyIngredient INSTANCE = new EmptyIngredient();

    public static final MapCodec<EmptyIngredient> CODEC = MapCodec.unit(INSTANCE);

    public static final StreamCodec<RegistryFriendlyByteBuf, EmptyIngredient> STREAM_CODEC =
        StreamCodec.unit(INSTANCE);

    public static final IngredientType<EmptyIngredient> TYPE = 
        new IngredientType<>(CODEC, STREAM_CODEC);
    
    private EmptyIngredient() {
    }
    
    @Override
    public Stream<Holder<Item>> items() {
        return Stream.of(Items.AIR.builtInRegistryHolder());
    }
    
    @Override
    public boolean test(ItemStack stack) {
        return false;
    }
    
    @Override
    public boolean isSimple() {
        return true;
    }
    
    @Override
    public IngredientType<?> getType() {
        return TYPE;
    }
    
    @Override
    public SlotDisplay display() {
        return SlotDisplay.Empty.INSTANCE;
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyIngredient;
    }
    
    @Override
    public int hashCode() {
        return EmptyIngredient.class.hashCode();
    }
    
    @Override
    public String toString() {
        return "EmptyIngredient";
    }

    public static Ingredient create() {
        return new Ingredient(INSTANCE);
    }

    @SubscribeEvent
    public static void registerIngredientType(RegisterEvent event) {
        if (event.getRegistryKey().equals(NeoForgeRegistries.Keys.INGREDIENT_TYPES)) {
            event.register(NeoForgeRegistries.Keys.INGREDIENT_TYPES,
                ResourceLocation.fromNamespaceAndPath("${modid}", "empty"),
                () -> TYPE
            );
        }
    }
}