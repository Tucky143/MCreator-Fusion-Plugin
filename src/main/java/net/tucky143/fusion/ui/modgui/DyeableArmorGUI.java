//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.ui.modgui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import net.mcreator.blockly.data.Dependency;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.minecraft.JavaModels;
import net.mcreator.minecraft.MinecraftImageGenerator.Preview;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JColor;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.JStringListField;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.minecraft.MCItemListField;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.minecraft.TabListField;
import net.mcreator.ui.minecraft.TextureComboBox;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.LogicProcedureSelector;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.procedure.StringListProcedureSelector;
import net.mcreator.ui.procedure.AbstractProcedureSelector.Side;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.Validator.ValidationResult;
import net.mcreator.ui.validation.Validator.ValidationResultType;
import net.mcreator.ui.validation.component.VComboBox;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.ConditionalTextFieldValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.util.StringUtils;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Model.Type;
import net.tucky143.fusion.elements.DyeableArmor;
import net.tucky143.fusion.ui.components.OverlayTextureComboBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jetbrains.annotations.Nullable;

public class DyeableArmorGUI extends ModElementGUI<DyeableArmor> {
    private static final Logger LOG = LogManager.getLogger("Dyeable Armor UI");
    private static final int ARMOR_TEXTURE_SIZE_FACTOR = 5;
    private final TextureSelectionButton textureHelmet;
    private final TextureSelectionButton textureBody;
    private final TextureSelectionButton textureLeggings;
    private final TextureSelectionButton textureBoots;
    private final TextureSelectionButton overlayTextureHelmet;
    private final TextureSelectionButton overlayTextureBody;
    private final TextureSelectionButton overlayTextureLeggings;
    private final TextureSelectionButton overlayTextureBoots;
    private final VTextField helmetName;
    private final VTextField bodyName;
    private final VTextField leggingsName;
    private final VTextField bootsName;
    private static final Model defaultModel = new Model.BuiltInModel("Default");
    private final Model normal;
    private final Model tool;
    private final VComboBox<Model> helmetModel;
    private final VComboBox<Model> bodyModel;
    private final VComboBox<Model> leggingsModel;
    private final VComboBox<Model> bootsModel;
    private StringListProcedureSelector helmetSpecialInformation;
    private StringListProcedureSelector bodySpecialInformation;
    private StringListProcedureSelector leggingsSpecialInformation;
    private StringListProcedureSelector bootsSpecialInformation;
    private ActionListener helmetModelListener;
    private ActionListener bodyModelListener;
    private ActionListener leggingsModelListener;
    private ActionListener bootsModelListener;
    private final VComboBox<String> helmetModelPart;
    private final VComboBox<String> bodyModelPart;
    private final VComboBox<String> armsModelPartL;
    private final VComboBox<String> armsModelPartR;
    private final VComboBox<String> leggingsModelPartL;
    private final VComboBox<String> leggingsModelPartR;
    private final VComboBox<String> bootsModelPartL;
    private final VComboBox<String> bootsModelPartR;
    private final TextureComboBox armorTextureFile;
    private final OverlayTextureComboBox armorOverlayTextureFile;
    private final JCheckBox enableHelmet;
    private final JCheckBox enableBody;
    private final JCheckBox enableLeggings;
    private final JCheckBox enableBoots;
    private final TextureComboBox helmetModelTexture;
    private final TextureComboBox bodyModelTexture;
    private final TextureComboBox leggingsModelTexture;
    private final TextureComboBox bootsModelTexture;
    private final SearchableComboBox<Model> helmetItemRenderType;
    private final SearchableComboBox<Model> bodyItemRenderType;
    private final SearchableComboBox<Model> leggingsItemRenderType;
    private final SearchableComboBox<Model> bootsItemRenderType;
    private final JCheckBox helmetImmuneToFire;
    private final JCheckBox bodyImmuneToFire;
    private final JCheckBox leggingsImmuneToFire;
    private final JCheckBox bootsImmuneToFire;
    private LogicProcedureSelector helmetGlowCondition;
    private LogicProcedureSelector bodyGlowCondition;
    private LogicProcedureSelector leggingsGlowCondition;
    private LogicProcedureSelector bootsGlowCondition;
    private LogicProcedureSelector helmetPiglinNeutral;
    private LogicProcedureSelector bodyPiglinNeutral;
    private LogicProcedureSelector leggingsPiglinNeutral;
    private LogicProcedureSelector bootsPiglinNeutral;
    private final JLabel col1;
    private final JLabel col2;
    private final SoundSelector equipSound;
    private final JSpinner maxDamage;
    private final JSpinner damageValueBoots;
    private final JSpinner damageValueLeggings;
    private final JSpinner damageValueBody;
    private final JSpinner damageValueHelmet;
    private final JSpinner enchantability;
    private final JSpinner toughness;
    private final JSpinner knockbackResistance;
    private ProcedureSelector onHelmetTick;
    private ProcedureSelector onBodyTick;
    private ProcedureSelector onLeggingsTick;
    private ProcedureSelector onBootsTick;
    private final TabListField creativeTabs;
    private MCItemListField repairItems;
    private final JCheckBox isDyeableHelmet;
    private final JColor defaultColorHelmet;
    private final JCheckBox canWaterRemoveColorHelmet;
    private final JCheckBox canLavaRemoveColorHelmet;
    private final JCheckBox canPowderSnowRemoveColorHelmet;
    private final JCheckBox isDyeableBody;
    private final JColor defaultColorBody;
    private final JCheckBox canWaterRemoveColorBody;
    private final JCheckBox canLavaRemoveColorBody;
    private final JCheckBox canPowderSnowRemoveColorBody;
    private final JCheckBox isDyeableLeggings;
    private final JColor defaultColorLeggings;
    private final JCheckBox canWaterRemoveColorLeggings;
    private final JCheckBox canLavaRemoveColorLeggings;
    private final JCheckBox canPowderSnowRemoveColorLeggings;
    private final JCheckBox isDyeableBoots;
    private final JColor defaultColorBoots;
    private final JCheckBox canWaterRemoveColorBoots;
    private final JCheckBox canLavaRemoveColorBoots;
    private final JCheckBox canPowderSnowRemoveColorBoots;
    private final ValidationGroup groupHelmetPage;
    private final ValidationGroup groupBodyPage;
    private final ValidationGroup groupLeggingsPage;
    private final ValidationGroup groupBootsPage;
    private final ValidationGroup groupPropertiesPage;

