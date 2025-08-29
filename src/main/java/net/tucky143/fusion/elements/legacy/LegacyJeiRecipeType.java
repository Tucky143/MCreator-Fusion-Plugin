package net.tucky143.fusion.elements.legacy;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;
import net.mcreator.workspace.references.TextureReference;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LegacyJeiRecipeType extends GeneratableElement {

    @TextureReference(TextureType.SCREEN)
    public String textureSelector;
    public int width;
    public int height;
    public MItemBlock icon;
    public MItemBlock craftingtable;
    public List<MItemBlock> craftingtables;
    public boolean enableCraftingtable;
    public String title;
    @ModElementReference @Nullable public String caGui;
    public boolean clickableArea;
    public int caX;
    public int caY;
    public int caWidth;
    public int caHeight;
    public List<JeiSlotListEntry> slotList = new ArrayList<>();

    public static class JeiSlotListEntry {
        public JeiSlotListEntry() {}

        public String type;
        public String name;
        public int x;
        public int y;
        public boolean optional;
    }

    public LegacyJeiRecipeType(ModElement element) {
        super(element);
    }
}

