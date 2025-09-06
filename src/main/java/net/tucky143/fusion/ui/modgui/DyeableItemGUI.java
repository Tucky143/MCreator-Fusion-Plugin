//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.ui.modgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.mcreator.ui.validation.validators.TextureSelectionButtonValidator;
import net.tucky143.fusion.elements.DyeableItem;
import net.mcreator.blockly.data.Dependency;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.ModElementType;
import net.mcreator.element.parts.ProjectileEntry;
import net.mcreator.element.types.GUI;
import net.mcreator.element.types.Item;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JColor;
import net.mcreator.ui.component.JStringListField;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.minecraft.DataListComboBox;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.minecraft.TabListField;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.minecraft.states.item.JItemPropertiesStatesList;
import net.mcreator.ui.modgui.ItemGUI;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.LogicProcedureSelector;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.procedure.StringListProcedureSelector;
import net.mcreator.ui.procedure.AbstractProcedureSelector.Side;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.elements.VariableTypeLoader.BuiltInTypes;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Model.Type;
import org.jetbrains.annotations.Nullable;

public class DyeableItemGUI extends ModElementGUI<DyeableItem> {
    private TextureSelectionButton texture;
    private StringListProcedureSelector specialInformation;
    private final JSpinner stackSize = new JSpinner(new SpinnerNumberModel(64, 0, 64, 1));
    private final VTextField name = new VTextField(20);
    private final JComboBox<String> rarity = new JComboBox<>(new String[]{"COMMON", "UNCOMMON", "RARE", "EPIC"});
    private final MCItemHolder recipeRemainder;
    private final JSpinner enchantability;
    private final JSpinner useDuration;
    private final JSpinner toolType;
    private final JSpinner damageCount;
    private final JCheckBox immuneToFire;
    private final JCheckBox destroyAnyBlock;
    private final JCheckBox stayInGridWhenCrafting;
    private final JCheckBox damageOnCrafting;
    private LogicProcedureSelector glowCondition;
    private final JCheckBox enableRanged;
    private final JCheckBox shootConstantly;
    private final JCheckBox rangedItemChargesPower;
    private ProcedureSelector onRangedItemUsed;
    private ProcedureSelector rangedUseCondition;
    private final DataListComboBox projectile;
    private final TabListField creativeTabs;
    private static final Model normal = new Model.BuiltInModel("Normal");
    private static final Model tool = new Model.BuiltInModel("Tool");
    private static final Model rangedItem = new Model.BuiltInModel("Ranged item");
    public static final Model[] builtinitemmodels;
    private final SearchableComboBox<Model> renderType;
    private JItemPropertiesStatesList customProperties;
    private ProcedureSelector onRightClickedInAir;
    private ProcedureSelector onCrafted;
    private ProcedureSelector onRightClickedOnBlock;
    private ProcedureSelector onEntityHitWith;
    private ProcedureSelector onItemInInventoryTick;
    private ProcedureSelector onItemInUseTick;
    private ProcedureSelector onStoppedUsing;
    private ProcedureSelector onEntitySwing;
    private ProcedureSelector onDroppedByPlayer;
    private ProcedureSelector onFinishUsingItem;
    private final ValidationGroup page1group;
    private final JSpinner damageVsEntity;
    private final JCheckBox enableMeleeDamage;
    private final SearchableComboBox<String> guiBoundTo;
    private final JSpinner inventorySize;
    private final JSpinner inventoryStackSize;
    private final JCheckBox isFood;
    private final JSpinner nutritionalValue;
    private final JSpinner saturation;
    private final JCheckBox isMeat;
    private final JCheckBox isAlwaysEdible;
    private final JComboBox<String> animation;
    private final MCItemHolder eatResultItem;
    private TextureSelectionButton overlayTexture;
    private final JCheckBox isDyeable;
    private final JColor defaultColor;
    private final JCheckBox canWaterRemoveColor;
    private final JCheckBox canLavaRemoveColor;
    private final JCheckBox canPowderSnowRemoveColor;

