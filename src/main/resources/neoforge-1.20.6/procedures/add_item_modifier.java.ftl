<#include "attributes.ftl">
<#include "mcitems.ftl">
<#if false>
<#assign attr = getAttribute(field$attribute)>
<#assign which = getSource(field$attribute)>
<#if which == "custom" || which == "forge">
    if((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY) != ItemStack.EMPTY)
    {
        CompoundTag _tag = (${mappedMCItemToItemStackCode(input$item, 1)}).getOrCreateTag();
        if (!_tag.contains("AttributeModifiers", 9)) {
            _tag.put("AttributeModifiers", new ListTag());
        }
        ListTag _listtag = _tag.getList("AttributeModifiers", 10);
        CompoundTag _compoundtag = ${input$modifier}.save();
        int _index = -1;
        for(int _i = 0; _i < _listtag.size(); _i++) {
            if((_listtag.get(_i) instanceof CompoundTag _e && AttributeModifier.load(_e).equals(${input$modifier}))) {
                _index = _i;
                break;
            }
        }
        if(_index != -1) {
            _listtag.remove(_index);
        }
        <#if which == "custom">
        _compoundtag.putString("AttributeName", ${getAttributeResource(field$attribute)}.getId().toString());
        <#elseif which == "forge">
        _compoundtag.putString("AttributeName", ${getAttributeResource(field$attribute)}.unwrapKey().get().registry().toString());
        </#if>
        _compoundtag.putString("Slot", EquipmentSlot.${generator.map(field$slot, "slot")}.getName());
        _listtag.add(_compoundtag);
    }
<#else>
    (${mappedMCItemToItemStackCode(input$item, 1)}).addAttributeModifier(${attr}, ${input$modifier}, EquipmentSlot.${generator.map(field$slot, "slot")});
</#if>
</#if>
