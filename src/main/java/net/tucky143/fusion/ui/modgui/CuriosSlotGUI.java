package net.tucky143.fusion.ui.modgui;

import net.mcreator.element.parts.TextureHolder;
import net.mcreator.generator.GeneratorUtils;
import net.mcreator.io.FileIO;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemListField;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.validation.validators.TextureSelectionButtonValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.CuriosSlot;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class CuriosSlotGUI extends ModElementGUI<CuriosSlot> {

    private TextureSelectionButton texture;
    private final VTextField name;
    private final JSpinner amount;
    private MCItemListField items;
    private final ValidationGroup page1group = new ValidationGroup();

    public CuriosSlotGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        name = new VTextField(17);
        amount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        texture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.SCREEN));
        this.texture.setOpaque(false);
        items = new MCItemListField(mcreator, ElementUtil::loadBlocksAndItems);

        JComponent textureComponent = PanelUtils.totalCenterInPanel(ComponentUtils.squareAndBorder(HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_texture"), this.texture), L10N.t("elementgui.common.texture", new Object[0])));

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_name"), L10N.label("elementgui.curiosslot.slot_name", new Object[0])));
        mainPanel.add(name);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/custom_slot_amount"), L10N.label("elementgui.curiosslot.slot_amount", new Object[0])));
        mainPanel.add(amount);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/items"), L10N.label("elementgui.curiosslot.items", new Object[0])));
        mainPanel.add(items);

        name.setValidator(new TextFieldValidator(this.name, L10N.t("elementgui.curiosslot.needs_name", new Object[0])));
        name.enableRealtimeValidation();
        page1group.addValidationElement(name);
        texture.setValidator(new TextureSelectionButtonValidator(texture));
        page1group.addValidationElement(texture);

        if (!this.isEditingMode()) {
            String readableNameFromModElement = net.mcreator.util.StringUtils.machineToReadableName(this.modElement.getName());
            name.setText(readableNameFromModElement);
        }

        pane1.add("Center", PanelUtils.totalCenterInPanel(PanelUtils.northAndCenterElement(textureComponent, mainPanel)));
        addPage(pane1);
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("curios_api"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.curiosbauble.needs_api", new Object[0]));
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(CuriosSlot slot) {
        texture.setTexture(new TextureHolder(getModElement().getWorkspace(), StringUtils.removeEnd(slot.texture, ".png")));
        name.setText(slot.name);
        items.setListElements(slot.items);
        amount.setValue(slot.amount);
    }

    @Override
    protected void afterGeneratableElementStored() {
        if (texture.hasTexture()) {
           FileIO.copyFile(new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/screens/" + texture.getTextureHolder().name() + ".png"),
                   new File(GeneratorUtils.getResourceRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration()), "assets/curios/textures/slot/" + texture.getTextureHolder().name() + ".png"));
        }
    }

    public CuriosSlot getElementFromGUI() {
        CuriosSlot slot = new CuriosSlot(this.modElement);
        slot.texture = texture.getTextureHolder().name() + ".png";
        slot.name = name.getText();
        slot.amount = (int) amount.getValue();
        slot.items = items.getListElements();
        return slot;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

}
