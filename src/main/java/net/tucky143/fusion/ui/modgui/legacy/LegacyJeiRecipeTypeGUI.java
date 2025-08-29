package net.tucky143.fusion.ui.modgui.legacy;

import net.mcreator.element.ModElementType;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.minecraft.MCItemListField;
import net.mcreator.ui.minecraft.SingleModElementSelector;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VComboBox;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.ConditionalItemListFieldValidator;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.legacy.LegacyJeiRecipeType;
import net.tucky143.fusion.parts.WTextureComboBoxRenderer;
import net.tucky143.fusion.parts.legacy.JeiSlotList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LegacyJeiRecipeTypeGUI extends ModElementGUI<LegacyJeiRecipeType> {
    private final Object[] noParams = new Object[0];

    private final VTextField title;
    private MCItemHolder icon;
    private MCItemListField craftingtables;
    private JCheckBox enableCraftingtable;
    private final VComboBox<String> textureSelector;
    private JeiSlotList slotList;
    private JSpinner width;
    private JSpinner height;
    private JCheckBox enableClickArea;
    private SingleModElementSelector caGui;
    private JSpinner caX;
    private JSpinner caY;
    private JSpinner caWidth;
    private JSpinner caHeight;
    private final ValidationGroup page1group = new ValidationGroup();

    public LegacyJeiRecipeTypeGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        title = new VTextField(24);
        craftingtables = new MCItemListField(mcreator, ElementUtil::loadBlocksAndItemsAndTags, false, false);
        textureSelector= new SearchableComboBox((String[])mcreator.getFolderManager().getTexturesList(TextureType.SCREEN).stream().map(File::getName).toArray((x$0) -> {
            return new String[x$0];
        }));
        slotList = new JeiSlotList(this.mcreator, this);
        width = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        height = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        caGui = new SingleModElementSelector(mcreator, ModElementType.GUI);
        caGui.setDefaultText(L10N.t("elementgui.common.no_gui"));
        caX = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        caY = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        caWidth = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        caHeight = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        enableCraftingtable = L10N.checkbox("elementgui.common.enable", noParams);
        enableClickArea = L10N.checkbox("elementgui.common.enable", noParams);



        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        textureSelector.setRenderer(new WTextureComboBoxRenderer.TypeTextures(mcreator.getWorkspace(), TextureType.SCREEN));
        ComponentUtils.deriveFont(title, 16.0F);
        icon = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);
        enableCraftingtable.setOpaque(false);
        enableCraftingtable.addActionListener((e) -> {
            craftingtables.setEnabled(enableCraftingtable.isSelected());
        });
        enableClickArea.addActionListener(e -> {
            caEnable(enableClickArea.isSelected());
        });
        caGui.addEntrySelectedListener(e -> {
            if (!isEditingMode()) {
                String selected = caGui.getEntry();
                if (selected != null) {
                    ModElement element = mcreator.getWorkspace().getModElementByName(selected);
                }
            }
        });

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.setOpaque(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(8, 2, 0, 2));
        mainPanel.setOpaque(false);
        // Recipe type title
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/title"), L10N.label("elementGui.jeiRecipeType.title", noParams)));
        mainPanel.add(title);
        // Recipe type icon
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/icon"), L10N.label("elementGui.jeiRecipeType.icon", noParams)));
        mainPanel.add(icon);

        // Crafting tables used
        JPanel craftingPanel = new JPanel(new GridLayout(1, 2, 0, 2));
        craftingPanel.setOpaque(false);
        craftingPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/crafting_table"), L10N.label("elementGui.jeiRecipeType.crafting_table", noParams)));
        craftingPanel.add(enableCraftingtable);
        // Crafting tables
        mainPanel.add(craftingPanel);
        mainPanel.add(craftingtables);

        // JEI GUI texture selection
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/background"), L10N.label("elementGui.jeiRecipeType.background", noParams)));
        mainPanel.add(textureSelector);

        // JEI GUI texture width
        JPanel texWidthPanel = new JPanel(new GridLayout(1, 3, 0, 1));
        texWidthPanel.setOpaque(false);
        texWidthPanel.add(L10N.label("elementGui.jeiRecipeType.width", noParams));
        texWidthPanel.add(width);
        texWidthPanel.add(new JEmptyBox());
        mainPanel.add(texWidthPanel);

        // JEI GUI texture height
        JPanel texHeightPanel = new JPanel(new GridLayout(1, 2, 0, 1));
        texHeightPanel.setOpaque(false);
        texHeightPanel.add(L10N.label("elementGui.jeiRecipeType.height", noParams));
        texHeightPanel.add(height);
        mainPanel.add(texHeightPanel);

        // Click Area Check
        JPanel clickAreaPanel = new JPanel(new GridLayout(1, 3, 0, 1));
        clickAreaPanel.setOpaque(false);
        clickAreaPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/click_area"), L10N.label("elementGui.jeiRecipeType.clickArea", noParams)));
        clickAreaPanel.add(enableClickArea);
        // Click Area for GUI
        mainPanel.add(clickAreaPanel);
        mainPanel.add(caGui);

        // Click Area Position X
        JPanel clickAreaPanelPosX = new JPanel(new GridLayout(1, 3, 0, 1));
        clickAreaPanelPosX.setOpaque(false);
        clickAreaPanelPosX.add(L10N.label("elementGui.jeiRecipeType.clickAreaX", noParams));
        clickAreaPanelPosX.add(caX);
        clickAreaPanelPosX.add(new JEmptyBox());
        mainPanel.add(clickAreaPanelPosX);

        // Click Area Position Y
        JPanel clickAreaPanelPosY = new JPanel(new GridLayout(1, 2, 0, 1));
        clickAreaPanelPosY.setOpaque(false);
        clickAreaPanelPosY.add(L10N.label("elementGui.jeiRecipeType.clickAreaY", noParams));
        clickAreaPanelPosY.add(caY);
        mainPanel.add(clickAreaPanelPosY);

        // Click Area Dimension Width
        JPanel clickAreaPanelDimWidth = new JPanel(new GridLayout(1, 3, 0, 1));
        clickAreaPanelDimWidth.setOpaque(false);
        clickAreaPanelDimWidth.add(L10N.label("elementGui.jeiRecipeType.clickAreaWidth", noParams));
        clickAreaPanelDimWidth.add(caWidth);
        clickAreaPanelDimWidth.add(new JEmptyBox());
        mainPanel.add(clickAreaPanelDimWidth);

        // Click Area Dimension Height
        JPanel clickAreaPanelDimHeight = new JPanel(new GridLayout(1, 2, 0, 1));
        clickAreaPanelDimHeight.setOpaque(false);
        clickAreaPanelDimHeight.add(L10N.label("elementGui.jeiRecipeType.clickAreaHeight", noParams));
        clickAreaPanelDimHeight.add(caHeight);
        mainPanel.add(clickAreaPanelDimHeight);

        // JEI slots
        JComponent slotPanel = PanelUtils.northAndCenterElement(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_type/slots"), L10N.label("elementGui.jeiRecipeType.slots", noParams)), this.slotList);
        slotPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        globalPanel.add(PanelUtils.northAndCenterElement(PanelUtils.join(0, new Component[]{mainPanel}), slotPanel));

        this.title.setValidator(new TextFieldValidator(this.title, L10N.t("elementGui.jeiRecipeType.recipeType_needs_title", noParams)));
        this.title.enableRealtimeValidation();
        this.page1group.addValidationElement(title);
        this.icon.setValidator(new MCItemHolderValidator(this.icon));
        this.page1group.addValidationElement(icon);
        this.craftingtables.setValidator(new ConditionalItemListFieldValidator(this.craftingtables, L10N.t("elementGui.jeiRecipeType.crafting_empty"), () -> !this.enableCraftingtable.isSelected(), false));
        this.page1group.addValidationElement(craftingtables);

        if (!this.isEditingMode()) {
            title.setText(StringUtils.machineToReadableName(this.modElement.getName()));
            craftingtables.setEnabled(enableCraftingtable.isSelected());
            caGui.setEnabled(enableClickArea.isSelected());
            caX.setEnabled(enableClickArea.isSelected());
            caY.setEnabled(enableClickArea.isSelected());
            caWidth.setEnabled(enableClickArea.isSelected());
            caHeight.setEnabled(enableClickArea.isSelected());
        }

        addPage(L10N.t("elementgui.common.page_properties", noParams), globalPanel);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        slotList.reloadDataLists();
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("jei"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jei.needs_api", noParams));
        else if (textureSelector.getSelectedItem().equals("")) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jeiRecipeType.missing_texture", noParams));
        }
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    @Override
    protected void openInEditingMode(LegacyJeiRecipeType recipeType) {
        title.setText(recipeType.title);
        icon.setBlock(recipeType.icon);
        enableCraftingtable.setSelected(recipeType.enableCraftingtable);
        textureSelector.setSelectedItem(recipeType.textureSelector);
        slotList.setEntries(recipeType.slotList);
        width.setValue(recipeType.width);
        height.setValue(recipeType.height);

        if (recipeType.craftingtable != null) {
            craftingtables.setListElements(List.of(recipeType.craftingtable));
            recipeType.craftingtable = null;
        } else craftingtables.setListElements(recipeType.craftingtables);

        craftingtables.setEnabled(enableCraftingtable.isSelected());
        enableClickArea.setSelected(recipeType.clickableArea);
        caGui.setEntry(recipeType.caGui);
        caX.setValue(recipeType.caX);
        caY.setValue(recipeType.caY);
        caWidth.setValue(recipeType.caWidth);
        caHeight.setValue(recipeType.caHeight);
        caEnable(enableClickArea.isSelected());
    }

    public LegacyJeiRecipeType getElementFromGUI() {
        LegacyJeiRecipeType recipeType = new LegacyJeiRecipeType(this.modElement);
        recipeType.textureSelector = textureSelector.getSelectedItem();
        recipeType.icon = icon.getBlock();
        recipeType.craftingtables = craftingtables.getListElements();
        recipeType.enableCraftingtable = enableCraftingtable.isSelected();
        recipeType.title = title.getText();
        recipeType.slotList = slotList.getEntries();
        recipeType.width = (int) width.getValue();
        recipeType.height = (int) height.getValue();
        recipeType.clickableArea = enableClickArea.isSelected();
        recipeType.caGui = caGui.getEntry();
        recipeType.caX = (int) caX.getValue();
        recipeType.caY = (int) caY.getValue();
        recipeType.caWidth = (int) caWidth.getValue();
        recipeType.caHeight = (int) caHeight.getValue();
        return recipeType;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

    public void caEnable(boolean enabled) {
        caGui.setEnabled(enabled);
        caX.setEnabled(enabled);
        caY.setEnabled(enabled);
        caWidth.setEnabled(enabled);
        caHeight.setEnabled(enabled);
    }
}

