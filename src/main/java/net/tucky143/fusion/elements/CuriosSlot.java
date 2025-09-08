package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.workspace.elements.ModElement;

import java.util.List;

public class CuriosSlot extends GeneratableElement {

    public String texture;
    public String name;
    public List<MItemBlock> items;
    public int amount;
    public CuriosSlot(ModElement element) {
        super(element);
    }

}