    public DyeableArmorGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.textureHelmet = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.textureBody = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.textureLeggings = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.textureBoots = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.overlayTextureHelmet = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.overlayTextureBody = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.overlayTextureLeggings = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.overlayTextureBoots = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.helmetName = new VTextField();
        this.bodyName = new VTextField();
        this.leggingsName = new VTextField();
        this.bootsName = new VTextField();
        this.normal = new Model.BuiltInModel("Normal");
        this.tool = new Model.BuiltInModel("Tool");
        this.helmetModel = new SearchableComboBox(new Model[]{defaultModel});
        this.bodyModel = new SearchableComboBox(new Model[]{defaultModel});
        this.leggingsModel = new SearchableComboBox(new Model[]{defaultModel});
        this.bootsModel = new SearchableComboBox(new Model[]{defaultModel});
        this.helmetModelListener = null;
        this.bodyModelListener = null;
        this.leggingsModelListener = null;
        this.bootsModelListener = null;
        this.helmetModelPart = new SearchableComboBox();
        this.bodyModelPart = new SearchableComboBox();
        this.armsModelPartL = new SearchableComboBox();
        this.armsModelPartR = new SearchableComboBox();
        this.leggingsModelPartL = new SearchableComboBox();
        this.leggingsModelPartR = new SearchableComboBox();
        this.bootsModelPartL = new SearchableComboBox();
        this.bootsModelPartR = new SearchableComboBox();
        this.armorTextureFile = (new TextureComboBox(this.mcreator, TextureType.ARMOR, true)).requireValue("elementgui.armor.armor_needs_texture");
        this.armorOverlayTextureFile = new OverlayTextureComboBox(this.mcreator, TextureType.ARMOR);
        this.enableHelmet = L10N.checkbox("elementgui.armor.armor_helmet", new Object[0]);
        this.enableBody = L10N.checkbox("elementgui.armor.armor_chestplate", new Object[0]);
        this.enableLeggings = L10N.checkbox("elementgui.armor.armor_leggings", new Object[0]);
        this.enableBoots = L10N.checkbox("elementgui.armor.armor_boots", new Object[0]);
        this.helmetModelTexture = new TextureComboBox(this.mcreator, TextureType.ENTITY, true, "From armor");
        this.bodyModelTexture = new TextureComboBox(this.mcreator, TextureType.ENTITY, true, "From armor");
        this.leggingsModelTexture = new TextureComboBox(this.mcreator, TextureType.ENTITY, true, "From armor");
        this.bootsModelTexture = new TextureComboBox(this.mcreator, TextureType.ENTITY, true, "From armor");
        this.helmetItemRenderType = new SearchableComboBox(new Model[]{this.normal, this.tool});
        this.bodyItemRenderType = new SearchableComboBox(new Model[]{this.normal, this.tool});
        this.leggingsItemRenderType = new SearchableComboBox(new Model[]{this.normal, this.tool});
        this.bootsItemRenderType = new SearchableComboBox(new Model[]{this.normal, this.tool});
        this.helmetImmuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.bodyImmuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.leggingsImmuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.bootsImmuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.col1 = new JLabel();
        this.col2 = new JLabel();
        this.equipSound = new SoundSelector(this.mcreator);
        this.maxDamage = new JSpinner(new SpinnerNumberModel(15, 0, 1024, 1));
        this.damageValueBoots = new JSpinner(new SpinnerNumberModel(2, 0, 1024, 1));
        this.damageValueLeggings = new JSpinner(new SpinnerNumberModel(5, 0, 1024, 1));
        this.damageValueBody = new JSpinner(new SpinnerNumberModel(6, 0, 1024, 1));
        this.damageValueHelmet = new JSpinner(new SpinnerNumberModel(2, 0, 1024, 1));
        this.enchantability = new JSpinner(new SpinnerNumberModel(9, 0, 128000, 1));
        this.toughness = new JSpinner(new SpinnerNumberModel((double)0.0F, (double)0.0F, (double)1024.0F, 0.1));
        this.knockbackResistance = new JSpinner(new SpinnerNumberModel((double)0.0F, (double)0.0F, (double)5.0F, 0.1));
        this.creativeTabs = new TabListField(this.mcreator);
        this.isDyeableHelmet = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.defaultColorHelmet = new JColor(this.mcreator, false, false);
        this.canWaterRemoveColorHelmet = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canLavaRemoveColorHelmet = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canPowderSnowRemoveColorHelmet = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.isDyeableBody = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.defaultColorBody = new JColor(this.mcreator, false, false);
        this.canWaterRemoveColorBody = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canLavaRemoveColorBody = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canPowderSnowRemoveColorBody = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.isDyeableLeggings = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.defaultColorLeggings = new JColor(this.mcreator, false, false);
        this.canWaterRemoveColorLeggings = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canLavaRemoveColorLeggings = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canPowderSnowRemoveColorLeggings = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.isDyeableBoots = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.defaultColorBoots = new JColor(this.mcreator, false, false);
        this.canWaterRemoveColorBoots = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canLavaRemoveColorBoots = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canPowderSnowRemoveColorBoots = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.groupHelmetPage = new ValidationGroup();
        this.groupBodyPage = new ValidationGroup();
        this.groupLeggingsPage = new ValidationGroup();
        this.groupBootsPage = new ValidationGroup();
        this.groupPropertiesPage = new ValidationGroup();
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        this.helmetGlowCondition = new LogicProcedureSelector(this.withEntry("item/glowing_effect"), this.mcreator, L10N.t("elementgui.armor.glowing_effect", new Object[0]), Side.CLIENT, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.bodyGlowCondition = new LogicProcedureSelector(this.withEntry("item/glowing_effect"), this.mcreator, L10N.t("elementgui.armor.glowing_effect", new Object[0]), Side.CLIENT, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.leggingsGlowCondition = new LogicProcedureSelector(this.withEntry("item/glowing_effect"), this.mcreator, L10N.t("elementgui.armor.glowing_effect", new Object[0]), Side.CLIENT, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.bootsGlowCondition = new LogicProcedureSelector(this.withEntry("item/glowing_effect"), this.mcreator, L10N.t("elementgui.armor.glowing_effect", new Object[0]), Side.CLIENT, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.helmetSpecialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), this.mcreator, L10N.t("elementgui.common.special_information", new Object[0]), Side.CLIENT, new JStringListField(this.mcreator, (Function)null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));
        this.bodySpecialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), this.mcreator, L10N.t("elementgui.common.special_information", new Object[0]), Side.CLIENT, new JStringListField(this.mcreator, (Function)null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));
        this.leggingsSpecialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), this.mcreator, L10N.t("elementgui.common.special_information", new Object[0]), Side.CLIENT, new JStringListField(this.mcreator, (Function)null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));
        this.bootsSpecialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), this.mcreator, L10N.t("elementgui.common.special_information", new Object[0]), Side.CLIENT, new JStringListField(this.mcreator, (Function)null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));
        this.helmetPiglinNeutral = new LogicProcedureSelector(this.withEntry("armor/piglin_neutral"), this.mcreator, L10N.t("elementgui.armor.piglin_neutral", new Object[0]), Side.BOTH, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.bodyPiglinNeutral = new LogicProcedureSelector(this.withEntry("armor/piglin_neutral"), this.mcreator, L10N.t("elementgui.armor.piglin_neutral", new Object[0]), Side.BOTH, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.leggingsPiglinNeutral = new LogicProcedureSelector(this.withEntry("armor/piglin_neutral"), this.mcreator, L10N.t("elementgui.armor.piglin_neutral", new Object[0]), Side.BOTH, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.bootsPiglinNeutral = new LogicProcedureSelector(this.withEntry("armor/piglin_neutral"), this.mcreator, L10N.t("elementgui.armor.piglin_neutral", new Object[0]), Side.BOTH, L10N.checkbox("elementgui.common.enable", new Object[0]), 0, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onHelmetTick = new ProcedureSelector(this.withEntry("armor/helmet_tick"), this.mcreator, L10N.t("elementgui.armor.helmet_tick_event", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onBodyTick = new ProcedureSelector(this.withEntry("armor/body_tick"), this.mcreator, L10N.t("elementgui.armor.chestplate_tick_event", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onLeggingsTick = new ProcedureSelector(this.withEntry("armor/leggings_tick"), this.mcreator, L10N.t("elementgui.armor.leggings_tick_event", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onBootsTick = new ProcedureSelector(this.withEntry("armor/boots_tick"), this.mcreator, L10N.t("elementgui.armor.boots_tick_event", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        ComponentUtils.deriveFont(this.helmetName, 16.0F);
        ComponentUtils.deriveFont(this.bodyName, 16.0F);
        ComponentUtils.deriveFont(this.leggingsName, 16.0F);
        ComponentUtils.deriveFont(this.bootsName, 16.0F);
        ComponentUtils.deriveFont(this.helmetModel, 16.0F);
        ComponentUtils.deriveFont(this.helmetModelPart, 16.0F);
        ComponentUtils.deriveFont(this.bodyModel, 16.0F);
        ComponentUtils.deriveFont(this.bodyModelPart, 16.0F);
        ComponentUtils.deriveFont(this.armsModelPartL, 16.0F);
        ComponentUtils.deriveFont(this.armsModelPartR, 16.0F);
        ComponentUtils.deriveFont(this.leggingsModel, 16.0F);
        ComponentUtils.deriveFont(this.leggingsModelPartL, 16.0F);
        ComponentUtils.deriveFont(this.leggingsModelPartR, 16.0F);
        ComponentUtils.deriveFont(this.bootsModel, 16.0F);
        ComponentUtils.deriveFont(this.bootsModelPartL, 16.0F);
        ComponentUtils.deriveFont(this.bootsModelPartR, 16.0F);
        ComponentUtils.deriveFont(this.helmetModelTexture, 16.0F);
        ComponentUtils.deriveFont(this.bodyModelTexture, 16.0F);
        ComponentUtils.deriveFont(this.leggingsModelTexture, 16.0F);
        ComponentUtils.deriveFont(this.bootsModelTexture, 16.0F);
        ComponentUtils.deriveFont(this.armorTextureFile, 16.0F);
        ComponentUtils.deriveFont(this.armorOverlayTextureFile, 16.0F);
        ComponentUtils.deriveFont(this.helmetItemRenderType, 16.0F);
        ComponentUtils.deriveFont(this.bodyItemRenderType, 16.0F);
        ComponentUtils.deriveFont(this.leggingsItemRenderType, 16.0F);
        ComponentUtils.deriveFont(this.bootsItemRenderType, 16.0F);
        this.helmetModel.setRenderer(new ModelComboBoxRenderer());
        this.bodyModel.setRenderer(new ModelComboBoxRenderer());
        this.leggingsModel.setRenderer(new ModelComboBoxRenderer());
        this.bootsModel.setRenderer(new ModelComboBoxRenderer());
        this.helmetItemRenderType.setRenderer(new ModelComboBoxRenderer());
        this.bodyItemRenderType.setRenderer(new ModelComboBoxRenderer());
        this.leggingsItemRenderType.setRenderer(new ModelComboBoxRenderer());
        this.bootsItemRenderType.setRenderer(new ModelComboBoxRenderer());
        this.armorTextureFile.setAddPNGExtension(false);
        this.armorOverlayTextureFile.setAddPNGExtension(false);
        this.helmetName.setPreferredSize(new Dimension(350, 36));
        this.bodyName.setPreferredSize(new Dimension(350, 36));
        this.leggingsName.setPreferredSize(new Dimension(350, 36));
        this.bootsName.setPreferredSize(new Dimension(350, 36));
        this.repairItems = new MCItemListField(this.mcreator, ElementUtil::loadBlocksAndItemsAndTags, false, true);
        JPanel helmetPage = new JPanel(new BorderLayout());
        JPanel helmetTop = new JPanel(new BorderLayout());
        JPanel helmetBottom = new JPanel(new GridLayout(10, 2, 2, 2));
        JPanel helmetTexturePanel = new JPanel();
        JPanel helmetConditionPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        helmetPage.setOpaque(false);
        helmetTop.setOpaque(false);
        helmetBottom.setOpaque(false);
        helmetTexturePanel.setOpaque(false);
        helmetConditionPanel.setOpaque(false);
        this.isDyeableHelmet.setOpaque(false);
        this.isDyeableHelmet.setSelected(true);
        this.isDyeableHelmet.addActionListener((e) -> this.updateHelmetDye());
        this.canWaterRemoveColorHelmet.setOpaque(false);
        this.canLavaRemoveColorHelmet.setOpaque(false);
        this.canPowderSnowRemoveColorHelmet.setOpaque(false);
        this.defaultColorHelmet.setEnabled(this.isDyeableHelmet.isSelected());
        this.canWaterRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected());
        this.canLavaRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected());
        this.canPowderSnowRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected());
        this.enableHelmet.setOpaque(false);
        this.enableHelmet.setSelected(true);
        this.enableHelmet.addActionListener((e) -> this.updateHelmetPage());
        this.helmetImmuneToFire.setOpaque(false);
        helmetTexturePanel.add(ComponentUtils.squareAndBorder(this.textureHelmet, L10N.t("elementgui.item.texture", new Object[0])));
        helmetTexturePanel.add(ComponentUtils.squareAndBorder(this.overlayTextureHelmet, L10N.t("elementgui.dyeable.common.overlay_texture", new Object[0])));
        helmetTop.add(helmetTexturePanel, "North");
        helmetTop.add(this.enableHelmet, "Center");
        helmetBottom.add(L10N.label("elementgui.armor.helmet_name", new Object[0]));
        helmetBottom.add(this.helmetName);
        helmetBottom.add(L10N.label("elementgui.armor.supported_java", new Object[0]));
        helmetBottom.add(PanelUtils.gridElements(1, 2, 2, 2, new Component[]{this.helmetModel, this.helmetModelPart}));
        helmetBottom.add(L10N.label("elementgui.armor.texture", new Object[0]));
        helmetBottom.add(this.helmetModelTexture);
        helmetBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])));
        helmetBottom.add(this.helmetItemRenderType);
        helmetBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/immune_to_fire"), L10N.label("elementgui.item.is_immune_to_fire", new Object[0])));
        helmetBottom.add(this.helmetImmuneToFire);
        helmetBottom.add(L10N.label("elementgui.dyeable.common.is_dyeable", new Object[0]));
        helmetBottom.add(this.isDyeableHelmet);
        helmetBottom.add(L10N.label("elementgui.dyeable.common.default_color", new Object[0]));
        helmetBottom.add(this.defaultColorHelmet);
        helmetBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_water"), L10N.label("elementgui.dyeable.common.can_water_remove_color", new Object[0])));
        helmetBottom.add(this.canWaterRemoveColorHelmet);
        helmetBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_lava"), L10N.label("elementgui.dyeable.common.can_lava_remove_color", new Object[0])));
        helmetBottom.add(this.canLavaRemoveColorHelmet);
        helmetBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_powder_snow"), L10N.label("elementgui.dyeable.common.can_powder_snow_remove_color", new Object[0])));
        helmetBottom.add(this.canPowderSnowRemoveColorHelmet);
        helmetConditionPanel.add(this.helmetGlowCondition);
        helmetConditionPanel.add(this.helmetSpecialInformation);
        helmetConditionPanel.add(this.helmetPiglinNeutral);
        helmetPage.add(helmetTop, "North");
        helmetPage.add(helmetBottom, "Center");
        helmetPage.add(helmetConditionPanel, "South");
        this.helmetModelListener = (e) -> {
            Model model = (Model)this.helmetModel.getSelectedItem();
            if (model != null && model != defaultModel) {
                this.helmetModelPart.removeAllItems();

                try {
                    ComboBoxUtil.updateComboBoxContents(this.helmetModelPart, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            } else {
                this.helmetModelPart.removeAllItems();
                this.helmetModelPart.addItem("Helmet");
            }

        };
        this.helmetModelListener.actionPerformed(new ActionEvent("", 0, ""));
        this.helmetName.setValidator(new ConditionalTextFieldValidator(this.helmetName, L10N.t("elementgui.armor.helmet_needs_name", new Object[0]), this.enableHelmet, true));
        this.textureHelmet.setValidator(() -> this.enableHelmet.isSelected() && !this.textureHelmet.hasTexture() ? new Validator.ValidationResult(ValidationResultType.ERROR, L10N.t("elementgui.armor.need_texture", new Object[0])) : ValidationResult.PASSED);
        this.helmetName.enableRealtimeValidation();
        this.groupHelmetPage.addValidationElement(this.helmetName);
        this.groupHelmetPage.addValidationElement(this.textureHelmet);
        JPanel bodyPage = new JPanel(new BorderLayout());
        JPanel bodyTop = new JPanel(new BorderLayout());
        JPanel bodyBottom = new JPanel(new GridLayout(11, 2, 2, 2));
        JPanel bodyTexturePanel = new JPanel();
        JPanel bodyConditionPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        bodyPage.setOpaque(false);
        bodyTop.setOpaque(false);
        bodyBottom.setOpaque(false);
        bodyTexturePanel.setOpaque(false);
        bodyConditionPanel.setOpaque(false);
        this.isDyeableBody.setOpaque(false);
        this.isDyeableBody.setSelected(true);
        this.isDyeableBody.addActionListener((e) -> this.updateBodyDye());
        this.canWaterRemoveColorBody.setOpaque(false);
        this.canLavaRemoveColorBody.setOpaque(false);
        this.canPowderSnowRemoveColorBody.setOpaque(false);
        this.defaultColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canWaterRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canLavaRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canPowderSnowRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.enableBody.setOpaque(false);
        this.enableBody.setSelected(true);
        this.enableBody.addActionListener((e) -> this.updateBodyPage());
        this.bodyImmuneToFire.setOpaque(false);
        bodyTexturePanel.add(ComponentUtils.squareAndBorder(this.textureBody, L10N.t("elementgui.item.texture", new Object[0])));
        bodyTexturePanel.add(ComponentUtils.squareAndBorder(this.overlayTextureBody, L10N.t("elementgui.dyeable.common.overlay_texture", new Object[0])));
        bodyTop.add(bodyTexturePanel, "North");
        bodyTop.add(this.enableBody, "Center");
        bodyBottom.add(L10N.label("elementgui.armor.body_name", new Object[0]));
        bodyBottom.add(this.bodyName);
        bodyBottom.add(L10N.label("elementgui.armor.supported_java", new Object[0]));
        bodyBottom.add(PanelUtils.gridElements(1, 2, 2, 2, new Component[]{this.bodyModel, this.bodyModelPart}));
        bodyBottom.add(new JEmptyBox());
        bodyBottom.add(PanelUtils.gridElements(1, 2, 2, 2, new Component[]{PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_arm_left", new Object[0]), this.armsModelPartL, 5, 5), PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_arm_right", new Object[0]), this.armsModelPartR, 5, 5)}));
        bodyBottom.add(L10N.label("elementgui.armor.texture", new Object[0]));
        bodyBottom.add(this.bodyModelTexture);
        bodyBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])));
        bodyBottom.add(this.bodyItemRenderType);
        bodyBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/immune_to_fire"), L10N.label("elementgui.item.is_immune_to_fire", new Object[0])));
        bodyBottom.add(this.bodyImmuneToFire);
        bodyBottom.add(L10N.label("elementgui.dyeable.common.is_dyeable", new Object[0]));
        bodyBottom.add(this.isDyeableBody);
        bodyBottom.add(L10N.label("elementgui.dyeable.common.default_color", new Object[0]));
        bodyBottom.add(this.defaultColorBody);
        bodyBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_water"), L10N.label("elementgui.dyeable.common.can_water_remove_color", new Object[0])));
        bodyBottom.add(this.canWaterRemoveColorBody);
        bodyBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_lava"), L10N.label("elementgui.dyeable.common.can_lava_remove_color", new Object[0])));
        bodyBottom.add(this.canLavaRemoveColorBody);
        bodyBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_powder_snow"), L10N.label("elementgui.dyeable.common.can_powder_snow_remove_color", new Object[0])));
        bodyBottom.add(this.canPowderSnowRemoveColorBody);
        bodyConditionPanel.add(this.bodyGlowCondition);
        bodyConditionPanel.add(this.bodySpecialInformation);
        bodyConditionPanel.add(this.bodyPiglinNeutral);
        bodyPage.add(bodyTop, "North");
        bodyPage.add(bodyBottom, "Center");
        bodyPage.add(bodyConditionPanel, "South");
        this.bodyModelListener = (e) -> {
            Model model = (Model)this.bodyModel.getSelectedItem();
            if (model != null && model != defaultModel) {
                this.bodyModelPart.removeAllItems();
                this.armsModelPartL.removeAllItems();
                this.armsModelPartR.removeAllItems();

                try {
                    this.armsModelPartL.addItem("");
                    this.armsModelPartR.addItem("");
                    ComboBoxUtil.updateComboBoxContents(this.bodyModelPart, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                    ComboBoxUtil.updateComboBoxContents(this.armsModelPartL, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                    ComboBoxUtil.updateComboBoxContents(this.armsModelPartR, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            } else {
                this.bodyModelPart.removeAllItems();
                this.armsModelPartL.removeAllItems();
                this.armsModelPartR.removeAllItems();
                this.bodyModelPart.addItem("Body");
                this.armsModelPartL.addItem("Arm L");
                this.armsModelPartR.addItem("Arm R");
            }

        };
        this.bodyModelListener.actionPerformed(new ActionEvent("", 0, ""));
        this.bodyName.setValidator(new ConditionalTextFieldValidator(this.bodyName, L10N.t("elementgui.armor.chestplate_needs_name", new Object[0]), this.enableBody, true));
        this.textureBody.setValidator(() -> this.enableBody.isSelected() && !this.textureBody.hasTexture() ? new Validator.ValidationResult(ValidationResultType.ERROR, L10N.t("elementgui.armor.need_texture", new Object[0])) : ValidationResult.PASSED);
        this.bodyName.enableRealtimeValidation();
        this.groupBodyPage.addValidationElement(this.bodyName);
        this.groupBodyPage.addValidationElement(this.textureBody);
        JPanel leggingsPage = new JPanel(new BorderLayout());
        JPanel leggingsTop = new JPanel(new BorderLayout());
        JPanel leggingsBottom = new JPanel(new GridLayout(11, 2, 2, 2));
        JPanel leggingsTexturePanel = new JPanel();
        JPanel leggingsConditionPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        leggingsPage.setOpaque(false);
        leggingsTop.setOpaque(false);
        leggingsBottom.setOpaque(false);
        leggingsTexturePanel.setOpaque(false);
        leggingsConditionPanel.setOpaque(false);
        this.isDyeableLeggings.setOpaque(false);
        this.isDyeableLeggings.setSelected(true);
        this.isDyeableLeggings.addActionListener((e) -> this.updateLeggingsDye());
        this.canWaterRemoveColorLeggings.setOpaque(false);
        this.canLavaRemoveColorLeggings.setOpaque(false);
        this.canPowderSnowRemoveColorLeggings.setOpaque(false);
        this.defaultColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canWaterRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canLavaRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canPowderSnowRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.enableLeggings.setOpaque(false);
        this.enableLeggings.setSelected(true);
        this.enableLeggings.addActionListener((e) -> this.updateLeggingsPage());
        this.leggingsImmuneToFire.setOpaque(false);
        leggingsTexturePanel.add(ComponentUtils.squareAndBorder(this.textureLeggings, L10N.t("elementgui.item.texture", new Object[0])));
        leggingsTexturePanel.add(ComponentUtils.squareAndBorder(this.overlayTextureLeggings, L10N.t("elementgui.dyeable.common.overlay_texture", new Object[0])));
        leggingsTop.add(leggingsTexturePanel, "North");
        leggingsTop.add(this.enableLeggings, "Center");
        leggingsBottom.add(L10N.label("elementgui.armor.leggings_name", new Object[0]));
        leggingsBottom.add(this.leggingsName);
        leggingsBottom.add(L10N.label("elementgui.armor.supported_java", new Object[0]));
        leggingsBottom.add(this.leggingsModel);
        leggingsBottom.add(new JEmptyBox());
        leggingsBottom.add(PanelUtils.gridElements(1, 2, 2, 2, new Component[]{PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_leg_left", new Object[0]), this.leggingsModelPartL, 5, 5), PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_leg_right", new Object[0]), this.leggingsModelPartR, 5, 5)}));
        leggingsBottom.add(L10N.label("elementgui.armor.texture", new Object[0]));
        leggingsBottom.add(this.leggingsModelTexture);
        leggingsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])));
        leggingsBottom.add(this.leggingsItemRenderType);
        leggingsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/immune_to_fire"), L10N.label("elementgui.item.is_immune_to_fire", new Object[0])));
        leggingsBottom.add(this.leggingsImmuneToFire);
        leggingsBottom.add(L10N.label("elementgui.dyeable.common.is_dyeable", new Object[0]));
        leggingsBottom.add(this.isDyeableLeggings);
        leggingsBottom.add(L10N.label("elementgui.dyeable.common.default_color", new Object[0]));
        leggingsBottom.add(this.defaultColorLeggings);
        leggingsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_water"), L10N.label("elementgui.dyeable.common.can_water_remove_color", new Object[0])));
        leggingsBottom.add(this.canWaterRemoveColorLeggings);
        leggingsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_lava"), L10N.label("elementgui.dyeable.common.can_lava_remove_color", new Object[0])));
        leggingsBottom.add(this.canLavaRemoveColorLeggings);
        leggingsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_powder_snow"), L10N.label("elementgui.dyeable.common.can_powder_snow_remove_color", new Object[0])));
        leggingsBottom.add(this.canPowderSnowRemoveColorLeggings);
        leggingsConditionPanel.add(this.leggingsGlowCondition);
        leggingsConditionPanel.add(this.leggingsSpecialInformation);
        leggingsConditionPanel.add(this.leggingsPiglinNeutral);
        leggingsPage.add(leggingsTop, "North");
        leggingsPage.add(leggingsBottom, "Center");
        leggingsPage.add(leggingsConditionPanel, "South");
        this.leggingsModelListener = (e) -> {
            Model model = (Model)this.leggingsModel.getSelectedItem();
            if (model != null && model != defaultModel) {
                this.leggingsModelPartL.removeAllItems();
                this.leggingsModelPartR.removeAllItems();

                try {
                    ComboBoxUtil.updateComboBoxContents(this.leggingsModelPartL, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                    ComboBoxUtil.updateComboBoxContents(this.leggingsModelPartR, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            } else {
                this.leggingsModelPartL.removeAllItems();
                this.leggingsModelPartR.removeAllItems();
                this.leggingsModelPartL.addItem("Legging L");
                this.leggingsModelPartR.addItem("Legging R");
            }

        };
        this.leggingsModelListener.actionPerformed(new ActionEvent("", 0, ""));
        this.leggingsName.setValidator(new ConditionalTextFieldValidator(this.leggingsName, L10N.t("elementgui.armor.leggings_needs_name", new Object[0]), this.enableLeggings, true));
        this.textureLeggings.setValidator(() -> this.enableLeggings.isSelected() && !this.textureLeggings.hasTexture() ? new Validator.ValidationResult(ValidationResultType.ERROR, L10N.t("elementgui.armor.need_texture", new Object[0])) : ValidationResult.PASSED);
        this.leggingsName.enableRealtimeValidation();
        this.groupBodyPage.addValidationElement(this.leggingsName);
        this.groupBodyPage.addValidationElement(this.textureLeggings);
        JPanel bootsPage = new JPanel(new BorderLayout());
        JPanel bootsTop = new JPanel(new BorderLayout());
        JPanel bootsBottom = new JPanel(new GridLayout(11, 2, 2, 2));
        JPanel bootsTexturePanel = new JPanel();
        JPanel bootsConditionPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        bootsPage.setOpaque(false);
        bootsTop.setOpaque(false);
        bootsBottom.setOpaque(false);
        bootsTexturePanel.setOpaque(false);
        bootsConditionPanel.setOpaque(false);
        this.isDyeableBoots.setOpaque(false);
        this.isDyeableBoots.setSelected(true);
        this.isDyeableBoots.addActionListener((e) -> this.updateBootsDye());
        this.canWaterRemoveColorBoots.setOpaque(false);
        this.canLavaRemoveColorBoots.setOpaque(false);
        this.canPowderSnowRemoveColorBoots.setOpaque(false);
        this.defaultColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canWaterRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canLavaRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canPowderSnowRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.enableBoots.setOpaque(false);
        this.enableBoots.setSelected(true);
        this.enableBoots.addActionListener((e) -> this.updateBootsPage());
        this.bootsImmuneToFire.setOpaque(false);
        bootsTexturePanel.add(ComponentUtils.squareAndBorder(this.textureBoots, L10N.t("elementgui.item.texture", new Object[0])));
        bootsTexturePanel.add(ComponentUtils.squareAndBorder(this.overlayTextureBoots, L10N.t("elementgui.dyeable.common.overlay_texture", new Object[0])));
        bootsTop.add(bootsTexturePanel, "North");
        bootsTop.add(this.enableBoots, "Center");
        bootsBottom.add(L10N.label("elementgui.armor.boots_name", new Object[0]));
        bootsBottom.add(this.bootsName);
        bootsBottom.add(L10N.label("elementgui.armor.supported_java", new Object[0]));
        bootsBottom.add(this.bootsModel);
        bootsBottom.add(new JEmptyBox());
        bootsBottom.add(PanelUtils.gridElements(1, 2, 2, 2, new Component[]{PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_boot_left", new Object[0]), this.bootsModelPartL, 5, 5), PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_boot_right", new Object[0]), this.bootsModelPartR, 5, 5)}));
        bootsBottom.add(L10N.label("elementgui.armor.texture", new Object[0]));
        bootsBottom.add(this.bootsModelTexture);
        bootsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])));
        bootsBottom.add(this.bootsItemRenderType);
        bootsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/immune_to_fire"), L10N.label("elementgui.item.is_immune_to_fire", new Object[0])));
        bootsBottom.add(this.bootsImmuneToFire);
        bootsBottom.add(L10N.label("elementgui.dyeable.common.is_dyeable", new Object[0]));
        bootsBottom.add(this.isDyeableBoots);
        bootsBottom.add(L10N.label("elementgui.dyeable.common.default_color", new Object[0]));
        bootsBottom.add(this.defaultColorBoots);
        bootsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_water"), L10N.label("elementgui.dyeable.common.can_water_remove_color", new Object[0])));
        bootsBottom.add(this.canWaterRemoveColorBoots);
        bootsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_lava"), L10N.label("elementgui.dyeable.common.can_lava_remove_color", new Object[0])));
        bootsBottom.add(this.canLavaRemoveColorBoots);
        bootsBottom.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_powder_snow"), L10N.label("elementgui.dyeable.common.can_powder_snow_remove_color", new Object[0])));
        bootsBottom.add(this.canPowderSnowRemoveColorBoots);
        bootsConditionPanel.add(this.bootsGlowCondition);
        bootsConditionPanel.add(this.bootsSpecialInformation);
        bootsConditionPanel.add(this.bootsPiglinNeutral);
        bootsPage.add(bootsTop, "North");
        bootsPage.add(bootsBottom, "Center");
        bootsPage.add(bootsConditionPanel, "South");
        this.bootsModelListener = (e) -> {
            Model model = (Model)this.bootsModel.getSelectedItem();
            if (model != null && model != defaultModel) {
                this.bootsModelPartL.removeAllItems();
                this.bootsModelPartR.removeAllItems();

                try {
                    ComboBoxUtil.updateComboBoxContents(this.bootsModelPartL, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                    ComboBoxUtil.updateComboBoxContents(this.bootsModelPartR, JavaModels.getModelParts((JavaClassSource)Roaster.parse(model.getFile())));
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            } else {
                this.bootsModelPartL.removeAllItems();
                this.bootsModelPartR.removeAllItems();
                this.bootsModelPartL.addItem("Boot L");
                this.bootsModelPartR.addItem("Boot R");
            }

        };
        this.bootsModelListener.actionPerformed(new ActionEvent("", 0, ""));
        this.bootsName.setValidator(new ConditionalTextFieldValidator(this.bootsName, L10N.t("elementgui.armor.boots_needs_name", new Object[0]), this.enableBoots, true));
        this.textureBoots.setValidator(() -> this.enableBoots.isSelected() && !this.textureLeggings.hasTexture() ? new Validator.ValidationResult(ValidationResultType.ERROR, L10N.t("elementgui.armor.need_texture", new Object[0])) : ValidationResult.PASSED);
        this.bootsName.enableRealtimeValidation();
        this.groupBootsPage.addValidationElement(this.bootsName);
        this.groupBootsPage.addValidationElement(this.textureBoots);
        JPanel propertiesPage = new JPanel(new BorderLayout(35, 35));
        JPanel propertiesPanel = new JPanel(new GridLayout(10, 2, 20, 2));
        propertiesPage.setOpaque(false);
        propertiesPanel.setOpaque(false);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/armor_layer_texture"), L10N.label("elementgui.armor.layer_texture", new Object[0])));
        propertiesPanel.add(this.armorTextureFile);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/armor/overlay_texture"), L10N.label("elementgui.dyeable.armor.overlay_texture", new Object[0])));
        propertiesPanel.add(this.armorOverlayTextureFile);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/creative_tabs"), L10N.label("elementgui.common.creative_tabs", new Object[0])));
        propertiesPanel.add(this.creativeTabs);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/equip_sound"), L10N.label("elementgui.armor.equip_sound", new Object[0])));
        propertiesPanel.add(this.equipSound);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/max_damage_absorbed"), L10N.label("elementgui.armor.max_damage_absorption", new Object[0])));
        propertiesPanel.add(this.maxDamage);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/damage_values"), L10N.label("elementgui.armor.damage_values", new Object[0])));
        propertiesPanel.add(PanelUtils.gridElements(1, 4, 2, 2, new Component[]{this.damageValueHelmet, this.damageValueBody, this.damageValueLeggings, this.damageValueBoots}));
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/enchantability"), L10N.label("elementgui.common.enchantability", new Object[0])));
        propertiesPanel.add(this.enchantability);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/toughness"), L10N.label("elementgui.armor.toughness", new Object[0])));
        propertiesPanel.add(this.toughness);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/knockback_resistance"), L10N.label("elementgui.armor.knockback_resistance", new Object[0])));
        propertiesPanel.add(this.knockbackResistance);
        propertiesPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armor/repair_items"), L10N.label("elementgui.common.repair_items", new Object[0])));
        propertiesPanel.add(this.repairItems);
        this.armorTextureFile.getComboBox().addActionListener((e) -> this.updateArmorTexturePreview());
        this.armorOverlayTextureFile.getComboBox().addActionListener((e) -> this.updateArmorTexturePreview());
        this.col1.setPreferredSize(new Dimension(320, 160));
        this.col2.setPreferredSize(new Dimension(320, 160));
        propertiesPage.add(propertiesPanel, "Center");
        propertiesPage.add(PanelUtils.join(new Component[]{this.col1, this.col2}), "South");
        this.groupPropertiesPage.addValidationElement(this.armorTextureFile);
        JPanel eventsPage = new JPanel(new BorderLayout(10, 10));
        JPanel events = new JPanel(new GridLayout(1, 4, 5, 5));
        eventsPage.setOpaque(false);
        events.setOpaque(false);
        events.add(this.onHelmetTick);
        events.add(this.onBodyTick);
        events.add(this.onLeggingsTick);
        events.add(this.onBootsTick);
        eventsPage.add(PanelUtils.totalCenterInPanel(events), "Center");
        this.addPage(L10N.t("elementgui.dyeable.armor.helmet", new Object[0]), PanelUtils.centerInPanel(helmetPage));
        this.addPage(L10N.t("elementgui.dyeable.armor.body", new Object[0]), PanelUtils.centerInPanel(bodyPage));
        this.addPage(L10N.t("elementgui.dyeable.armor.leggings", new Object[0]), PanelUtils.centerInPanel(leggingsPage));
        this.addPage(L10N.t("elementgui.dyeable.armor.boots", new Object[0]), PanelUtils.centerInPanel(bootsPage));
        this.addPage(L10N.t("elementgui.common.page_properties", new Object[0]), PanelUtils.centerInPanel(propertiesPage));
        this.addPage(L10N.t("elementgui.common.page_triggers", new Object[0]), eventsPage);
        if (!this.isEditingMode()) {
            this.creativeTabs.setListElements(List.of(new TabEntry(this.mcreator.getWorkspace(), "COMBAT")));
            String readableName = StringUtils.machineToReadableName(this.modElement.getName());
            this.helmetName.setText(L10N.t("elementgui.armor.helmet", new Object[]{readableName}));
            this.bodyName.setText(L10N.t("elementgui.armor.chestplate", new Object[]{readableName}));
            this.leggingsName.setText(L10N.t("elementgui.armor.leggings", new Object[]{readableName}));
            this.bootsName.setText(L10N.t("elementgui.armor.boots", new Object[]{readableName}));
        }

        this.updateHelmetPage();
        this.updateBodyPage();
        this.updateLeggingsPage();
        this.updateBootsPage();
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        this.helmetModel.removeActionListener(this.helmetModelListener);
        this.bodyModel.removeActionListener(this.bodyModelListener);
        this.leggingsModel.removeActionListener(this.leggingsModelListener);
        this.bootsModel.removeActionListener(this.bootsModelListener);
        this.helmetGlowCondition.refreshListKeepSelected();
        this.bodyGlowCondition.refreshListKeepSelected();
        this.leggingsGlowCondition.refreshListKeepSelected();
        this.bootsGlowCondition.refreshListKeepSelected();
        this.helmetPiglinNeutral.refreshListKeepSelected();
        this.bodyPiglinNeutral.refreshListKeepSelected();
        this.leggingsPiglinNeutral.refreshListKeepSelected();
        this.bootsPiglinNeutral.refreshListKeepSelected();
        this.onHelmetTick.refreshListKeepSelected();
        this.onBodyTick.refreshListKeepSelected();
        this.onLeggingsTick.refreshListKeepSelected();
        this.onBootsTick.refreshListKeepSelected();
        this.helmetSpecialInformation.refreshListKeepSelected();
        this.bodySpecialInformation.refreshListKeepSelected();
        this.leggingsSpecialInformation.refreshListKeepSelected();
        this.bootsSpecialInformation.refreshListKeepSelected();
        ComboBoxUtil.updateComboBoxContents(this.helmetModel, ListUtils.merge(Collections.singleton(defaultModel), (Collection)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JAVA || el.getType() == Type.MCREATOR).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.bodyModel, ListUtils.merge(Collections.singleton(defaultModel), (Collection)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JAVA || el.getType() == Type.MCREATOR).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.leggingsModel, ListUtils.merge(Collections.singleton(defaultModel), (Collection)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JAVA || el.getType() == Type.MCREATOR).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.bootsModel, ListUtils.merge(Collections.singleton(defaultModel), (Collection)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JAVA || el.getType() == Type.MCREATOR).collect(Collectors.toList())));
        this.helmetModelTexture.reload();
        this.bodyModelTexture.reload();
        this.leggingsModelTexture.reload();
        this.bootsModelTexture.reload();
        ComboBoxUtil.updateComboBoxContents(this.helmetItemRenderType, ListUtils.merge(Arrays.asList(this.normal, this.tool), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JSON || el.getType() == Type.OBJ).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.bodyItemRenderType, ListUtils.merge(Arrays.asList(this.normal, this.tool), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JSON || el.getType() == Type.OBJ).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.leggingsItemRenderType, ListUtils.merge(Arrays.asList(this.normal, this.tool), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JSON || el.getType() == Type.OBJ).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.bootsItemRenderType, ListUtils.merge(Arrays.asList(this.normal, this.tool), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JSON || el.getType() == Type.OBJ).collect(Collectors.toList())));
        this.armorTextureFile.reload();
        this.armorOverlayTextureFile.reload();
        this.helmetModel.addActionListener(this.helmetModelListener);
        this.bodyModel.addActionListener(this.bodyModelListener);
        this.leggingsModel.addActionListener(this.leggingsModelListener);
        this.bootsModel.addActionListener(this.bootsModelListener);
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (page == 0) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.groupHelmetPage});
        } else if (page == 1) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.groupBodyPage});
        } else if (page == 2) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.groupLeggingsPage});
        } else if (page == 3) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.groupBootsPage});
        } else {
            return (AggregatedValidationResult)(page == 4 ? new AggregatedValidationResult(new ValidationGroup[]{this.groupPropertiesPage}) : new AggregatedValidationResult.PASS());
        }
    }

    private void updateHelmetPage() {
        this.textureHelmet.setEnabled(this.enableHelmet.isSelected());
        this.overlayTextureHelmet.setEnabled(this.enableHelmet.isSelected());
        this.helmetName.setEnabled(this.enableHelmet.isSelected());
        this.helmetModelTexture.setEnabled(this.enableHelmet.isSelected());
        this.helmetModel.setEnabled(this.enableHelmet.isSelected());
        this.helmetModelPart.setEnabled(this.enableHelmet.isSelected());
        this.helmetItemRenderType.setEnabled(this.enableHelmet.isSelected());
        this.helmetImmuneToFire.setEnabled(this.enableHelmet.isSelected());
        this.isDyeableHelmet.setEnabled(this.enableHelmet.isSelected());
        this.defaultColorHelmet.setEnabled(this.enableHelmet.isSelected());
        this.canWaterRemoveColorHelmet.setEnabled(this.enableHelmet.isSelected());
        this.canLavaRemoveColorHelmet.setEnabled(this.enableHelmet.isSelected());
        this.canPowderSnowRemoveColorHelmet.setEnabled(this.enableHelmet.isSelected());
        this.helmetGlowCondition.setEnabled(this.enableHelmet.isSelected());
        this.helmetPiglinNeutral.setEnabled(this.enableHelmet.isSelected());
        this.helmetSpecialInformation.setEnabled(this.enableHelmet.isSelected());
        this.updateHelmetDye();
    }

    private void updateHelmetDye() {
        this.defaultColorHelmet.setEnabled(this.isDyeableHelmet.isSelected() && this.enableHelmet.isSelected());
        this.canWaterRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected() && this.enableHelmet.isSelected());
        this.canLavaRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected() && this.enableHelmet.isSelected());
        this.canPowderSnowRemoveColorHelmet.setEnabled(this.isDyeableHelmet.isSelected() && this.enableHelmet.isSelected());
    }

    private void updateBodyPage() {
        this.textureBody.setEnabled(this.enableBody.isSelected());
        this.overlayTextureBody.setEnabled(this.enableBody.isSelected());
        this.bodyName.setEnabled(this.enableBody.isSelected());
        this.bodyModelTexture.setEnabled(this.enableBody.isSelected());
        this.bodyModel.setEnabled(this.enableBody.isSelected());
        this.bodyModelPart.setEnabled(this.enableBody.isSelected());
        this.armsModelPartL.setEnabled(this.enableBody.isSelected());
        this.armsModelPartR.setEnabled(this.enableBody.isSelected());
        this.bodyItemRenderType.setEnabled(this.enableBody.isSelected());
        this.bodyImmuneToFire.setEnabled(this.enableBody.isSelected());
        this.isDyeableBody.setEnabled(this.enableBody.isSelected());
        this.defaultColorBody.setEnabled(this.enableBody.isSelected());
        this.canWaterRemoveColorBody.setEnabled(this.enableBody.isSelected());
        this.canLavaRemoveColorBody.setEnabled(this.enableBody.isSelected());
        this.canPowderSnowRemoveColorBody.setEnabled(this.enableBody.isSelected());
        this.bodyGlowCondition.setEnabled(this.enableBody.isSelected());
        this.bodyPiglinNeutral.setEnabled(this.enableBody.isSelected());
        this.bodySpecialInformation.setEnabled(this.enableBody.isSelected());
        this.updateBodyDye();
    }

    private void updateBodyDye() {
        this.defaultColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canWaterRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canLavaRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
        this.canPowderSnowRemoveColorBody.setEnabled(this.isDyeableBody.isSelected());
    }

    private void updateLeggingsPage() {
        this.textureLeggings.setEnabled(this.enableLeggings.isSelected());
        this.overlayTextureLeggings.setEnabled(this.enableLeggings.isSelected());
        this.leggingsName.setEnabled(this.enableLeggings.isSelected());
        this.leggingsModelTexture.setEnabled(this.enableLeggings.isSelected());
        this.leggingsModel.setEnabled(this.enableLeggings.isSelected());
        this.leggingsModelPartL.setEnabled(this.enableLeggings.isSelected());
        this.leggingsModelPartR.setEnabled(this.enableLeggings.isSelected());
        this.leggingsItemRenderType.setEnabled(this.enableLeggings.isSelected());
        this.leggingsImmuneToFire.setEnabled(this.enableLeggings.isSelected());
        this.isDyeableLeggings.setEnabled(this.enableLeggings.isSelected());
        this.defaultColorLeggings.setEnabled(this.enableLeggings.isSelected());
        this.canWaterRemoveColorLeggings.setEnabled(this.enableLeggings.isSelected());
        this.canLavaRemoveColorLeggings.setEnabled(this.enableLeggings.isSelected());
        this.canPowderSnowRemoveColorLeggings.setEnabled(this.enableLeggings.isSelected());
        this.leggingsGlowCondition.setEnabled(this.enableLeggings.isSelected());
        this.leggingsPiglinNeutral.setEnabled(this.enableLeggings.isSelected());
        this.leggingsSpecialInformation.setEnabled(this.enableLeggings.isSelected());
        this.updateLeggingsDye();
    }

    private void updateLeggingsDye() {
        this.defaultColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canWaterRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canLavaRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
        this.canPowderSnowRemoveColorLeggings.setEnabled(this.isDyeableLeggings.isSelected());
    }

    private void updateBootsPage() {
        this.textureBoots.setEnabled(this.enableBoots.isSelected());
        this.overlayTextureBoots.setEnabled(this.enableBoots.isSelected());
        this.bootsName.setEnabled(this.enableBoots.isSelected());
        this.bootsModelTexture.setEnabled(this.enableBoots.isSelected());
        this.bootsModel.setEnabled(this.enableBoots.isSelected());
        this.bootsModelPartL.setEnabled(this.enableBoots.isSelected());
        this.bootsModelPartR.setEnabled(this.enableBoots.isSelected());
        this.bootsItemRenderType.setEnabled(this.enableBoots.isSelected());
        this.bootsImmuneToFire.setEnabled(this.enableBoots.isSelected());
        this.isDyeableBoots.setEnabled(this.enableBoots.isSelected());
        this.defaultColorBoots.setEnabled(this.enableBoots.isSelected());
        this.canWaterRemoveColorBoots.setEnabled(this.enableBoots.isSelected());
        this.canLavaRemoveColorBoots.setEnabled(this.enableBoots.isSelected());
        this.canPowderSnowRemoveColorBoots.setEnabled(this.enableBoots.isSelected());
        this.bootsGlowCondition.setEnabled(this.enableBoots.isSelected());
        this.bootsPiglinNeutral.setEnabled(this.enableBoots.isSelected());
        this.bootsSpecialInformation.setEnabled(this.enableBoots.isSelected());
        this.updateBootsDye();
    }

    private void updateBootsDye() {
        this.defaultColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canWaterRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canLavaRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
        this.canPowderSnowRemoveColorBoots.setEnabled(this.isDyeableBoots.isSelected());
    }

    private void updateArmorTexturePreview() {
        File[] armorTextures = this.mcreator.getFolderManager().getArmorTextureFilesForName(this.armorTextureFile.getTextureName());
        if (armorTextures[0].isFile() && armorTextures[1].isFile()) {
            ImageIcon bg1 = new ImageIcon(ImageUtils.resize((new ImageIcon(armorTextures[0].getAbsolutePath())).getImage(), 320, 160));
            ImageIcon bg2 = new ImageIcon(ImageUtils.resize((new ImageIcon(armorTextures[1].getAbsolutePath())).getImage(), 320, 160));
            ImageIcon front1 = new ImageIcon(Preview.generateArmorPreviewFrame1());
            ImageIcon front2 = new ImageIcon(Preview.generateArmorPreviewFrame2());
            this.col1.setIcon(ImageUtils.drawOver(bg1, front1));
            this.col2.setIcon(ImageUtils.drawOver(bg2, front2));
        } else {
            this.col1.setIcon(new ImageIcon(Preview.generateArmorPreviewFrame1()));
            this.col2.setIcon(new ImageIcon(Preview.generateArmorPreviewFrame2()));
        }

    }

    protected void openInEditingMode(DyeableArmor armor) {
        this.textureHelmet.setTexture(armor.textureHelmet);
        this.textureBody.setTexture(armor.textureBody);
        this.textureLeggings.setTexture(armor.textureLeggings);
        this.textureBoots.setTexture(armor.textureBoots);
        this.overlayTextureHelmet.setTexture(armor.helmetOverlayTexture);
        this.overlayTextureBody.setTexture(armor.bodyOverlayTexture);
        this.overlayTextureLeggings.setTexture(armor.leggingsOverlayTexture);
        this.overlayTextureBoots.setTexture(armor.bootsOverlayTexture);
        this.armorTextureFile.setTextureFromTextureName(armor.armorTextureFile);
        this.armorOverlayTextureFile.setTextureFromTextureName(armor.armorOverlayTextureFile);
        this.maxDamage.setValue(armor.maxDamage);
        this.damageValueBoots.setValue(armor.damageValueBoots);
        this.damageValueLeggings.setValue(armor.damageValueLeggings);
        this.damageValueBody.setValue(armor.damageValueBody);
        this.damageValueHelmet.setValue(armor.damageValueHelmet);
        this.enchantability.setValue(armor.enchantability);
        this.toughness.setValue(armor.toughness);
        this.knockbackResistance.setValue(armor.knockbackResistance);
        this.creativeTabs.setListElements(armor.creativeTabs);
        this.repairItems.setListElements(armor.repairItems);
        this.equipSound.setSound(armor.equipSound);
        this.onHelmetTick.setSelectedProcedure(armor.onHelmetTick);
        this.onBodyTick.setSelectedProcedure(armor.onBodyTick);
        this.onLeggingsTick.setSelectedProcedure(armor.onLeggingsTick);
        this.onBootsTick.setSelectedProcedure(armor.onBootsTick);
        this.helmetSpecialInformation.setSelectedProcedure(armor.helmetSpecialInformation);
        this.bodySpecialInformation.setSelectedProcedure(armor.bodySpecialInformation);
        this.leggingsSpecialInformation.setSelectedProcedure(armor.leggingsSpecialInformation);
        this.bootsSpecialInformation.setSelectedProcedure(armor.bootsSpecialInformation);
        this.enableHelmet.setSelected(armor.enableHelmet);
        this.enableBody.setSelected(armor.enableBody);
        this.enableLeggings.setSelected(armor.enableLeggings);
        this.enableBoots.setSelected(armor.enableBoots);
        this.textureHelmet.setEnabled(this.enableHelmet.isSelected());
        this.textureBody.setEnabled(this.enableBody.isSelected());
        this.textureLeggings.setEnabled(this.enableLeggings.isSelected());
        this.textureBoots.setEnabled(this.enableBoots.isSelected());
        this.helmetName.setText(armor.helmetName);
        this.bodyName.setText(armor.bodyName);
        this.leggingsName.setText(armor.leggingsName);
        this.bootsName.setText(armor.bootsName);
        Model _helmetModel = armor.getHelmetModel();
        if (_helmetModel != null) {
            this.helmetModel.setSelectedItem(_helmetModel);
        }

        Model _bodyModel = armor.getBodyModel();
        if (_bodyModel != null) {
            this.bodyModel.setSelectedItem(_bodyModel);
        }

        Model _leggingsModel = armor.getLeggingsModel();
        if (_leggingsModel != null) {
            this.leggingsModel.setSelectedItem(_leggingsModel);
        }

        Model _bootsModel = armor.getBootsModel();
        if (_bootsModel != null) {
            this.bootsModel.setSelectedItem(_bootsModel);
        }

        this.helmetModelTexture.setTextureFromTextureName(armor.helmetModelTexture);
        this.bodyModelTexture.setTextureFromTextureName(armor.bodyModelTexture);
        this.leggingsModelTexture.setTextureFromTextureName(armor.leggingsModelTexture);
        this.bootsModelTexture.setTextureFromTextureName(armor.bootsModelTexture);
        this.helmetModelPart.setSelectedItem(armor.helmetModelPart);
        this.bodyModelPart.setSelectedItem(armor.bodyModelPart);
        this.armsModelPartL.setSelectedItem(armor.armsModelPartL);
        this.armsModelPartR.setSelectedItem(armor.armsModelPartR);
        this.leggingsModelPartL.setSelectedItem(armor.leggingsModelPartL);
        this.leggingsModelPartR.setSelectedItem(armor.leggingsModelPartR);
        this.bootsModelPartL.setSelectedItem(armor.bootsModelPartL);
        this.bootsModelPartR.setSelectedItem(armor.bootsModelPartR);
        this.helmetImmuneToFire.setSelected(armor.helmetImmuneToFire);
        this.bodyImmuneToFire.setSelected(armor.bodyImmuneToFire);
        this.leggingsImmuneToFire.setSelected(armor.leggingsImmuneToFire);
        this.bootsImmuneToFire.setSelected(armor.bootsImmuneToFire);
        this.helmetGlowCondition.setSelectedProcedure(armor.helmetGlowCondition);
        this.bodyGlowCondition.setSelectedProcedure(armor.bodyGlowCondition);
        this.leggingsGlowCondition.setSelectedProcedure(armor.leggingsGlowCondition);
        this.bootsGlowCondition.setSelectedProcedure(armor.bootsGlowCondition);
        this.helmetPiglinNeutral.setSelectedProcedure(armor.helmetPiglinNeutral);
        this.bodyPiglinNeutral.setSelectedProcedure(armor.bodyPiglinNeutral);
        this.leggingsPiglinNeutral.setSelectedProcedure(armor.leggingsPiglinNeutral);
        this.bootsPiglinNeutral.setSelectedProcedure(armor.bootsPiglinNeutral);
        Model helmetItemModel = armor.getHelmetItemModel();
        if (helmetItemModel != null) {
            this.helmetItemRenderType.setSelectedItem(helmetItemModel);
        }

        Model bodyItemModel = armor.getBodyItemModel();
        if (bodyItemModel != null) {
            this.bodyItemRenderType.setSelectedItem(bodyItemModel);
        }

        Model leggingsItemModel = armor.getLeggingsItemModel();
        if (leggingsItemModel != null) {
            this.leggingsItemRenderType.setSelectedItem(leggingsItemModel);
        }

        Model bootsItemModel = armor.getBootsItemModel();
        if (bootsItemModel != null) {
            this.bootsItemRenderType.setSelectedItem(bootsItemModel);
        }

        this.isDyeableHelmet.setSelected(armor.helmetIsDyeable);
        this.isDyeableBody.setSelected(armor.bodyIsDyeable);
        this.isDyeableLeggings.setSelected(armor.leggingsIsDyeable);
        this.isDyeableBoots.setSelected(armor.bootsIsDyeable);
        this.defaultColorHelmet.setColor(armor.helmetDefaultColor);
        this.defaultColorBody.setColor(armor.bodyDefaultColor);
        this.defaultColorLeggings.setColor(armor.leggingsDefaultColor);
        this.defaultColorBoots.setColor(armor.bootsDefaultColor);
        this.canWaterRemoveColorHelmet.setSelected(armor.helmetCanWaterRemoveColor);
        this.canWaterRemoveColorBody.setSelected(armor.bodyCanWaterRemoveColor);
        this.canWaterRemoveColorLeggings.setSelected(armor.leggingsCanWaterRemoveColor);
        this.canWaterRemoveColorBoots.setSelected(armor.bootsCanWaterRemoveColor);
        this.canLavaRemoveColorHelmet.setSelected(armor.helmetCanLavaRemoveColor);
        this.canLavaRemoveColorBody.setSelected(armor.bodyCanLavaRemoveColor);
        this.canLavaRemoveColorLeggings.setSelected(armor.leggingsCanLavaRemoveColor);
        this.canLavaRemoveColorBoots.setSelected(armor.bootsCanLavaRemoveColor);
        this.canPowderSnowRemoveColorHelmet.setSelected(armor.helmetCanPowderSnowRemoveColor);
        this.canPowderSnowRemoveColorBody.setSelected(armor.bodyCanPowderSnowRemoveColor);
        this.canPowderSnowRemoveColorLeggings.setSelected(armor.leggingsCanPowderSnowRemoveColor);
        this.canPowderSnowRemoveColorBoots.setSelected(armor.bootsCanPowderSnowRemoveColor);
        this.updateArmorTexturePreview();
        this.updateHelmetPage();
        this.updateBodyPage();
        this.updateLeggingsPage();
        this.updateBootsPage();
    }

    public DyeableArmor getElementFromGUI() {
        DyeableArmor armor = new DyeableArmor(this.modElement);
        armor.enableHelmet = this.enableHelmet.isSelected();
        armor.enableBody = this.enableBody.isSelected();
        armor.enableLeggings = this.enableLeggings.isSelected();
        armor.enableBoots = this.enableBoots.isSelected();
        armor.textureHelmet = this.textureHelmet.getTextureHolder();
        armor.textureBody = this.textureBody.getTextureHolder();
        armor.textureLeggings = this.textureLeggings.getTextureHolder();
        armor.textureBoots = this.textureBoots.getTextureHolder();
        armor.onHelmetTick = this.onHelmetTick.getSelectedProcedure();
        armor.onBodyTick = this.onBodyTick.getSelectedProcedure();
        armor.onLeggingsTick = this.onLeggingsTick.getSelectedProcedure();
        armor.onBootsTick = this.onBootsTick.getSelectedProcedure();
        armor.helmetSpecialInformation = this.helmetSpecialInformation.getSelectedProcedure();
        armor.bodySpecialInformation = this.bodySpecialInformation.getSelectedProcedure();
        armor.leggingsSpecialInformation = this.leggingsSpecialInformation.getSelectedProcedure();
        armor.bootsSpecialInformation = this.bootsSpecialInformation.getSelectedProcedure();
        armor.creativeTabs = this.creativeTabs.getListElements();
        armor.enchantability = (Integer)this.enchantability.getValue();
        armor.toughness = (Double)this.toughness.getValue();
        armor.knockbackResistance = (Double)this.knockbackResistance.getValue();
        armor.equipSound = this.equipSound.getSound();
        armor.repairItems = this.repairItems.getListElements();
        armor.armorTextureFile = this.armorTextureFile.getTextureName();
        armor.armorOverlayTextureFile = this.armorOverlayTextureFile.getTextureName();
        armor.maxDamage = (Integer)this.maxDamage.getValue();
        armor.damageValueHelmet = (Integer)this.damageValueHelmet.getValue();
        armor.damageValueBody = (Integer)this.damageValueBody.getValue();
        armor.damageValueLeggings = (Integer)this.damageValueLeggings.getValue();
        armor.damageValueBoots = (Integer)this.damageValueBoots.getValue();
        armor.helmetName = this.helmetName.getText();
        armor.bodyName = this.bodyName.getText();
        armor.leggingsName = this.leggingsName.getText();
        armor.bootsName = this.bootsName.getText();
        armor.helmetModelName = ((Model)Objects.requireNonNull((Model)this.helmetModel.getSelectedItem())).getReadableName();
        armor.bodyModelName = ((Model)Objects.requireNonNull((Model)this.bodyModel.getSelectedItem())).getReadableName();
        armor.leggingsModelName = ((Model)Objects.requireNonNull((Model)this.leggingsModel.getSelectedItem())).getReadableName();
        armor.bootsModelName = ((Model)Objects.requireNonNull((Model)this.bootsModel.getSelectedItem())).getReadableName();
        armor.helmetModelPart = (String)this.helmetModelPart.getSelectedItem();
        armor.bodyModelPart = (String)this.bodyModelPart.getSelectedItem();
        armor.armsModelPartL = (String)this.armsModelPartL.getSelectedItem();
        armor.armsModelPartR = (String)this.armsModelPartR.getSelectedItem();
        armor.leggingsModelPartL = (String)this.leggingsModelPartL.getSelectedItem();
        armor.leggingsModelPartR = (String)this.leggingsModelPartR.getSelectedItem();
        armor.bootsModelPartL = (String)this.bootsModelPartL.getSelectedItem();
        armor.bootsModelPartR = (String)this.bootsModelPartR.getSelectedItem();
        armor.helmetModelTexture = this.helmetModelTexture.getTextureName();
        armor.bodyModelTexture = this.bodyModelTexture.getTextureName();
        armor.leggingsModelTexture = this.leggingsModelTexture.getTextureName();
        armor.bootsModelTexture = this.bootsModelTexture.getTextureName();
        armor.helmetImmuneToFire = this.helmetImmuneToFire.isSelected();
        armor.bodyImmuneToFire = this.bodyImmuneToFire.isSelected();
        armor.leggingsImmuneToFire = this.leggingsImmuneToFire.isSelected();
        armor.bootsImmuneToFire = this.bootsImmuneToFire.isSelected();
        armor.helmetGlowCondition = this.helmetGlowCondition.getSelectedProcedure();
        armor.bodyGlowCondition = this.bodyGlowCondition.getSelectedProcedure();
        armor.leggingsGlowCondition = this.leggingsGlowCondition.getSelectedProcedure();
        armor.bootsGlowCondition = this.bootsGlowCondition.getSelectedProcedure();
        armor.helmetPiglinNeutral = this.helmetPiglinNeutral.getSelectedProcedure();
        armor.bodyPiglinNeutral = this.bodyPiglinNeutral.getSelectedProcedure();
        armor.leggingsPiglinNeutral = this.leggingsPiglinNeutral.getSelectedProcedure();
        armor.bootsPiglinNeutral = this.bootsPiglinNeutral.getSelectedProcedure();
        Model.Type helmetModelType = ((Model)Objects.requireNonNull((Model)this.helmetItemRenderType.getSelectedItem())).getType();
        armor.helmetItemRenderType = 0;
        if (helmetModelType == Type.JSON) {
            armor.helmetItemRenderType = 1;
        } else if (helmetModelType == Type.OBJ) {
            armor.helmetItemRenderType = 2;
        }

        armor.helmetItemCustomModelName = ((Model)Objects.requireNonNull((Model)this.helmetItemRenderType.getSelectedItem())).getReadableName();
        Model.Type bodyModelType = ((Model)Objects.requireNonNull((Model)this.bodyItemRenderType.getSelectedItem())).getType();
        armor.bodyItemRenderType = 0;
        if (bodyModelType == Type.JSON) {
            armor.bodyItemRenderType = 1;
        } else if (bodyModelType == Type.OBJ) {
            armor.bodyItemRenderType = 2;
        }

        armor.bodyItemCustomModelName = ((Model)Objects.requireNonNull((Model)this.bodyItemRenderType.getSelectedItem())).getReadableName();
        Model.Type leggingsModelType = ((Model)Objects.requireNonNull((Model)this.leggingsItemRenderType.getSelectedItem())).getType();
        armor.leggingsItemRenderType = 0;
        if (leggingsModelType == Type.JSON) {
            armor.leggingsItemRenderType = 1;
        } else if (leggingsModelType == Type.OBJ) {
            armor.leggingsItemRenderType = 2;
        }

        armor.leggingsItemCustomModelName = ((Model)Objects.requireNonNull((Model)this.leggingsItemRenderType.getSelectedItem())).getReadableName();
        Model.Type bootsModelType = ((Model)Objects.requireNonNull((Model)this.bootsItemRenderType.getSelectedItem())).getType();
        armor.bootsItemRenderType = 0;
        if (bootsModelType == Type.JSON) {
            armor.bootsItemRenderType = 1;
        } else if (bootsModelType == Type.OBJ) {
            armor.bootsItemRenderType = 2;
        }

        armor.bootsItemCustomModelName = ((Model)Objects.requireNonNull((Model)this.bootsItemRenderType.getSelectedItem())).getReadableName();
        armor.helmetOverlayTexture = this.overlayTextureHelmet.getTextureHolder();
        armor.helmetIsDyeable = this.isDyeableHelmet.isSelected();
        armor.helmetDefaultColor = this.defaultColorHelmet.getColor();
        armor.helmetCanWaterRemoveColor = this.canWaterRemoveColorHelmet.isSelected();
        armor.helmetCanLavaRemoveColor = this.canLavaRemoveColorHelmet.isSelected();
        armor.helmetCanPowderSnowRemoveColor = this.canPowderSnowRemoveColorHelmet.isSelected();
        armor.bodyOverlayTexture = this.overlayTextureBody.getTextureHolder();
        armor.bodyIsDyeable = this.isDyeableBody.isSelected();
        armor.bodyDefaultColor = this.defaultColorBody.getColor();
        armor.bodyCanWaterRemoveColor = this.canWaterRemoveColorBody.isSelected();
        armor.bodyCanLavaRemoveColor = this.canLavaRemoveColorBody.isSelected();
        armor.bodyCanPowderSnowRemoveColor = this.canPowderSnowRemoveColorBody.isSelected();
        armor.leggingsOverlayTexture = this.overlayTextureLeggings.getTextureHolder();
        armor.leggingsIsDyeable = this.isDyeableLeggings.isSelected();
        armor.leggingsDefaultColor = this.defaultColorLeggings.getColor();
        armor.leggingsCanWaterRemoveColor = this.canWaterRemoveColorLeggings.isSelected();
        armor.leggingsCanLavaRemoveColor = this.canLavaRemoveColorLeggings.isSelected();
        armor.leggingsCanPowderSnowRemoveColor = this.canPowderSnowRemoveColorLeggings.isSelected();
        armor.bootsOverlayTexture = this.overlayTextureBoots.getTextureHolder();
        armor.bootsIsDyeable = this.isDyeableBoots.isSelected();
        armor.bootsDefaultColor = this.defaultColorBoots.getColor();
        armor.bootsCanWaterRemoveColor = this.canWaterRemoveColorBoots.isSelected();
        armor.bootsCanLavaRemoveColor = this.canLavaRemoveColorBoots.isSelected();
        armor.bootsCanPowderSnowRemoveColor = this.canPowderSnowRemoveColorBoots.isSelected();
        return armor;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
