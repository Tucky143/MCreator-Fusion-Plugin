package ${package}.init;

@Mod.EventBusSubscriber(modid = ${JavaModName}.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}Configs {

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
		<#list configs as config>
			ModLoadingContext.get().registerConfig(ModConfig.Type.
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