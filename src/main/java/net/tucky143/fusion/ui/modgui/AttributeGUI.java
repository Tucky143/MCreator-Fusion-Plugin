package net.tucky143.fusion.ui.modgui;

import net.tucky143.fusion.elements.Attribute;
import net.tucky143.fusion.parts.JEntries;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.elements.ModElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI for creating new {@link Attribute}
 */
public class AttributeGUI extends ModElementGUI<Attribute> {
    private final VTextField description = new VTextField(20);
    private final JSpinner defaultValue = new JSpinner(new SpinnerNumberModel(0.0, -2000000000.0, 2000000000.0, 1.0));
    private final JSpinner minValue = new JSpinner(new SpinnerNumberModel(0.0, -2000000000.0, 2000000000.0, 1.0));
    private final JSpinner maxValue = new JSpinner(new SpinnerNumberModel(0.0, -2000000000.0, 2000000000.0, 1.0));
    private final JEntries entities = new JEntries(this.mcreator, this);
    private final JCheckBox isPersistent = L10N.checkbox("elementgui.common.enable");
    private final ValidationGroup page1group = new ValidationGroup();

    @SuppressWarnings("unused")
    public AttributeGUI(MCreator mcreator, ModElement element, boolean editingMode) {
        super(mcreator, element, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    /**
     * Initalizes GUI
     */
    protected void initGUI() {

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel pane2 = new JPanel(new BorderLayout());
        pane2.setOpaque(false);
        JPanel selp = new JPanel(new GridLayout(5, 2, 5, 3));
        selp.setOpaque(false);
        this.isPersistent.setOpaque(false);
        selp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((Color) UIManager.get("MCreatorLAF.BRIGHT_COLOR"), 1),
                L10N.t("elementgui.attribute.border"),
                0, 0, this.getFont().deriveFont(12.0F), (Color) UIManager.get("MCreatorLAF.BRIGHT_COLOR")));

        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("attribute/description"), L10N.label("elementgui.attribute.description")));
        selp.add(this.description);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("attribute/defaultValue"), L10N.label("elementgui.attribute.defaultValue")));
        selp.add(this.defaultValue);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("attribute/minValue"), L10N.label("elementgui.attribute.minValue")));
        selp.add(this.minValue);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("attribute/maxValue"), L10N.label("elementgui.attribute.maxValue")));
        selp.add(this.maxValue);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("attribute/isPersistent"),
                L10N.label("elementgui.attribute.isPersistent")));
        selp.add(this.isPersistent);
        pane1.add(PanelUtils.totalCenterInPanel(selp));
        this.description.setValidator(new TextFieldValidator(this.description, L10N.t("elementgui.attribute.needs_description")));
        this.description.enableRealtimeValidation();
        this.page1group.addValidationElement(this.description);

        pane2.add(this.entities);

        this.addPage(L10N.t("elementgui.attribute.page.new"), pane1);
        this.addPage(L10N.t("elementgui.attribute.page.entities"), pane2);

        if (!this.isEditingMode()) {
            String readableNameFromModElement = StringUtils.machineToReadableName(this.modElement.getName());
            this.description.setText(readableNameFromModElement);
        }
    }

    /**
     * Gets values from {@link Attribute} and puts them in correct fields in GUI
     *
     * @param attribute Attribute which's GUI is being open
     */
    public void openInEditingMode(Attribute attribute) {
        this.description.setText(attribute.description);
        this.defaultValue.setValue(attribute.defaultValue);
        this.minValue.setValue(attribute.minValue);
        this.maxValue.setValue(attribute.maxValue);
        this.entities.setEntities(attribute.entities);
        this.isPersistent.setSelected(attribute.isPersistent);
    }

    /**
     * Gets values from GUI and returns as a new {@link Attribute}
     *
     * @return New Attribute
     */
    public Attribute getElementFromGUI() {
        Attribute attribute = new Attribute(this.modElement);

        attribute.description = this.description.getText();
        attribute.defaultValue = (Double) this.defaultValue.getValue();
        attribute.minValue = (Double) this.minValue.getValue();
        attribute.maxValue = (Double) this.maxValue.getValue();
        attribute.entities = this.entities.getEntities();
        attribute.isPersistent = this.isPersistent.isSelected();

        return attribute;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

    protected AggregatedValidationResult validatePage(int page) {
        List<String> messages = new ArrayList<String>();
        if ((double) this.minValue.getValue() > (double) this.defaultValue.getValue())
            messages.add(L10N.t("elementgui.attribute.warning.minDefault"));
        if ((double) this.maxValue.getValue() < (double) this.defaultValue.getValue())
            messages.add(L10N.t("elementgui.attribute.warning.maxDefault"));
        if ((double) this.maxValue.getValue() < (double) this.minValue.getValue())
            messages.add(L10N.t("elementgui.attribute.warning.minMax"));
        if (messages.size() == 1)
            return new AggregatedValidationResult.FAIL(messages.get(0));
        return new AggregatedValidationResult(this.page1group);
    }
}
