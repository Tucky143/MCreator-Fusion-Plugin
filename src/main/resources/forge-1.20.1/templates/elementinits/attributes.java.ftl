<#-- @formatter:off -->
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package ${package}.init;
@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD) public class ${JavaModName}Attributes {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ${JavaModName}.MODID);
    <#list attributes as attribute>
        public static final RegistryObject<Attribute>${attribute.getModElement().getRegistryNameUpper()?replace("_", "")} = REGISTRY.register("${attribute.getModElement().getRegistryName().toLowerCase()}", () -> (new RangedAttribute( "attribute." + ${JavaModName}.MODID + ".${attribute.getModElement().getRegistryName()}", ${attribute.defaultValue}, ${attribute.minValue}, ${attribute.maxValue})).setSyncable(true));
    </#list>

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        <#assign typesListNeeded = false>
        <#list attributes as attribute>
            <#list attribute.entities as entity>
                <#if entity == "AmbientCreature" || entity == "Animal" || entity == "Monster" || entity == "AbstractGolem" || entity == "Mob" || entity == "WaterAnimal">
                    <#assign typesListNeeded = true>
                </#if>
            </#list>
        </#list>
        <#if typesListNeeded == true>
            List<EntityType<? extends LivingEntity>> entityTypes = event.getTypes();
        </#if>
        <#list attributes as attribute>
            <#assign condition = "">
            <#list attribute.entities as entity>
                 <#if entity == "AmbientCreature" || entity == "Animal" || entity == "Monster" || entity == "AbstractGolem" || entity == "Mob" || entity == "WaterAnimal">
                    <#assign condition = condition + "|| baseClass.isAssignableFrom(${entity}.class)">
                 </#if>
            </#list>
            <#list attribute.entities as entity>
                <#if condition == "">
                    <#assign e = generator.map(entity.getUnmappedValue(), "entities", 1)!"null">
                    <#if e != "null">
                        event.add(${e}, ${attribute.getModElement().getRegistryNameUpper()?replace("_", "")}.get());
                    </#if>
                </#if>
            </#list>
            <#if condition != "">
                entityTypes.forEach((e) -> {
                    Class<? extends Entity> baseClass = e.getBaseClass();
                    if(${condition?keep_after("|| ")}) {
                        event.add(e, ${attribute.getModElement().getRegistryNameUpper()?replace("_", "")}.get());
                    }
                });
            </#if>
        </#list>
    }
    <#-- Determine if we have persistent player attributes -->
    <#assign hasPersistentPlayerAttrs = false>
    <#list attributes as a>
        <#if (a.isPersistent??) && a.isPersistent && a.entities?seq_contains("Player")>
            <#assign hasPersistentPlayerAttrs = true>
            <#break>
        </#if>
    </#list>

    <#if hasPersistentPlayerAttrs>
        @Mod.EventBusSubscriber
        private class Utils {
            @SubscribeEvent
            public static void persistAttributes(PlayerEvent.Clone event) {
                Player oldP = event.getOriginal();
                Player newP = (Player) event.getEntity();
                <#list attributes as attribute>
                    <#if attribute.isPersistent && attribute.entities?seq_contains("Player")>
                        newP.getAttribute(${attribute.getModElement().getRegistryNameUpper()?replace("_", "")}.get())
                            .setBaseValue(
                                oldP.getAttribute(${attribute.getModElement().getRegistryNameUpper()?replace("_", "")}.get()).getBaseValue()
                            );
                    </#if>
                </#list>
            }
        }
    </#if>
}
<#-- @formatter:on -->