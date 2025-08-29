package net.tucky143.fusion.parts.legacy;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.workspace.Workspace;
import net.tucky143.fusion.elements.legacy.LegacyJeiRecipeType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class JeiSlotListEntry extends JSimpleListEntry<LegacyJeiRecipeType.JeiSlotListEntry> {
    private final Object[] noParams = new Object[0];

    private final JComboBox<String> type = new JComboBox<>(new String[]{"ITEM_INPUT", "FLUID_INPUT", "OUTPUT"});
    private final VTextField name = new VTextField();
    private final JSpinner x = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JSpinner y = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JCheckBox optional = new JCheckBox();

    private final Workspace workspace;

    public JeiSlotListEntry(MCreator mcreator, IHelpContext gui, JPanel parent, List<JeiSlotListEntry> entryList) {
        super(parent, entryList);
        this.workspace = mcreator.getWorkspace();
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/recipe_type/slot_type"), L10N.label("elementGui.jeiRecipeType.slot_type", noParams)));
        this.line.add(this.type);
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/recipe_type/slot_name"), L10N.label("elementGui.jeiRecipeType.slot_name", noParams)));
        name.setPreferredSize(new Dimension(80, 20));
        this.line.add(this.name);
        this.line.add(new JLabel("X:"));
        x.setPreferredSize(new Dimension(90, 20));
        this.line.add(this.x);
        this.line.add(new JLabel("Y:"));
        y.setPreferredSize(new Dimension(90, 20));
        this.line.add(this.y);
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/recipe_type/optional"), L10N.label("elementGui.jeiRecipeType.optional", noParams)));
        this.line.add(optional);

        this.type.addActionListener((e) -> {
            name.setEnabled(!type.getSelectedItem().equals("OUTPUT"));
            optional.setEnabled(type.getSelectedItem().equals("ITEM_INPUT"));
        });
    }

    public void reloadDataLists() {
        super.reloadDataLists();
    }

    protected void setEntryEnabled(boolean enabled) {
        this.type.setEnabled(enabled);
        this.name.setEnabled(enabled);
        this.x.setEnabled(enabled);
        this.y.setEnabled(enabled);
        this.optional.setEnabled(enabled);
    }

    public LegacyJeiRecipeType.JeiSlotListEntry getEntry() {
        LegacyJeiRecipeType.JeiSlotListEntry entry = new LegacyJeiRecipeType.JeiSlotListEntry();
        entry.type = (String) type.getSelectedItem();
        entry.name = name.getText();
        entry.x = (int) x.getValue();
        entry.y = (int) y.getValue();
        entry.optional = optional.isSelected();
        return entry;
    }

    public void setEntry(LegacyJeiRecipeType.JeiSlotListEntry entry) {
        type.setSelectedItem(entry.type);
        name.setText(entry.name);
        x.setValue(entry.x);
        y.setValue(entry.y);
        optional.setSelected(entry.optional);
    }
}
