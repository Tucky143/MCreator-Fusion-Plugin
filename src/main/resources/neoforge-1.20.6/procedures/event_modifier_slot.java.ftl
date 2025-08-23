<#include "mcitems.ftl">
if(event instanceof ItemAttributeModifierEvent _event && _event.getSlotType() == EquipmentSlot.${generator.map(field$slot, "slot")}) {
    ${statement$procedures}
}