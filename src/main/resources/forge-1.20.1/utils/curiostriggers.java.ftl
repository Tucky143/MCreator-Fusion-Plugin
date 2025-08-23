<#include "procedures.java.ftl">

<#macro curioTick procedure="">
<#if hasProcedure(procedure)>
@Override
public void curioTick(SlotContext slotContext, ItemStack stack) {
	<@procedureCode procedure, {
		"x": "slotContext.entity().getX()",
		"y": "slotContext.entity().getY()",
		"z": "slotContext.entity().getZ()",
		"world": "slotContext.entity().level()",
		"entity": "slotContext.entity()",
		"itemstack": "stack"
	}/>
}
</#if>
</#macro>

<#macro onEquip procedure="">
<#if hasProcedure(procedure)>
@Override
public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
	<@procedureCode procedure, {
		"x": "slotContext.entity().getX()",
		"y": "slotContext.entity().getY()",
		"z": "slotContext.entity().getZ()",
		"world": "slotContext.entity().level()",
		"entity": "slotContext.entity()",
		"itemstack": "stack"
	}/>
}
</#if>
</#macro>

<#macro onUnequip procedure="">
<#if hasProcedure(procedure)>
@Override
public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
	<@procedureCode procedure, {
		"x": "slotContext.entity().getX()",
		"y": "slotContext.entity().getY()",
		"z": "slotContext.entity().getZ()",
		"world": "slotContext.entity().level()",
		"entity": "slotContext.entity()",
		"itemstack": "stack"
	}/>
}
</#if>
</#macro>