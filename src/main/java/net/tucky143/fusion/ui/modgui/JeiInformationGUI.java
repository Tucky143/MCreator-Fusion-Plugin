package net.tucky143.fusion.ui.modgui;

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
import net.tucky143.fusion.elements.JeiInformation;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

public class JeiInformationGUI extends ModElementGUI<JeiInformation> {
    private MCItemListField items;
    private JStringListField information;

    public JeiInformationGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        items = new MCItemListField(this.mcreator, ElementUtil::loadBlocksAndItems, false, false);
        information = new JStringListField(this.mcreator, (Function)null);

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/info_items"), L10N.label("elementgui.jeiinformation.info_items", new Object[0])));
        mainPanel.add(items);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/items_info"), L10N.label("elementgui.jeiinformation.items_info", new Object[0])));
        mainPanel.add(information);

        pane1.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(pane1);

    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("jei"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jei.needs_api", new Object[0]));
        if (items.getListElements().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeiinformation.needs_items", new Object[0]));
        if (information.getTextList().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeiinformation.needs_description", new Object[0]));
        return new AggregatedValidationResult.PASS();
    }

    public void openInEditingMode(JeiInformation info) {
        items.setListElements(info.items);
        information.setTextList(info.information);
    }

    public JeiInformation getElementFromGUI() {
        JeiInformation info = new JeiInformation(this.modElement);
        info.items = items.getListElements();
        info.information = information.getTextList();
        return info;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

}
