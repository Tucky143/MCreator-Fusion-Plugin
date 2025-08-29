package net.tucky143.fusion.elements.legacy;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

public class LegacyAnvilRecipe extends GeneratableElement {
    public MItemBlock leftitem;
    public MItemBlock rightitem;
    public int rightcost;
    public int xpcost;
    public MItemBlock output;

    public LegacyAnvilRecipe(ModElement element) {
        super(element);
    }
}
