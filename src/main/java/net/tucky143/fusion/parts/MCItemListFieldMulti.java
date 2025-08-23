package net.tucky143.fusion.parts;

import net.mcreator.element.parts.MItemBlock;
import net.mcreator.minecraft.MCItem;
import net.mcreator.minecraft.TagType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.dialogs.AddTagDialog;
import net.mcreator.ui.dialogs.MCItemSelectorDialog;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.util.image.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MCItemListFieldMulti extends JItemListFieldMulti<MItemBlock> {
    private final MCItem.ListProvider supplier;

    public MCItemListFieldMulti(MCreator mcreator, MCItem.ListProvider supplier) {
        this(mcreator, supplier, false, false);
    }

    public MCItemListFieldMulti(MCreator mcreator, MCItem.ListProvider supplier, boolean excludeButton, boolean supportTags) {
        super(mcreator, excludeButton, supportTags);
        this.supplier = supplier;
        this.elementsList.setCellRenderer(new CustomListCellRenderer());
    }

    public List<MItemBlock> getElementsToAdd() {
        return (List) MCItemSelectorDialog.openMultiSelectorDialog(this.mcreator, this.supplier).stream().map((e) -> {
            return new MItemBlock(this.mcreator.getWorkspace(), e.getName());
        }).collect(Collectors.toList());
    }

    protected List<MItemBlock> getTagsToAdd() {
        TagType tagType = TagType.BLOCKS;
        List<MCItem> items = this.supplier.provide(this.mcreator.getWorkspace());
        Iterator var3 = items.iterator();

        while(var3.hasNext()) {
            MCItem item = (MCItem)var3.next();
            if (item.getType().equals("item")) {
                tagType = TagType.ITEMS;
                break;
            }
        }

        List<MItemBlock> tags = new ArrayList();
        String tag = AddTagDialog.openAddTagDialog(this.mcreator, this.mcreator, tagType, new String[]{"tag", "category/tag"});
        if (tag != null) {
            tags.add(new MItemBlock(this.mcreator.getWorkspace(), "TAG:" + tag));
        }

        return tags;
    }

    class CustomListCellRenderer extends JLabel implements ListCellRenderer<MItemBlock> {
        CustomListCellRenderer() {
        }

        public Component getListCellRendererComponent(JList<? extends MItemBlock> list, MItemBlock value, int index, boolean isSelected, boolean cellHasFocus) {
            this.setOpaque(isSelected);
            this.setBackground(isSelected ? Theme.current().getForegroundColor() : Theme.current().getAltBackgroundColor());
            this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Theme.current().getBackgroundColor()), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
            this.setToolTipText(value.getUnmappedValue().replace("CUSTOM:", "").replace("Blocks.", "").replace("Items.", ""));
            this.setIcon(new ImageIcon(ImageUtils.resizeAA(MCItem.getBlockIconBasedOnName(mcreator.getWorkspace(), value.getUnmappedValue()).getImage(), 25)));
            this.setText("" + index);
            return this;
        }
    }
}
