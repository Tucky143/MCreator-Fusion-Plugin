package net.tucky143.fusion.ui.modgui;

import net.mcreator.element.ModElementType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.LootModifier;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class LootModifierGUI extends ModElementGUI<LootModifier> {
    private final ValidationGroup page1group = new ValidationGroup();
    private final VTextField modifiedTable;
    private final SearchableComboBox<String> modifierTable;

    public LootModifierGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        modifiedTable = new VTextField(24);
        modifierTable = new SearchableComboBox<>();
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("lootmodifier/modified_table"), L10N.label("elementgui.lootmodifier.modified_table", new Object[0])));
        mainPanel.add(modifiedTable);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("lootmodifier/modifier_table"), L10N.label("elementgui.lootmodifier.modifier_table", new Object[0])));
        mainPanel.add(modifierTable);

        modifiedTable.setValidator(new TextFieldValidator(modifiedTable, L10N.t("elementgui.lootmodified.modifier_needs_provider", new Object[0])));
        modifiedTable.enableRealtimeValidation();
        page1group.addValidationElement(modifiedTable);

        pane1.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(pane1);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(modifierTable, (java.util.List<String>)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == ModElementType.LOOTTABLE;
        }).map(ModElement::getName).collect(Collectors.toList()));
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (modifierTable.getSelectedItem() == null)
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.lootmodifier.needs_modifier", new Object[0]));
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(LootModifier modifier) {
        modifiedTable.setText(modifier.modifiedTable);
        modifierTable.setSelectedItem(modifier.modifierTable);
    }

    public LootModifier getElementFromGUI() {
        LootModifier modifier = new LootModifier(this.modElement);
        modifier.modifiedTable = modifiedTable.getText();
        modifier.modifierTable = modifierTable.getSelectedItem();
        return modifier;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return new URI(MCreatorApplication.SERVER_DOMAIN + "/wiki/how-make-loot-table");
    }

}
