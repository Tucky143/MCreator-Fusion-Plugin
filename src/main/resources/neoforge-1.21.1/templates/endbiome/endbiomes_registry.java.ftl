package ${package}.init;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ${JavaModName}EndBiomes {

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        <#list endbiomes as biome>
            TheEndBiomes.
            <#if biome.generationType == "Highlands">
                addHighlandsBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.biome).getRegistryName()}")), ${biome.weight}d);
            <#elseif biome.generationType == "Small islands">
                addSmallIslandsBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.biome).getRegistryName()}")), ${biome.weight}d);
            <#elseif biome.generationType == "Main island">
                addMainIslandBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.biome).getRegistryName()}")), ${biome.weight}d);
            <#elseif biome.generationType == "Midlands">
                addMidlandsBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:end_highlands")),
                    ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.midlands).getRegistryName()}")), ${biome.weight}d);
            <#else>
                addBarrensBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:end_highlands")),
                    ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.barrens).getRegistryName()}")), ${biome.weight}d);
            </#if>

            <#if biome.generationType == "Highlands" && biome.midlands != "Vanilla">
                TheEndBiomes.addMidlandsBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.biome).getRegistryName()}")),
                    ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.midlands).getRegistryName()}")), ${biome.midlandsWeight}d);
            </#if>

            <#if biome.generationType == "Highlands" && biome.barrens != "Vanilla">
                TheEndBiomes.addBarrensBiome(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.biome).getRegistryName()}")),
                    ResourceKey.create(Registries.BIOME, ResourceLocation.parse("${modid}:${w.getWorkspace().getModElementByName(biome.barrens).getRegistryName()}")), ${biome.barrensWeight}d);
            </#if>
        </#list>
        });
    }

}