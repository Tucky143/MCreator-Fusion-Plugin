package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

import java.util.List;

public class JeiInformation extends GeneratableElement {
    public List<MItemBlock> items;
    public List<String> information;
    public JeiInformation(ModElement element) {
        super(element);
    }

    public String getDescription() {
        String description = "";
        for (int i = 0; i < information.size(); i++)
            description += i == 0 ? information.get(i) : "\n" + information.get(i);
        return description;
    }

}