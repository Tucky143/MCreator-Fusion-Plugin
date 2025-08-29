package net.tucky143.fusion.parts.legacy;

import net.mcreator.element.parts.Fluid;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.FluidListField;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.workspace.Workspace;
import net.tucky143.fusion.elements.legacy.LegacyJeiRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JeiRecipeListEntry extends JSimpleListEntry<LegacyJeiRecipe.JeiRecipeListEntry> {
    private final Object[] noParams = new Object[0];

    private final JComboBox<String> type = new JComboBox<>(new String[]{"ITEM_INPUT", "FLUID_INPUT"});
    private final VTextField name = new VTextField();
    private MCItemHolder itemInput;
    private final JSpinner itemAmount = new JSpinner(new SpinnerNumberModel(1, 1, 64 ,1));
    private FluidListField fluidInput;
    private final JSpinner fluidAmount = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));

    private final Workspace workspace;

    public JeiRecipeListEntry(MCreator mcreator, IHelpContext gui, JPanel parent, List<JeiRecipeListEntry> entryList) {
        super(parent, entryList);

        itemInput = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);
        fluidInput = new FluidListField(mcreator);

        JLabel mb = new JLabel("mB");
        mb.setVisible(false);
        JLabel itemText = new JLabel("Item Input:");
        JLabel fluidText = new JLabel("Fluid Input:");
        fluidText.setVisible(false);

        this.workspace = mcreator.getWorkspace();
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/recipe/slot_type"), L10N.label("elementGui.jeiRecipeType.slot_type", noParams)));
        this.line.add(type);
        this.line.add(HelpUtils.wrapWithHelpButton(gui.withEntry("jei/recipe/slot_name"), L10N.label("elementGui.jeiRecipeType.slot_name", noParams)));
        name.setPreferredSize(new Dimension(80, 20));
        this.line.add(name);
        this.line.add(itemText);
        itemInput.setPreferredSize(new Dimension(30, 30));
        this.line.add(itemInput);
        this.line.add(fluidText);
        fluidInput.setPreferredSize(new Dimension(250, 30));
        fluidInput.setVisible(false);
        this.line.add(fluidInput);
        this.line.add(new JLabel("Amount:"));
        itemAmount.setPreferredSize(new Dimension(90, 20));
        this.line.add(itemAmount);
        fluidAmount.setVisible(false);
        fluidAmount.setPreferredSize(new Dimension(90, 20));
        this.line.add(fluidAmount);
        this.line.add(mb);

        this.type.addActionListener(e -> {
            boolean itemEnabled = switch (type.getSelectedItem().toString()) {
                case "ITEM_INPUT" -> true;
                case "FLUID_INPUT" -> false;
                default -> true;
            };

            itemText.setVisible(itemEnabled);
            itemInput.setVisible(itemEnabled);
            itemAmount.setVisible(itemEnabled);

            fluidText.setVisible(!itemEnabled);
            fluidInput.setVisible(!itemEnabled);
            fluidAmount.setVisible(!itemEnabled);
            mb.setVisible(!itemEnabled);
        });

        this.fluidInput.addChangeListener(e -> {
            List<Fluid> fluid = new ArrayList<>();
            List<Fluid> oldFluids = fluidInput.getListElements();
            if (oldFluids.size() > 1) {
                fluid.add(oldFluids.getLast());
                fluidInput.setListElements(fluid);
            }
        });
    }

    public void reloadDataLists() {
        super.reloadDataLists();
    }

    protected void setEntryEnabled(boolean enabled) {
        this.type.setEnabled(enabled);
        this.name.setEnabled(enabled);
        this.itemInput.setEnabled(enabled);
        this.itemAmount.setEnabled(enabled);
        this.fluidInput.setEnabled(enabled);
        this.fluidAmount.setEnabled(enabled);
    }

    public LegacyJeiRecipe.JeiRecipeListEntry getEntry() {
        LegacyJeiRecipe.JeiRecipeListEntry entry = new LegacyJeiRecipe.JeiRecipeListEntry();
        entry.type = (String) type.getSelectedItem();
        entry.name = name.getText();
        if (!itemInput.getBlock().isEmpty()) {
            entry.itemInput = itemInput.getBlock();
        }
        entry.itemAmount = (int) itemAmount.getValue();
        if (!fluidInput.getListElements().isEmpty()) {
            entry.fluidInput = fluidInput.getListElements().getFirst();
        }
        entry.fluidAmount = (int) fluidAmount.getValue();
        return entry;
    }

    public void setEntry(LegacyJeiRecipe.JeiRecipeListEntry entry) {
        type.setSelectedItem(entry.type);
        name.setText(entry.name);
        if (entry.itemInput != null) {
            itemInput.setBlock(entry.itemInput);
        }
        itemAmount.setValue(entry.itemAmount);
        if (entry.fluidInput != null) {
            fluidInput.setListElements(List.of(entry.fluidInput));
        }
        fluidAmount.setValue(entry.fluidAmount);
    }
}
