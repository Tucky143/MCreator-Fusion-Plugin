package ${package}.init;

@EventBusSubscriber(modid = ${JavaModName}.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ${JavaModName}Configs {

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
		<#list configs as config>
			ModList.get().getModContainerById("${modid}").get().registerConfig(ModConfig.Type.
		<#if config.configType = 0>
			COMMON,
		<#elseif config.configType = 1>
			SERVER,
		<#else>
			CLIENT, 
		</#if>
			${config.getModElement().getName()}Configuration.SPEC,
                	"${config.file}.toml");
		</#list>
		});
	}

}