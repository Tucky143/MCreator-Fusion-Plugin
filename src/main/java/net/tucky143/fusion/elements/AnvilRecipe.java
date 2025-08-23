package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

public class AnvilRecipe extends GeneratableElement {
    public MItemBlock leftitem;
    public MItemBlock rightitem;
    public int rightcost;
    public int xpcost;
    public MItemBlock output;
    public AnvilRecipe(ModElement element) {
        super(element);
    }

}
