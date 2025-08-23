package net.tucky143.fusion.parts;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.workspace.Workspace;
import net.tucky143.fusion.elements.JeiRecipeType;

import javax.swing.*;
import java.util.List;
public class JeiSlotListEntry extends JSimpleListEntry<JeiRecipeType.JeiSlotListEntry> {

    private final JComboBox<String> type = new JComboBox<>(new String[]{"INPUT", "OUTPUT"});
    private final JSpinner x = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JSpinner y = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JSpinner slotid = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final Workspace workspace;

    public JeiSlotListEntry(MCreator mcreator, IHelpContext gui, JPanel parent, List<JeiSlotListEntry> entryList) {
        super(parent, entryList);
        this.workspace = mcreator.getWorkspace();
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/slot_type"), L10N.label("elementgui.jeirecipetype.slot_type", new Object[0])));
        this.line.add(this.type);
        this.line.add(new JLabel("X:"));
        this.line.add(this.x);
        this.line.add(new JLabel("Y:"));
        this.line.add(this.y);
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/slot_id"), L10N.label("elementgui.jeirecipetype.slot_id", new Object[0])));
        this.line.add(this.slotid);

        this.type.addActionListener((e) -> {
            slotid.setEnabled(type.getSelectedItem().equals("INPUT"));
        });
    }

    public void reloadDataLists() {
        super.reloadDataLists();
    }

    protected void setEntryEnabled(boolean enabled) {
        this.type.setEnabled(enabled);
        this.x.setEnabled(enabled);
        this.y.setEnabled(enabled);
        this.slotid.setEnabled(enabled);
    }

    public JeiRecipeType.JeiSlotListEntry getEntry() {
        JeiRecipeType.JeiSlotListEntry entry = new JeiRecipeType.JeiSlotListEntry();
        entry.type = (String) type.getSelectedItem();
        entry.x = (int) x.getValue();
        entry.y = (int) y.getValue();
        entry.slotid = (int) slotid.getValue();
        return entry;
    }

    public void setEntry(JeiRecipeType.JeiSlotListEntry e) {
        type.setSelectedItem(e.type);
        x.setValue(e.x);
        y.setValue(e.y);
        slotid.setValue(e.slotid);
    }
}
