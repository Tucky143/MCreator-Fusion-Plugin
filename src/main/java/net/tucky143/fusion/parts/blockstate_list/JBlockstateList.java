package net.tucky143.fusion.parts.blockstate_list;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleEntriesList;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.tucky143.fusion.elements.AnimatedBlock;

import javax.swing.*;
import java.util.List;

public class JBlockstateList extends JSimpleEntriesList<JBlockstateListEntry, AnimatedBlock.BlockstateListEntry> {
    public JBlockstateList(MCreator mcreator, IHelpContext gui) {
        super(mcreator, gui);
        this.add.setText(L10N.t("elementgui.animatedblock.add_blockstate", new Object[0]));
    }

    public AggregatedValidationResult getValidationResult() {
        AggregatedValidationResult validationResult = new AggregatedValidationResult();
        entryList.forEach(validationResult::addValidationElement);
        return validationResult;
    }

    protected JBlockstateListEntry newEntry(JPanel parent, List<JBlockstateListEntry> entryList, boolean userAction) {
        return new JBlockstateListEntry(this.mcreator, this.gui, parent, entryList, entryList.size() + 1);
    }
}
