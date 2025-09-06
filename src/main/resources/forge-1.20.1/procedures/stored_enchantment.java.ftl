<#include "mcitems.ftl">

    if (itemstack.getItem() == ${mappedMCItemToItem(input$item)}) {
        Map<net.minecraft.world.item.enchantment.Enchantment, Integer> storedEnchantments = EnchantmentHelper.getEnchantments(itemstack);
        if (storedEnchantments.containsKey(${generator.map(field$enchant, "enchantments")})) {
            ${statement$foreach}
        
    }
}