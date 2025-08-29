package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import java.util.ArrayList;
import java.util.List;

public class JeiRecipe extends GeneratableElement {

    public String category;
    public MItemBlock result;
    public int count;
    @ModElementReference
    public List<MItemBlock> ingredients;
    public List<Integer> integers;
    public List<String> strings;
    public String recipetype = "";

    public JeiRecipe(ModElement element) {
        super(element);
        integers = new ArrayList<>();
        strings = new ArrayList<>();
        recipetype = "";
    }

}
