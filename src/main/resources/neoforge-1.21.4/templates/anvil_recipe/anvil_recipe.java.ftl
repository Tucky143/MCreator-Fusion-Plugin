<#include "../mcitems.ftl">

package ${package}.anvilrecipes;

@EventBusSubscriber
public class ${name}AnvilRecipe {

    @SubscribeEvent
    public static void execute(AnvilUpdateEvent event) {
        if ((event.getLeft().getItem() == ${mappedMCItemToItem(data.leftitem)}) && (event.getRight().getItem() == ${mappedMCItemToItem(data.rightitem)})) {
            if ((event.getLeft().getCount() == 1) && (event.getRight().getCount() >= ${data.rightcost})) {
                event.setMaterialCost(${data.rightcost});
                event.setCost(${data.xpcost});
                event.setOutput(${mappedMCItemToItemStackCode(data.output)});
            }
        }
    }

}