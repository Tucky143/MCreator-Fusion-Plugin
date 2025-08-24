package net.tucky143.fusion.parts;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.minecraft.boundingboxes.JBoundingBoxList;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.validators.TextureSelectionButtonValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.resources.Model;
import net.tucky143.fusion.elements.Blockstates;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class JBlockstateListEntry extends JSimpleListEntry<Blockstates.BlockstateListEntry> implements IValidable {
    private final Workspace workspace;
    private final ValidationGroup page1group = new ValidationGroup();
    private final TextureSelectionButton texture;
    private final TextureSelectionButton textureTop;
    private final TextureSelectionButton textureLeft;
    private final TextureSelectionButton textureFront;
    private final TextureSelectionButton textureRight;
    private final TextureSelectionButton textureBack;
    private final TextureSelectionButton particleTexture;
    private final Model normal;
    private final Model singleTexture;
    private final Model cross;
    private final Model crop;
    private final SearchableComboBox<Model> renderType;
    private Validator validator;
    private final JSpinner luminance;
    private final JBoundingBoxList boundingBoxList;
    private final int index;

    public JBlockstateListEntry(MCreator mcreator, IHelpContext gui, JPanel parent, List<JBlockstateListEntry> entryList, int index) {
        super(parent, entryList);
        this.index = index;
        this.workspace = mcreator.getWorkspace();
        this.line.setOpaque(false);
        this.normal = new Model.BuiltInModel("Normal");
        this.singleTexture = new Model.BuiltInModel("Single texture");
        this.cross = new Model.BuiltInModel("Cross model");
        this.crop = new Model.BuiltInModel("Crop model");
        this.renderType = new SearchableComboBox(new Model[]{this.normal, this.singleTexture, this.cross, this.crop});
        this.renderType.addActionListener((e) -> {
            this.updateTextureOptions();
        });
        ComponentUtils.deriveFont(this.renderType, 16.0F);
        this.renderType.setPreferredSize(new Dimension(320, 42));
        this.renderType.setRenderer(new ModelComboBoxRenderer());
        this.luminance = new JSpinner(new SpinnerNumberModel(0, 0, 15, 1));

        SearchableComboBox<Model> var10005 = this.renderType;
        Objects.requireNonNull(var10005);
        this.boundingBoxList = new JBoundingBoxList(mcreator, gui, var10005::getSelectedItem);
        this.renderType.addActionListener((e) -> {
                    Model selected = this.renderType.getSelectedItem();
                    if (selected != null) {
                        this.boundingBoxList.modelChanged();
                    }
        });

        JPanel destal = new JPanel(new GridLayout(3, 4));
        destal.setOpaque(false);
        this.texture = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK)).setFlipUV(true);
        this.textureTop = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK)).setFlipUV(true);
        this.textureLeft = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK));
        this.textureFront = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK));
        this.textureRight = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK));
        this.textureBack = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK));
        this.particleTexture = new TextureSelectionButton(new TypedTextureSelectorDialog(mcreator, TextureType.BLOCK), 32);
        this.particleTexture.setOpaque(false);
        this.texture.setOpaque(false);
        this.textureTop.setOpaque(false);
        this.textureLeft.setOpaque(false);
        this.textureFront.setOpaque(false);
        this.textureRight.setOpaque(false);
        this.textureBack.setOpaque(false);
        destal.add(new JLabel());
        destal.add(ComponentUtils.squareAndBorder(this.textureTop, L10N.t("elementgui.block.texture_place_top")));
        destal.add(new JLabel());
        destal.add(new JLabel());
        destal.add(ComponentUtils.squareAndBorder(this.textureLeft, new Color(126, 196, 255), L10N.t("elementgui.block.texture_place_left_overlay")));
        destal.add(ComponentUtils.squareAndBorder(this.textureFront, L10N.t("elementgui.block.texture_place_front_side")));
        destal.add(ComponentUtils.squareAndBorder(this.textureRight, L10N.t("elementgui.block.texture_place_right")));
        destal.add(ComponentUtils.squareAndBorder(this.textureBack, L10N.t("elementgui.block.texture_place_back")));
        this.textureLeft.addTextureSelectedListener((event) -> {
            if (!this.texture.hasTexture() && !this.textureTop.hasTexture() && !this.textureBack.hasTexture() && !this.textureFront.hasTexture() && !this.textureRight.hasTexture()) {
                this.texture.setTexture(this.textureLeft.getTextureHolder());
                this.textureTop.setTexture(this.textureLeft.getTextureHolder());
                this.textureBack.setTexture(this.textureLeft.getTextureHolder());
                this.textureFront.setTexture(this.textureLeft.getTextureHolder());
                this.textureRight.setTexture(this.textureLeft.getTextureHolder());
            }

        });
        destal.add(new JLabel());
        destal.add(ComponentUtils.squareAndBorder(this.texture, new Color(125, 255, 174), L10N.t("elementgui.block.texture_place_bottom_main")));
        destal.add(new JLabel());
        destal.add(new JLabel());
        JPanel sbbp22 = PanelUtils.totalCenterInPanel(destal);
        sbbp22.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.block.block_textures"), 0, 0, this.getFont().deriveFont(12.0F), Theme.current().getForegroundColor()));
        JPanel topnbot = new JPanel(new BorderLayout());
        topnbot.setOpaque(false);
        topnbot.add("Center", sbbp22);
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 0, 2));
        bottomPanel.setOpaque(false);
        bottomPanel.add(HelpUtils.wrapWithHelpButton(gui.withEntry("block/model"), L10N.label("elementgui.block.model")));
        bottomPanel.add(this.renderType);
        bottomPanel.add(HelpUtils.wrapWithHelpButton(gui.withEntry("block/particle_texture"), L10N.label("elementgui.block.particle_texture")));
        bottomPanel.add(this.particleTexture);
        bottomPanel.add(HelpUtils.wrapWithHelpButton(gui.withEntry("block/luminance"), L10N.label("elementgui.common.luminance")));
        bottomPanel.add(this.luminance);
        bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.blockstates.model"), 0, 0, this.getFont().deriveFont(12.0F), Theme.current().getForegroundColor()));
        this.boundingBoxList.setPreferredSize(new Dimension(200, 170));
        topnbot.add("East", PanelUtils.pullElementUp(PanelUtils.northAndCenterElement(bottomPanel, boundingBoxList)));
        topnbot.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.blockstates.blockstate") + " " + index, 0, 0, this.getFont().deriveFont(12.0F), Theme.current().getForegroundColor()));
        this.line.add(PanelUtils.totalCenterInPanel(topnbot));
    }
    private void updateTextureOptions() {
        this.textureTop.setFlipUV(false);
        this.textureTop.setVisible(false);
        this.textureLeft.setVisible(false);
        this.textureFront.setVisible(false);
        this.textureRight.setVisible(false);
        this.textureBack.setVisible(false);
        if (this.normal.equals(this.renderType.getSelectedItem())) {
            this.texture.setFlipUV(true);
            this.texture.setVisible(true);
            this.textureTop.setFlipUV(true);
            this.textureTop.setVisible(true);
            this.textureLeft.setVisible(true);
            this.textureFront.setVisible(true);
            this.textureRight.setVisible(true);
            this.textureBack.setVisible(true);
        }

        this.texture.setValidator(new TextureSelectionButtonValidator(this.texture));
        this.page1group.addValidationElement(this.texture);

    }


    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(this.renderType, ListUtils.merge(Arrays.asList(this.normal, this.singleTexture, this.cross, this.crop), Model.getModelsWithTextureMaps(workspace).stream().filter((el) -> {
            return el.getType() == Model.Type.JSON || el.getType() == Model.Type.OBJ;
        }).collect(Collectors.toList())));
    }

    protected void setEntryEnabled(boolean enabled) {
    }

    @Override public Validator.ValidationResult getValidationStatus() {
        Validator.ValidationResult validationResult = Validator.ValidationResult.PASSED;
        if (!page1group.validateIsErrorFree()) {
            Validator.ValidationResult result = new Validator.ValidationResult(Validator.ValidationResultType.ERROR, page1group.getValidationProblemMessages().get(0));
            return result;
        }
        return validationResult;
    }

    @Override public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override public Validator getValidator() {
        return validator;
    }

    public Blockstates.BlockstateListEntry getEntry() {
        Blockstates.BlockstateListEntry entry = new Blockstates.BlockstateListEntry();
        Model model = Objects.requireNonNull(this.renderType.getSelectedItem());
        entry.renderType = 0;
        if (model.getType() == Model.Type.JSON) {
            entry.renderType = 2;
        } else if (model.getType() == Model.Type.OBJ) {
            entry.renderType = 3;
        } else if (model.equals(this.singleTexture)) {
            entry.renderType = 4;
        } else if (model.equals(this.cross)) {
            entry.renderType = 1;
        } else if (model.equals(this.crop)) {
            entry.renderType = 5;
        }
        entry.customModelName = model.getReadableName();
        entry.particleTexture = this.particleTexture.getTextureHolder();
        entry.texture = this.texture.getTextureHolder();
        entry.textureTop = this.textureTop.getTextureHolder();
        entry.textureLeft = this.textureLeft.getTextureHolder();
        entry.textureFront = this.textureFront.getTextureHolder();
        entry.textureRight = this.textureRight.getTextureHolder();
        entry.textureBack = this.textureBack.getTextureHolder();
        entry.luminance = (Integer)this.luminance.getValue();
        entry.boundingBoxes = this.boundingBoxList.getEntries();
        return entry;
    }

    public void setEntry(Blockstates.BlockstateListEntry e) {
        Model model = e.getItemModel(workspace);
        if (model != null && model.getType() != null && model.getReadableName() != null) {
            this.renderType.setSelectedItem(model);
        }
        this.particleTexture.setTexture(e.particleTexture);
        this.texture.setTexture(e.texture);
        this.textureTop.setTexture(e.textureTop);
        this.textureLeft.setTexture(e.textureLeft);
        this.textureFront.setTexture(e.textureFront);
        this.textureRight.setTexture(e.textureRight);
        this.textureBack.setTexture(e.textureBack);
        this.luminance.setValue(e.luminance);
        this.boundingBoxList.setEntries(e.boundingBoxes);
    }
}
