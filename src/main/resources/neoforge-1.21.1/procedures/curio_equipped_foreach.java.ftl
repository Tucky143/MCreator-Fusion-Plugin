<#include "mcitems.ftl">
if (${input$entity} instanceof LivingEntity lv) {
CuriosApi.getCuriosHelper().findCurios(lv, ${mappedMCItemToItem(input$item)}).forEach(item -> {
	ItemStack itemstackiterator = item.stack();
	${statement$foreach}
	});
}