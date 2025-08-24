package net.tucky143.fusion.ui.modgui;

import net.mcreator.element.ModElementType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.parts.JBlockstateList;
import net.tucky143.fusion.elements.Blockstates;
import net.tucky143.fusion.parts.PluginElementTypes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class BlockstatesGUI extends ModElementGUI<Blockstates> {
    private final SearchableComboBox<String> block;
    private JBlockstateList blockstateList;

    public BlockstatesGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        block = new SearchableComboBox<>();
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        ComponentUtils.deriveFont(block, 16);
        this.setOpaque(false);
        blockstateList = new JBlockstateList(this.mcreator, this);

        JPanel pane3 = new JPanel(new BorderLayout());
        pane3.setOpaque(false);
        JPanel northPanel = new JPanel(new GridLayout(1, 2, 0, 2));
        northPanel.setOpaque(false);
        northPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("blockstates/block"), L10N.label("elementgui.blockstates.block", new Object[0])));
        northPanel.add(this.block);

        JComponent mainEditor = PanelUtils.northAndCenterElement(new JEmptyBox(), this.blockstateList);
        mainEditor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pane3.add(PanelUtils.northAndCenterElement(PanelUtils.join(0, new Component[]{northPanel}), mainEditor));
        this.addPage(pane3, false);
    }

    private boolean blockAlreadyHasBlockstates() {
        List<Blockstates> blockstates = this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.BLOCKSTATES && var != this.getModElement();
        }).map(blockstate -> ((Blockstates)blockstate.getGeneratableElement())).toList();
        if (block.getSelectedItem() != null) {
            for (Blockstates blockstate : blockstates)
                if (blockstate.block.equals(block.getSelectedItem()))
                    return true;
        }
        return false;
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(block, this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == ModElementType.BLOCK;
        }).map(ModElement::getName).collect(Collectors.toList()));
        blockstateList.reloadDataLists();
    }

    protected void afterGeneratableElementStored() {
        try {
            mcreator.getGenerator().generateElement(mcreator.getWorkspace().getModElementByName(block.getSelectedItem()).getGeneratableElement(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (block.getSelectedItem() == null)
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.blockstates.needs_block", new Object[0]));
        if (blockstateList.getEntries().isEmpty()) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.blockstates.needs_blockstates", new Object[0]));
        }
        if (blockAlreadyHasBlockstates())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.blockstates.already_exists", new Object[0]));
        return blockstateList.getValidationResult();
    }

    public void openInEditingMode(Blockstates blockstates) {
        block.setSelectedItem(blockstates.block);
        blockstateList.setEntries(blockstates.blockstateList);
    }

    public Blockstates getElementFromGUI() {
        Blockstates blockstates = new Blockstates(this.modElement);
        blockstates.block = block.getSelectedItem();
        blockstates.blockstateList = blockstateList.getEntries();
        return blockstates;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return new URI(MCreatorApplication.SERVER_DOMAIN + "/wiki/how-make-block");
    }

}
