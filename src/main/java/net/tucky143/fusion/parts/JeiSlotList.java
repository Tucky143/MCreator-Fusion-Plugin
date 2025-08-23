package net.tucky143.fusion.parts;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleEntriesList;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.tucky143.fusion.elements.JeiRecipeType;

import javax.swing.*;
import java.util.List;

public class JeiSlotList extends JSimpleEntriesList<JeiSlotListEntry, JeiRecipeType.JeiSlotListEntry> {
    public JeiSlotList(MCreator mcreator, IHelpContext gui) {
        super(mcreator, gui);
        this.add.setText(L10N.t("elementgui.jeirecipetype.add_entry", new Object[0]));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.jeirecipetype.slots", new Object[0]), 0, 0, this.getFont().deriveFont(12.0F), Theme.current().getForegroundColor()));
    }

    protected JeiSlotListEntry newEntry(JPanel parent, List<JeiSlotListEntry> entryList, boolean userAction) {
        return new JeiSlotListEntry(this.mcreator, this.gui, parent, entryList);
    }
}
