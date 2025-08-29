package net.tucky143.fusion.ui.modgui.legacy;

import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JStringListField;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemListField;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.legacy.LegacyJeiInformation;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

public class LegacyJeiInformationGUI extends ModElementGUI<LegacyJeiInformation> {
    private final Object[] noParams = new Object[0];

    private MCItemListField items;
    private JStringListField information;

    public LegacyJeiInformationGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        items = new MCItemListField(this.mcreator, ElementUtil::loadBlocksAndItems, false, false);
        information = new JStringListField(this.mcreator, (Function)null);

        // Global
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.setOpaque(false);

        // Main
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        mainPanel.setOpaque(false);

        // Item Panel
        JPanel itemPanel = new JPanel(new GridLayout(0, 2,2, 0));
        itemPanel.setOpaque(false);
        itemPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/info/info_items"), L10N.label("elementGui.jeiInformation.info_items", noParams)));
        items.setPreferredSize(new Dimension(250, 34));
        itemPanel.add(items);
        mainPanel.add(itemPanel);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/info/items_info"), L10N.label("elementGui.jeiInformation.items_info", noParams)));
        information.setPreferredSize(new Dimension(400, 34));
        mainPanel.add(information);

        globalPanel.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(globalPanel);

    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("jei"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jei.needs_api", noParams));
        if (items.getListElements().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jeiInformation.needs_items", noParams));
        if (information.getTextList().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jeiInformation.needs_description", noParams));
        return new AggregatedValidationResult.PASS();
    }

    public void openInEditingMode(LegacyJeiInformation info) {
        items.setListElements(info.items);
        information.setTextList(info.information);
    }

    public LegacyJeiInformation getElementFromGUI() {
        LegacyJeiInformation info = new LegacyJeiInformation(this.modElement);
        info.items = items.getListElements();
        info.information = information.getTextList();
        return info;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
