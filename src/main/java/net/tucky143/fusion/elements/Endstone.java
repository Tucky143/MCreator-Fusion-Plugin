package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

import java.util.List;

public class Endstone extends GeneratableElement {
    public MItemBlock block;
    public List<String> mixins = List.of("ChorusFlowerBlockMixin", "ChorusPlantBlockMixin");
    public Endstone(ModElement element) {
        super(element);
    }
}
