package ${package}.init;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ${JavaModName}CustomCuriosSlots {

	@SubscribeEvent
	public static void enqueueIMC(final InterModEnqueueEvent event) {
        <#list curiosslots as slot>
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("${slot.getModElement().getRegistryName()}").icon(ResourceLocation.parse("curios:slot/${slot.texture?replace(".png", "")}")).size(${slot.amount}).build());
        </#list>
	}

}