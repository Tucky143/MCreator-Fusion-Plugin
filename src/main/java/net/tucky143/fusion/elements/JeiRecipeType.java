package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.TextureReference;

import java.util.ArrayList;
import java.util.List;

public class JeiRecipeType extends GeneratableElement {

    @TextureReference(TextureType.SCREEN)
    public String textureSelector;
    public int width;
    public int height;
    public MItemBlock icon;
    public MItemBlock craftingtable;
    public List<MItemBlock> craftingtables;
    public boolean enableCraftingtable;
    public String title;
    public List<JeiSlotListEntry> slotList = new ArrayList<>();

    public static class JeiSlotListEntry {
        public JeiSlotListEntry() {}

        public String type;
        public int x;
        public int y;
        public int slotid;
    }


    public JeiRecipeType(ModElement element) { super(element); }

    public int getIngredientCount() {
        int count = 0;

        for (int i = 0; i < slotList.size(); i++) {
            JeiSlotListEntry entry = slotList.get(i);

            if (entry.type.equals("INPUT")) {
                boolean isUnique = true;
                for (int j = 0; j < i; j++) {
                    JeiSlotListEntry previousEntry = slotList.get(j);
                    if (entry.slotid == previousEntry.slotid) {
                        isUnique = false;
                        break;
                    }
                }

                if (isUnique) {
                    count++;
                }
            }
        }

        return count;
    }

}

