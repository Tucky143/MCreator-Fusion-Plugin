package net.tucky143.fusion.elements.legacy;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.Fluid;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import java.util.ArrayList;
import java.util.List;

public class LegacyJeiRecipe extends GeneratableElement {

    public String category;
    public MItemBlock output;
    public int outputAmount;
    public List<JeiRecipeListEntry> inputs = new ArrayList<>();

    public static class JeiRecipeListEntry {
        public JeiRecipeListEntry() {}

        public String type;
        public String name;
        @ModElementReference
        public MItemBlock itemInput;
        public int itemAmount;
        @ModElementReference
        public Fluid fluidInput;
        public int fluidAmount;
    }

    public LegacyJeiRecipe(ModElement element) {
        super(element);
    }

}
