package ${package}.init;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}CustomCuriosSlots {

	@SubscribeEvent
	public static void enqueueIMC(final InterModEnqueueEvent event) {
        <#list curiosslots as slot>
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("${slot.getModElement().getRegistryName()}").icon(new ResourceLocation("curios:slot/${slot.texture?replace(".png", "")}")).size(${slot.amount}).build());
        </#list>
	}

}