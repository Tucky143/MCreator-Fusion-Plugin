package net.tucky143.fusion.ui.modgui;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.Toast;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class ToastGUI extends ModElementGUI<Toast> {

    private final VTextField title = new VTextField(12);
    private final VTextField description = new VTextField(30);
    private final JSpinner durationTime = new JSpinner(new SpinnerNumberModel(5000, 1000, 600000, 500));
    private final JComboBox<String> icon = new JComboBox<>();
    private final JComboBox<String> background = new JComboBox<>();

    private final ValidationGroup page1group = new ValidationGroup();

    public ToastGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        ComponentUtils.deriveFont(title, 16.0F);
        ComponentUtils.deriveFont(description, 16.0F);

        icon.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        icon.setRenderer(new WTextureComboBoxRenderer.TypeTextures(mcreator.getWorkspace(), TextureType.SCREEN));
        background.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        background.setRenderer(new WTextureComboBoxRenderer.TypeTextures(mcreator.getWorkspace(), TextureType.SCREEN));

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("toast/title"), L10N.label("elementgui.toast.title")));
        mainPanel.add(title);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("toast/description"), L10N.label("elementgui.toast.description")));
        mainPanel.add(description);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("toast/duration_time"), L10N.label("elementgui.toast.duration_time")));
        mainPanel.add(durationTime);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("toast/icon"), L10N.label("elementgui.toast.icon")));
        mainPanel.add(icon);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("toast/background"), L10N.label("elementgui.toast.background")));
        mainPanel.add(background);

        page1group.addValidationElement(title);
        title.setValidator(new TextFieldValidator(title, L10N.t("elementgui.toast.cant_be_empty", title)));
        title.enableRealtimeValidation();

        addPage(L10N.t("elementgui.common.page_mainPanel"), PanelUtils.totalCenterInPanel(mainPanel));
    }

    public void reloadDataLists() {
        ComboBoxUtil.updateComboBoxContents(icon, ListUtils.merge(Collections.singleton("None"),
                mcreator.getFolderManager().getTexturesList(TextureType.SCREEN).stream()
                        .map(File::getName).toList()), "None");
        ComboBoxUtil.updateComboBoxContents(background, ListUtils.merge(Collections.singleton("Default"),
                mcreator.getFolderManager().getTexturesList(TextureType.SCREEN).stream()
                        .map(File::getName).toList()), "Default");
    }

    protected AggregatedValidationResult validatePage(int i) {
        return new AggregatedValidationResult(page1group);
    }

    @Override
    protected void openInEditingMode(Toast toast) {
        title.setText(toast.title);
        description.setText(toast.description);
        durationTime.setValue(toast.durationTime);
        icon.setSelectedItem(toast.icon);
        background.setSelectedItem(toast.background);
        if (toast.background == null)
            background.setSelectedItem("Default");
    }

    @Override
    public Toast getElementFromGUI() {
        Toast toast = new Toast(modElement);
        toast.title = title.getText();
        toast.description = description.getText();
        toast.durationTime = (int) durationTime.getValue();
        toast.icon = (String) icon.getSelectedItem();
        toast.background = (String) background.getSelectedItem();

        return toast;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