    public DyeableItemGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.recipeRemainder = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.enchantability = new JSpinner(new SpinnerNumberModel(0, -100, 128000, 1));
        this.useDuration = new JSpinner(new SpinnerNumberModel(0, -100, 128000, 1));
        this.toolType = new JSpinner(new SpinnerNumberModel((double)1.0F, (double)-100.0F, (double)128000.0F, 0.1));
        this.damageCount = new JSpinner(new SpinnerNumberModel(0, 0, 128000, 1));
        this.immuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.destroyAnyBlock = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.stayInGridWhenCrafting = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.damageOnCrafting = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.enableRanged = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.shootConstantly = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.rangedItemChargesPower = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.projectile = new DataListComboBox(this.mcreator);
        this.creativeTabs = new TabListField(this.mcreator);
        this.renderType = new SearchableComboBox<>(builtinitemmodels);
        this.page1group = new ValidationGroup();
        this.damageVsEntity = new JSpinner(new SpinnerNumberModel((double)0.0F, (double)0.0F, (double)128000.0F, 0.1));
        this.enableMeleeDamage = new JCheckBox();
        this.guiBoundTo = new SearchableComboBox<>();
        this.inventorySize = new JSpinner(new SpinnerNumberModel(9, 0, 256, 1));
        this.inventoryStackSize = new JSpinner(new SpinnerNumberModel(64, 1, 1024, 1));
        this.isFood = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.nutritionalValue = new JSpinner(new SpinnerNumberModel(4, -1000, 1000, 1));
        this.saturation = new JSpinner(new SpinnerNumberModel(0.3, (double)-1000.0F, (double)1000.0F, 0.1));
        this.isMeat = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.isAlwaysEdible = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.animation = new JComboBox<>(new String[]{"none", "eat", "block", "bow", "crossbow", "drink", "spear"});
        this.eatResultItem = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.isDyeable = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.defaultColor = new JColor(this.mcreator, false, false);
        this.canWaterRemoveColor = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canLavaRemoveColor = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.canPowderSnowRemoveColor = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        this.onRightClickedInAir = new ProcedureSelector(this.withEntry("item/when_right_clicked"), this.mcreator, L10N.t("elementgui.common.event_right_clicked_air", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onCrafted = new ProcedureSelector(this.withEntry("item/on_crafted"), this.mcreator, L10N.t("elementgui.common.event_on_crafted", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onRightClickedOnBlock = (new ProcedureSelector(this.withEntry("item/when_right_clicked_block"), this.mcreator, L10N.t("elementgui.common.event_right_clicked_block", new Object[0]), BuiltInTypes.ACTIONRESULTTYPE, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack/direction:direction/blockstate:blockstate"))).makeReturnValueOptional();
        this.onEntityHitWith = new ProcedureSelector(this.withEntry("item/when_entity_hit"), this.mcreator, L10N.t("elementgui.item.event_entity_hit", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/sourceentity:entity/itemstack:itemstack"));
        this.onItemInInventoryTick = new ProcedureSelector(this.withEntry("item/inventory_tick"), this.mcreator, L10N.t("elementgui.item.event_inventory_tick", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack/slot:number"));
        this.onItemInUseTick = new ProcedureSelector(this.withEntry("item/hand_tick"), this.mcreator, L10N.t("elementgui.item.event_hand_tick", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack/slot:number"));
        this.onStoppedUsing = new ProcedureSelector(this.withEntry("item/when_stopped_using"), this.mcreator, L10N.t("elementgui.item.event_stopped_using", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack/time:number"));
        this.onEntitySwing = new ProcedureSelector(this.withEntry("item/when_entity_swings"), this.mcreator, L10N.t("elementgui.item.event_entity_swings", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onDroppedByPlayer = new ProcedureSelector(this.withEntry("item/on_dropped"), this.mcreator, L10N.t("elementgui.item.event_on_dropped", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onFinishUsingItem = new ProcedureSelector(this.withEntry("item/when_stopped_using"), this.mcreator, L10N.t("elementgui.item.player_useitem_finish", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onRangedItemUsed = (new ProcedureSelector(this.withEntry("item/when_used"), this.mcreator, L10N.t("elementgui.item.event_on_use", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"))).makeInline();
        this.specialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), this.mcreator, L10N.t("elementgui.common.special_information", new Object[0]), Side.CLIENT, new JStringListField(this.mcreator, (Function)null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));
        this.glowCondition = new LogicProcedureSelector(this.withEntry("item/glowing_effect"), this.mcreator, L10N.t("elementgui.item.glowing_effect", new Object[0]), Side.CLIENT, L10N.checkbox("elementgui.common.enable", new Object[0]), 160, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.rangedUseCondition = (new ProcedureSelector(this.withEntry("item/ranged_use_condition"), this.mcreator, L10N.t("elementgui.item.can_use_ranged", new Object[0]), BuiltInTypes.LOGIC, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"))).makeInline();
        this.customProperties = new JItemPropertiesStatesList(this.mcreator, this);
        this.customProperties.setPreferredSize(new Dimension(0, 0));
        this.guiBoundTo.addActionListener((e) -> {
            if (!this.isEditingMode()) {
                String selected = (String)this.guiBoundTo.getSelectedItem();
                if (selected != null) {
                    ModElement element = this.mcreator.getWorkspace().getModElementByName(selected);
                    if (element != null) {
                        GeneratableElement generatableElement = element.getGeneratableElement();
                        if (generatableElement instanceof GUI) {
                            this.inventorySize.setValue(((GUI)generatableElement).getMaxSlotID() + 1);
                        }
                    }
                }
            }

        });
        this.useDuration.addChangeListener((change) -> this.onStoppedUsing.setEnabled((Integer)this.useDuration.getValue() > 0));
        JPanel pane2 = new JPanel(new BorderLayout(10, 10));
        JPanel cipp = new JPanel(new BorderLayout(10, 10));
        JPanel pane3 = new JPanel(new BorderLayout(10, 10));
        JPanel foodProperties = new JPanel(new BorderLayout(10, 10));
        JPanel advancedProperties = new JPanel(new BorderLayout(10, 10));
        JPanel rangedPanel = new JPanel(new BorderLayout(10, 10));
        JPanel pane4 = new JPanel(new BorderLayout(10, 10));
        this.texture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.texture.setOpaque(false);
        JPanel destal2 = new JPanel(new BorderLayout(0, 5));
        destal2.setOpaque(false);
        destal2.add("Center", PanelUtils.northAndCenterElement(this.glowCondition, this.specialInformation, 0, 5));
        ComponentUtils.deriveFont(this.renderType, 16.0F);
        JPanel rent = new JPanel();
        rent.setLayout(new BoxLayout(rent, 3));
        rent.setOpaque(false);
        rent.add(PanelUtils.join(new Component[]{HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])), PanelUtils.join(new Component[]{this.renderType})}));
        this.renderType.setPreferredSize(new Dimension(350, 42));
        this.renderType.setRenderer(new ModelComboBoxRenderer());
        rent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.item.item_3d_model", new Object[0]), 0, 0, this.getFont().deriveFont(12.0F), Theme.current().getForegroundColor()));
        this.overlayTexture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.overlayTexture.setOpaque(false);
        JPanel texturesPanel = new JPanel(new BorderLayout());
        texturesPanel.setOpaque(false);
        texturesPanel.add(ComponentUtils.squareAndBorder(this.texture, L10N.t("elementgui.item.texture", new Object[0])), "West");
        texturesPanel.add(ComponentUtils.squareAndBorder(this.overlayTexture, L10N.t("elementgui.dyeable.common.overlay_texture", new Object[0])), "East");
        destal2.add("North", PanelUtils.totalCenterInPanel(PanelUtils.westAndCenterElement(texturesPanel, rent)));
        JPanel sbbp2 = new JPanel(new BorderLayout());
        sbbp2.setOpaque(false);
        sbbp2.add("West", destal2);
        pane2.add("Center", PanelUtils.totalCenterInPanel(PanelUtils.centerInPanel(sbbp2)));
        pane2.setOpaque(false);
        cipp.setOpaque(false);
        cipp.add("Center", this.customProperties);
        JPanel subpane2 = new JPanel(new GridLayout(15, 2, 2, 2));
        ComponentUtils.deriveFont(this.name, 16.0F);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/gui_name"), L10N.label("elementgui.common.name_in_gui", new Object[0])));
        subpane2.add(this.name);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/rarity"), L10N.label("elementgui.common.rarity", new Object[0])));
        subpane2.add(this.rarity);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/creative_tabs"), L10N.label("elementgui.common.creative_tabs", new Object[0])));
        subpane2.add(this.creativeTabs);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/stack_size"), L10N.label("elementgui.common.max_stack_size", new Object[0])));
        subpane2.add(this.stackSize);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/enchantability"), L10N.label("elementgui.common.enchantability", new Object[0])));
        subpane2.add(this.enchantability);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/destroy_speed"), L10N.label("elementgui.item.destroy_speed", new Object[0])));
        subpane2.add(this.toolType);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/damage_vs_entity"), L10N.label("elementgui.item.damage_vs_entity", new Object[0])));
        subpane2.add(PanelUtils.westAndCenterElement(this.enableMeleeDamage, this.damageVsEntity));
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/number_of_uses"), L10N.label("elementgui.item.number_of_uses", new Object[0])));
        subpane2.add(this.damageCount);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/immune_to_fire"), L10N.label("elementgui.item.is_immune_to_fire", new Object[0])));
        subpane2.add(this.immuneToFire);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/can_destroy_any_block"), L10N.label("elementgui.item.can_destroy_any_block", new Object[0])));
        subpane2.add(this.destroyAnyBlock);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/container_item"), L10N.label("elementgui.item.container_item", new Object[0])));
        subpane2.add(this.stayInGridWhenCrafting);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/container_item_damage"), L10N.label("elementgui.item.container_item_damage", new Object[0])));
        subpane2.add(this.damageOnCrafting);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/recipe_remainder"), L10N.label("elementgui.item.recipe_remainder", new Object[0])));
        subpane2.add(PanelUtils.centerInPanel(this.recipeRemainder));
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/animation"), L10N.label("elementgui.item.item_animation", new Object[0])));
        subpane2.add(this.animation);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/use_duration"), L10N.label("elementgui.item.use_duration", new Object[0])));
        subpane2.add(this.useDuration);
        this.enchantability.setOpaque(false);
        this.useDuration.setOpaque(false);
        this.toolType.setOpaque(false);
        this.damageCount.setOpaque(false);
        this.immuneToFire.setOpaque(false);
        this.destroyAnyBlock.setOpaque(false);
        this.stayInGridWhenCrafting.setOpaque(false);
        this.damageOnCrafting.setOpaque(false);
        subpane2.setOpaque(false);
        pane3.setOpaque(false);
        pane3.add("Center", PanelUtils.totalCenterInPanel(subpane2));
        JPanel foodSubpane = new JPanel(new GridLayout(6, 2, 2, 2));
        foodSubpane.setOpaque(false);
        this.isFood.setOpaque(false);
        this.isMeat.setOpaque(false);
        this.isAlwaysEdible.setOpaque(false);
        this.nutritionalValue.setOpaque(false);
        this.saturation.setOpaque(false);
        this.isFood.addActionListener((e) -> {
            this.updateFoodPanel();
            if (!this.isEditingMode()) {
                this.animation.setSelectedItem("eat");
                this.useDuration.setValue(32);
            }

        });
        this.updateFoodPanel();
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/is_food"), L10N.label("elementgui.item.is_food", new Object[0])));
        foodSubpane.add(this.isFood);
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/nutritional_value"), L10N.label("elementgui.item.nutritional_value", new Object[0])));
        foodSubpane.add(this.nutritionalValue);
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/saturation"), L10N.label("elementgui.item.saturation", new Object[0])));
        foodSubpane.add(this.saturation);
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/result_item"), L10N.label("elementgui.item.eating_result", new Object[0])));
        foodSubpane.add(PanelUtils.centerInPanel(this.eatResultItem));
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/is_meat"), L10N.label("elementgui.item.is_meat", new Object[0])));
        foodSubpane.add(this.isMeat);
        foodSubpane.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/always_edible"), L10N.label("elementgui.item.is_edible", new Object[0])));
        foodSubpane.add(this.isAlwaysEdible);
        foodProperties.add("Center", PanelUtils.totalCenterInPanel(foodSubpane));
        foodProperties.setOpaque(false);
        advancedProperties.setOpaque(false);
        JPanel events = new JPanel(new GridLayout(4, 3, 5, 5));
        events.setOpaque(false);
        events.add(this.onRightClickedInAir);
        events.add(this.onRightClickedOnBlock);
        events.add(this.onCrafted);
        events.add(this.onEntityHitWith);
        events.add(this.onItemInInventoryTick);
        events.add(this.onItemInUseTick);
        events.add(this.onStoppedUsing);
        events.add(this.onEntitySwing);
        events.add(this.onDroppedByPlayer);
        events.add(this.onFinishUsingItem);
        pane4.add("Center", PanelUtils.totalCenterInPanel(events));
        pane4.setOpaque(false);
        JPanel inventoryProperties = new JPanel(new GridLayout(3, 2, 35, 2));
        inventoryProperties.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.common.page_inventory", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));
        inventoryProperties.setOpaque(false);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/bind_gui"), L10N.label("elementgui.item.bind_gui", new Object[0])));
        inventoryProperties.add(this.guiBoundTo);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/inventory_size"), L10N.label("elementgui.item.inventory_size", new Object[0])));
        inventoryProperties.add(this.inventorySize);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/inventory_stack_size"), L10N.label("elementgui.common.max_stack_size", new Object[0])));
        inventoryProperties.add(this.inventoryStackSize);
        this.updateRangedPanel();
        JPanel rangedProperties = new JPanel(new GridLayout(4, 2, 2, 2));
        rangedProperties.setOpaque(false);
        rangedProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/enable_ranged_item"), L10N.label("elementgui.item.enable_ranged_item", new Object[0])));
        this.enableRanged.setOpaque(false);
        this.enableRanged.addActionListener((e) -> this.updateRangedPanel());
        rangedProperties.add(this.enableRanged);
        rangedProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/projectile"), L10N.label("elementgui.item.projectile", new Object[0])));
        rangedProperties.add(this.projectile);
        rangedProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/shoot_constantly"), L10N.label("elementgui.item.shoot_constantly", new Object[0])));
        this.shootConstantly.setOpaque(false);
        rangedProperties.add(this.shootConstantly);
        rangedProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/charges_power"), L10N.label("elementgui.item.charges_power", new Object[0])));
        this.rangedItemChargesPower.setOpaque(false);
        rangedProperties.add(this.rangedItemChargesPower);
        this.shootConstantly.addActionListener((e) -> {
            this.rangedItemChargesPower.setEnabled(!this.shootConstantly.isSelected());
            if (this.shootConstantly.isSelected()) {
                this.rangedItemChargesPower.setSelected(false);
            }

        });
        JPanel rangedTriggers = new JPanel(new GridLayout(2, 1, 2, 2));
        rangedTriggers.setOpaque(false);
        rangedTriggers.add(this.rangedUseCondition);
        rangedTriggers.add(this.onRangedItemUsed);
        rangedPanel.setOpaque(false);
        rangedPanel.add("Center", PanelUtils.centerAndSouthElement(rangedProperties, rangedTriggers));
        rangedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.item.ranged_properties", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));
        advancedProperties.add("Center", PanelUtils.totalCenterInPanel(PanelUtils.centerAndEastElement(PanelUtils.pullElementUp(inventoryProperties), rangedPanel, 10, 10)));
        this.texture.setValidator(new TextureSelectionButtonValidator(this.texture));
        this.page1group.addValidationElement(this.texture);
        this.name.setValidator(new TextFieldValidator(this.name, L10N.t("elementgui.item.error_item_needs_name", new Object[0])));
        this.name.enableRealtimeValidation();
        this.isDyeable.setOpaque(false);
        this.isDyeable.setSelected(true);
        this.isDyeable.addActionListener((e) -> this.updateDyePanel());
        this.defaultColor.setColor(Color.WHITE);
        JPanel dyePane = new JPanel(new BorderLayout());
        dyePane.setOpaque(false);
        JPanel dyeProperties = new JPanel(new GridLayout(5, 2, 2, 3));
        dyeProperties.setOpaque(false);
        dyeProperties.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
        dyeProperties.add(L10N.label("elementgui.dyeable.common.is_dyeable", new Object[0]));
        dyeProperties.add(this.isDyeable);
        dyeProperties.add(L10N.label("elementgui.dyeable.common.default_color", new Object[0]));
        dyeProperties.add(this.defaultColor);
        this.canWaterRemoveColor.setOpaque(false);
        this.canLavaRemoveColor.setOpaque(false);
        this.canPowderSnowRemoveColor.setOpaque(false);
        dyeProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_water"), L10N.label("elementgui.dyeable.common.can_water_remove_color", new Object[0])));
        dyeProperties.add(this.canWaterRemoveColor);
        dyeProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_lava"), L10N.label("elementgui.dyeable.common.can_lava_remove_color", new Object[0])));
        dyeProperties.add(this.canLavaRemoveColor);
        dyeProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("dyeable/remove_powder_snow"), L10N.label("elementgui.dyeable.common.can_powder_snow_remove_color", new Object[0])));
        dyeProperties.add(this.canPowderSnowRemoveColor);
        dyePane.add(PanelUtils.totalCenterInPanel(dyeProperties));
        this.addPage(L10N.t("elementgui.common.page_visual", new Object[0]), pane2);
        this.addPage(L10N.t("elementgui.item.page_item_states", new Object[0]), cipp, false);
        this.addPage(L10N.t("elementgui.common.page_properties", new Object[0]), pane3);
        this.addPage(L10N.t("elementgui.item.food_properties", new Object[0]), foodProperties);
        this.addPage(L10N.t("elementgui.common.page_advanced_properties", new Object[0]), advancedProperties);
        this.addPage(L10N.t("elementgui.dyeable.common.dye", new Object[0]), dyePane);
        this.addPage(L10N.t("elementgui.common.page_triggers", new Object[0]), pane4);
        if (!this.isEditingMode()) {
            String readableNameFromModElement = StringUtils.machineToReadableName(this.modElement.getName());
            this.name.setText(readableNameFromModElement);
        }

        this.updateFoodPanel();
        this.updateDyePanel();
    }

    private void updateDyePanel() {
        this.defaultColor.setEnabled(this.isDyeable.isSelected());
        this.canWaterRemoveColor.setEnabled(this.isDyeable.isSelected());
        this.canLavaRemoveColor.setEnabled(this.isDyeable.isSelected());
        this.canPowderSnowRemoveColor.setEnabled(this.isDyeable.isSelected());
    }

    private void updateFoodPanel() {
        if (this.isFood.isSelected()) {
            this.nutritionalValue.setEnabled(true);
            this.saturation.setEnabled(true);
            this.isMeat.setEnabled(true);
            this.isAlwaysEdible.setEnabled(true);
            this.eatResultItem.setEnabled(true);
        } else {
            this.nutritionalValue.setEnabled(false);
            this.saturation.setEnabled(false);
            this.isMeat.setEnabled(false);
            this.isAlwaysEdible.setEnabled(false);
            this.eatResultItem.setEnabled(false);
        }

    }

    private void updateRangedPanel() {
        if (this.enableRanged.isSelected()) {
            this.shootConstantly.setEnabled(true);
            this.rangedItemChargesPower.setEnabled(!this.shootConstantly.isSelected());
            this.projectile.setEnabled(true);
            this.onRangedItemUsed.setEnabled(true);
            this.rangedUseCondition.setEnabled(true);
            if (!this.isEditingMode()) {
                if ((Integer)this.useDuration.getValue() == 0) {
                    this.useDuration.setValue(72000);
                }

                if (this.renderType.getSelectedItem() == normal) {
                    this.renderType.setSelectedItem(rangedItem);
                }

                if ("none".equals(this.animation.getSelectedItem())) {
                    this.animation.setSelectedItem("bow");
                }
            }
        } else {
            this.shootConstantly.setEnabled(false);
            this.rangedItemChargesPower.setEnabled(false);
            this.projectile.setEnabled(false);
            this.onRangedItemUsed.setEnabled(false);
            this.rangedUseCondition.setEnabled(false);
            if (!this.isEditingMode()) {
                if ((Integer)this.useDuration.getValue() == 72000) {
                    this.useDuration.setValue(0);
                }

                if (this.renderType.getSelectedItem() == rangedItem) {
                    this.renderType.setSelectedItem(normal);
                }

                this.animation.setSelectedItem("none");
            }
        }

    }

    public void reloadDataLists() {
        super.reloadDataLists();
        this.onRightClickedInAir.refreshListKeepSelected();
        this.onCrafted.refreshListKeepSelected();
        this.onRightClickedOnBlock.refreshListKeepSelected();
        this.onEntityHitWith.refreshListKeepSelected();
        this.onItemInInventoryTick.refreshListKeepSelected();
        this.onItemInUseTick.refreshListKeepSelected();
        this.onStoppedUsing.refreshListKeepSelected();
        this.onEntitySwing.refreshListKeepSelected();
        this.onDroppedByPlayer.refreshListKeepSelected();
        this.onFinishUsingItem.refreshListKeepSelected();
        this.specialInformation.refreshListKeepSelected();
        this.glowCondition.refreshListKeepSelected();
        this.onRangedItemUsed.refreshListKeepSelected();
        this.rangedUseCondition.refreshListKeepSelected();
        ComboBoxUtil.updateComboBoxContents(this.projectile, ElementUtil.loadArrowProjectiles(this.mcreator.getWorkspace()));
        this.customProperties.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(this.renderType, ListUtils.merge(Arrays.asList(ItemGUI.builtinitemmodels), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> el.getType() == Type.JSON || el.getType() == Type.OBJ).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.guiBoundTo, ListUtils.merge(Collections.singleton("<NONE>"), (Collection)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> var.getType() == ModElementType.GUI).map(ModElement::getName).collect(Collectors.toList())), "<NONE>");
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (page == 0) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
        } else if (page == 1) {
            return this.customProperties.getValidationResult();
        } else {
            return (AggregatedValidationResult)(page == 2 ? new AggregatedValidationResult(new IValidable[]{this.name}) : new AggregatedValidationResult.PASS());
        }
    }

    protected void openInEditingMode(DyeableItem dyeableItem) {
        this.name.setText(dyeableItem.name);
        this.rarity.setSelectedItem(dyeableItem.rarity);
        this.texture.setTexture(dyeableItem.texture);
        this.onRightClickedInAir.setSelectedProcedure(dyeableItem.onRightClickedInAir);
        this.onRightClickedOnBlock.setSelectedProcedure(dyeableItem.onRightClickedOnBlock);
        this.onCrafted.setSelectedProcedure(dyeableItem.onCrafted);
        this.onEntityHitWith.setSelectedProcedure(dyeableItem.onEntityHitWith);
        this.onItemInInventoryTick.setSelectedProcedure(dyeableItem.onItemInInventoryTick);
        this.onItemInUseTick.setSelectedProcedure(dyeableItem.onItemInUseTick);
        this.onStoppedUsing.setSelectedProcedure(dyeableItem.onStoppedUsing);
        this.onEntitySwing.setSelectedProcedure(dyeableItem.onEntitySwing);
        this.onDroppedByPlayer.setSelectedProcedure(dyeableItem.onDroppedByPlayer);
        this.creativeTabs.setListElements(dyeableItem.creativeTabs);
        this.stackSize.setValue(dyeableItem.stackSize);
        this.enchantability.setValue(dyeableItem.enchantability);
        this.toolType.setValue(dyeableItem.toolType);
        this.useDuration.setValue(dyeableItem.useDuration);
        this.damageCount.setValue(dyeableItem.damageCount);
        this.recipeRemainder.setBlock(dyeableItem.recipeRemainder);
        this.immuneToFire.setSelected(dyeableItem.immuneToFire);
        this.destroyAnyBlock.setSelected(dyeableItem.destroyAnyBlock);
        this.stayInGridWhenCrafting.setSelected(dyeableItem.stayInGridWhenCrafting);
        this.damageOnCrafting.setSelected(dyeableItem.damageOnCrafting);
        this.specialInformation.setSelectedProcedure(dyeableItem.specialInformation);
        this.glowCondition.setSelectedProcedure(dyeableItem.glowCondition);
        this.damageVsEntity.setValue(dyeableItem.damageVsEntity);
        this.enableMeleeDamage.setSelected(dyeableItem.enableMeleeDamage);
        this.guiBoundTo.setSelectedItem(dyeableItem.guiBoundTo);
        this.inventorySize.setValue(dyeableItem.inventorySize);
        this.inventoryStackSize.setValue(dyeableItem.inventoryStackSize);
        this.isFood.setSelected(dyeableItem.isFood);
        this.isMeat.setSelected(dyeableItem.isMeat);
        this.isAlwaysEdible.setSelected(dyeableItem.isAlwaysEdible);
        this.onFinishUsingItem.setSelectedProcedure(dyeableItem.onFinishUsingItem);
        this.nutritionalValue.setValue(dyeableItem.nutritionalValue);
        this.saturation.setValue(dyeableItem.saturation);
        this.animation.setSelectedItem(dyeableItem.animation);
        this.eatResultItem.setBlock(dyeableItem.eatResultItem);
        this.enableRanged.setSelected(dyeableItem.enableRanged);
        this.shootConstantly.setSelected(dyeableItem.shootConstantly);
        this.rangedItemChargesPower.setSelected(dyeableItem.rangedItemChargesPower);
        this.projectile.setSelectedItem(dyeableItem.projectile);
        this.rangedUseCondition.setSelectedProcedure(dyeableItem.rangedUseCondition);
        this.onRangedItemUsed.setSelectedProcedure(dyeableItem.onRangedItemUsed);
        this.overlayTexture.setTexture(dyeableItem.overlayTexture);
        this.isDyeable.setSelected(dyeableItem.isDyeable);
        this.defaultColor.setColor(dyeableItem.defaultColor);
        this.canWaterRemoveColor.setSelected(dyeableItem.canWaterRemoveColor);
        this.canLavaRemoveColor.setSelected(dyeableItem.canLavaRemoveColor);
        this.canPowderSnowRemoveColor.setSelected(dyeableItem.canPowderSnowRemoveColor);
        this.updateFoodPanel();
        this.updateRangedPanel();
        this.onStoppedUsing.setEnabled((Integer)this.useDuration.getValue() > 0);
        Model model = dyeableItem.getItemModel();
        if (model != null) {
            this.renderType.setSelectedItem(model);
        }

        this.customProperties.setProperties(dyeableItem.customProperties);
        this.customProperties.setStates(dyeableItem.states);
        this.updateDyePanel();
    }

    public DyeableItem getElementFromGUI() {
        DyeableItem item = new DyeableItem(this.modElement);
        item.name = this.name.getText();
        item.rarity = (String)this.rarity.getSelectedItem();
        item.creativeTabs = this.creativeTabs.getListElements();
        item.stackSize = (Integer)this.stackSize.getValue();
        item.enchantability = (Integer)this.enchantability.getValue();
        item.useDuration = (Integer)this.useDuration.getValue();
        item.toolType = (Double)this.toolType.getValue();
        item.damageCount = (Integer)this.damageCount.getValue();
        item.recipeRemainder = this.recipeRemainder.getBlock();
        item.immuneToFire = this.immuneToFire.isSelected();
        item.destroyAnyBlock = this.destroyAnyBlock.isSelected();
        item.stayInGridWhenCrafting = this.stayInGridWhenCrafting.isSelected();
        item.damageOnCrafting = this.damageOnCrafting.isSelected();
        item.specialInformation = this.specialInformation.getSelectedProcedure();
        item.glowCondition = this.glowCondition.getSelectedProcedure();
        item.onRightClickedInAir = this.onRightClickedInAir.getSelectedProcedure();
        item.onRightClickedOnBlock = this.onRightClickedOnBlock.getSelectedProcedure();
        item.onCrafted = this.onCrafted.getSelectedProcedure();
        item.onEntityHitWith = this.onEntityHitWith.getSelectedProcedure();
        item.onItemInInventoryTick = this.onItemInInventoryTick.getSelectedProcedure();
        item.onItemInUseTick = this.onItemInUseTick.getSelectedProcedure();
        item.onStoppedUsing = this.onStoppedUsing.getSelectedProcedure();
        item.onEntitySwing = this.onEntitySwing.getSelectedProcedure();
        item.onDroppedByPlayer = this.onDroppedByPlayer.getSelectedProcedure();
        item.damageVsEntity = (Double)this.damageVsEntity.getValue();
        item.enableMeleeDamage = this.enableMeleeDamage.isSelected();
        item.inventorySize = (Integer)this.inventorySize.getValue();
        item.inventoryStackSize = (Integer)this.inventoryStackSize.getValue();
        item.guiBoundTo = (String)this.guiBoundTo.getSelectedItem();
        item.isFood = this.isFood.isSelected();
        item.nutritionalValue = (Integer)this.nutritionalValue.getValue();
        item.saturation = (Double)this.saturation.getValue();
        item.isMeat = this.isMeat.isSelected();
        item.isAlwaysEdible = this.isAlwaysEdible.isSelected();
        item.animation = (String)this.animation.getSelectedItem();
        item.onFinishUsingItem = this.onFinishUsingItem.getSelectedProcedure();
        item.eatResultItem = this.eatResultItem.getBlock();
        item.enableRanged = this.enableRanged.isSelected();
        item.shootConstantly = this.shootConstantly.isSelected();
        item.rangedItemChargesPower = this.rangedItemChargesPower.isSelected();
        item.projectile = new ProjectileEntry(this.mcreator.getWorkspace(), this.projectile.getSelectedItem());
        item.onRangedItemUsed = this.onRangedItemUsed.getSelectedProcedure();
        item.rangedUseCondition = this.rangedUseCondition.getSelectedProcedure();
        item.overlayTexture = this.overlayTexture.getTextureHolder();
        item.isDyeable = this.isDyeable.isSelected();
        item.defaultColor = this.defaultColor.getColor();
        item.canWaterRemoveColor = this.canWaterRemoveColor.isSelected();
        item.canLavaRemoveColor = this.canLavaRemoveColor.isSelected();
        item.canPowderSnowRemoveColor = this.canPowderSnowRemoveColor.isSelected();
        item.texture = this.texture.getTextureHolder();
        item.renderType = Item.encodeModelType(((Model)Objects.requireNonNull((Model)this.renderType.getSelectedItem())).getType());
        item.customModelName = ((Model)Objects.requireNonNull((Model)this.renderType.getSelectedItem())).getReadableName();
        item.customProperties = this.customProperties.getProperties();
        item.states = this.customProperties.getStates();
        return item;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

    static {
        builtinitemmodels = new Model[]{normal, tool, rangedItem};
    }
}
