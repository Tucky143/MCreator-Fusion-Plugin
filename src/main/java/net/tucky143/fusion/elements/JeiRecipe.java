package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import java.util.List;

public class JeiRecipe extends GeneratableElement {

    public String category;
    public MItemBlock result;
    public int count;
    @ModElementReference
    public List<MItemBlock> ingredients;

    public JeiRecipe(ModElement element) {
        super(element);
    }

}
