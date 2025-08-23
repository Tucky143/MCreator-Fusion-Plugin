package ${package}.init;

@EventBusSubscriber(modid = ${JavaModName}.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ${JavaModName}RecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, "${modid}");
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, "${modid}");

    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        IEventBus bus = ModList.get().getModContainerById("${modid}").get().getEventBus();
		event.enqueueWork(() -> {
		    RECIPE_TYPES.register(bus);
		    SERIALIZERS.register(bus);

            <#list jeirecipetypes as type>
                RECIPE_TYPES.register("${type.getModElement().getRegistryName()}", () -> ${type.getModElement().getName()}Recipe.Type.INSTANCE);
                SERIALIZERS.register("${type.getModElement().getRegistryName()}", () -> ${type.getModElement().getName()}Recipe.Serializer.INSTANCE);
            </#list>
		});
    }

}