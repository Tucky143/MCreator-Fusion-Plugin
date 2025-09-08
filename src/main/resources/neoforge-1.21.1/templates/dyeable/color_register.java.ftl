package ${package}.client.color;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD) public class RegisterColor {
    @SubscribeEvent public static void onItemColorRegister(RegisterColorHandlersEvent.Item event) {
        <#list w.getGElementsOfType("dyeable_item")?filter(e -> e.isDyeable) as e>
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, ${e.defaultColor.getRGB()}), ${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}.get());
        </#list>

        <#list w.getGElementsOfType("dyeable_armor")?filter(e -> e.hasDyeablePart()) as e>
        <#list e.getDyeableParts() as part>
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, ${e.getDefaultColor(part).getRGB()}), ${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()}.get());
        </#list>
        </#list>

        <#if w.getGElementsOfType("dyeable_item")?filter(e -> e.canWaterRemoveColor)?size != 0 ||
            w.getGElementsOfType("dyeable_armor")?filter(e -> e.canWaterRemoveAnyColor())?size != 0>
        Map<Item, CauldronInteraction> WATER_MAP = CauldronInteraction.WATER.map();
        CauldronInteraction.addDefaultInteractions(WATER_MAP);
        </#if>

        <#if w.getGElementsOfType("dyeable_item")?filter(e -> e.canLavaRemoveColor)?size != 0 ||
            w.getGElementsOfType("dyeable_armor")?filter(e -> e.canLavaRemoveAnyColor())?size != 0>
        Map<Item, CauldronInteraction> LAVA_MAP = CauldronInteraction.LAVA.map();
        CauldronInteraction.addDefaultInteractions(LAVA_MAP);
        </#if>

        <#if w.getGElementsOfType("dyeable_item")?filter(e -> e.canPowderSnowRemoveColor)?size != 0 ||
            w.getGElementsOfType("dyeable_armor")?filter(e -> e.canPowderSnowRemoveAnyColor())?size != 0>
        Map<Item, CauldronInteraction> POWDER_SNOW_MAP = CauldronInteraction.POWDER_SNOW.map();
        CauldronInteraction.addDefaultInteractions(POWDER_SNOW_MAP);
        </#if>



        <#list w.getGElementsOfType("dyeable_item")?filter(e -> e.isDyeable) as e>
        <#if e.canWaterRemoveColor>
        WATER_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>

        <#if e.canLavaRemoveColor>
        LAVA_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>

        <#if e.canPowderSnowRemoveColor>
        POWDER_SNOW_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>
        </#list>


        <#list w.getGElementsOfType("dyeable_armor")?filter(e -> e.hasDyeablePart()) as e>
        <#list e.getDyeableRemovalParts() as part>
        <#if e.canWaterRemoveColorPart(part)>
        WATER_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>

        <#if e.canLavaRemoveColorPart(part)>
        LAVA_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>

        <#if e.canPowderSnowRemoveColorPart(part)>
        POWDER_SNOW_MAP.put(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()}.get(), CauldronInteraction.DYED_ITEM);
        </#if>
        </#list>
        </#list>
    }
}