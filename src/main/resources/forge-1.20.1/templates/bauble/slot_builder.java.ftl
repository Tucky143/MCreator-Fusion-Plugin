package ${package}.init;

<#assign hasHead = false>
<#assign hasNecklace = false>
<#assign hasBack = false>
<#assign hasBody = false>
<#assign hasBracelet = false>
<#assign hasHands = false>
<#assign hasRing = false>
<#assign hasBelt = false>
<#assign hasCharm = false>
<#assign hasCurio = false>

<#assign headNum = 0>
<#assign necklaceNum = 0>
<#assign backNum = 0>
<#assign bodyNum = 0>
<#assign braceletNum = 0>
<#assign handsNum = 0>
<#assign ringNum = 0>
<#assign beltNum = 0>
<#assign charmNum = 0>
<#assign curioNum = 0>

<#list curiosbaubles as bauble>
<#if bauble.addSlot>
<#if bauble.slotType == "HEAD">
<#assign hasHead = true>
<#assign headNum += bauble.slotAmount>
<#elseif bauble.slotType == "NECKLACE">
<#assign hasNecklace = true>
<#assign necklaceNum += bauble.slotAmount>
<#elseif bauble.slotType == "BACK">
<#assign hasBack = true>
<#assign backNum += bauble.slotAmount>
<#elseif bauble.slotType == "BODY">
<#assign hasBody = true>
<#assign bodyNum += bauble.slotAmount>
<#elseif bauble.slotType == "BRACELET">
<#assign hasBracelet = true>
<#assign braceletNum += bauble.slotAmount>
<#elseif bauble.slotType == "HANDS">
<#assign hasHands = true>
<#assign handsNum += bauble.slotAmount>
<#elseif bauble.slotType == "RING">
<#assign hasRing = true>
<#assign ringNum += bauble.slotAmount>
<#elseif bauble.slotType == "BELT">
<#assign hasBelt = true>
<#assign beltNum += bauble.slotAmount>
<#elseif bauble.slotType == "CHARM">
<#assign hasCharm = true>
<#assign charmNum += bauble.slotAmount>
<#elseif bauble.slotType == "CURIO">
<#assign hasCurio = true>
<#assign curioNum += bauble.slotAmount>
</#if>
</#if>
</#list>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}CuriosSlots {

	@SubscribeEvent
	public static void enqueueIMC(final InterModEnqueueEvent event) {
	<#if hasHead>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.HEAD.getMessageBuilder().size(${headNum}).build());
	</#if>
	<#if hasNecklace>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.NECKLACE.getMessageBuilder().size(${necklaceNum}).build());
	</#if>
	<#if hasBack>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.BACK.getMessageBuilder().size(${backNum}).build());
	</#if>
	<#if hasBody>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.BODY.getMessageBuilder().size(${bodyNum}).build());
	</#if>
	<#if hasBracelet>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.BRACELET.getMessageBuilder().size(${braceletNum}).build());
	</#if>
	<#if hasHands>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.HANDS.getMessageBuilder().size(${handsNum}).build());
	</#if>
	<#if hasRing>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.RING.getMessageBuilder().size(${ringNum}).build());
	</#if>
	<#if hasBelt>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.BELT.getMessageBuilder().size(${beltNum}).build());
	</#if>
	<#if hasCharm>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.CHARM.getMessageBuilder().size(${charmNum}).build());
	</#if>
	<#if hasCurio>
        	InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> 
			SlotTypePreset.CURIO.getMessageBuilder().size(${curioNum}).build());
	</#if>
	}

}