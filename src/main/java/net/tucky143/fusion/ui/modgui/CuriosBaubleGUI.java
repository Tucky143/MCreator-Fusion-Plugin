package net.tucky143.fusion.ui.modgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.*;

import net.mcreator.blockly.data.Dependency;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.ModElementType;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.types.GUI;
import net.mcreator.element.types.Item;
import net.mcreator.minecraft.DataListEntry;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JStringListField;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TextureImportDialogs;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.minecraft.DataListComboBox;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.minecraft.states.item.JItemPropertiesStatesList;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.AbstractProcedureSelector;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.procedure.AbstractProcedureSelector.Side;
import net.mcreator.ui.procedure.StringListProcedureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VComboBox;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.validation.validators.TextureSelectionButtonValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.elements.VariableTypeLoader.BuiltInTypes;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Model.Type;
import net.tucky143.fusion.elements.CuriosBauble;
import net.tucky143.fusion.parts.PluginElementTypes;
import org.jetbrains.annotations.Nullable;

public class CuriosBaubleGUI extends ModElementGUI<CuriosBauble> {
    private TextureSelectionButton texture;
    private StringListProcedureSelector specialInformation;
    private final JSpinner stackSize = new JSpinner(new SpinnerNumberModel(64, 0, 64, 1));
    private final VTextField name = new VTextField(20);
    private final JComboBox<String> rarity = new JComboBox(new String[]{"COMMON", "UNCOMMON", "RARE", "EPIC"});
    private final SearchableComboBox<String> slotType = new SearchableComboBox<>();
    private final MCItemHolder recipeRemainder;
    private final JSpinner enchantability;
    private final JSpinner useDuration;
    private final JSpinner toolType;
    private final JSpinner damageCount;
    private final JCheckBox immuneToFire;
    private final JCheckBox destroyAnyBlock;
    private final JCheckBox stayInGridWhenCrafting;
    private final JCheckBox damageOnCrafting;
    private final JCheckBox hasGlow;
    private final JCheckBox addSlot = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox friendlyPigs = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox enderMask = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox hasModel = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox rotateModel = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox translateModel = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private ProcedureSelector glowCondition;
    private final DataListComboBox creativeTab;
    private static final Model normal = new Model.BuiltInModel("Normal");
    private static final Model tool = new Model.BuiltInModel("Tool");
    public static final Model[] builtinitemmodels = new Model[] { normal, tool };
    private final SearchableComboBox<Model> renderType = new SearchableComboBox<>(builtinitemmodels);
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
    private ProcedureSelector curioTick;
    private ProcedureSelector onEquip;
    private ProcedureSelector onUnequip;
    private final ValidationGroup page1group;
    private final JSpinner damageVsEntity;
    private final JCheckBox enableMeleeDamage;
    private final JComboBox<String> guiBoundTo;
    private final JSpinner inventorySize;
    private final JSpinner inventoryStackSize;
    private final JCheckBox isFood;
    private final JSpinner nutritionalValue;
    private final JSpinner saturation;
    private final JSpinner slotAmount = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    private final JCheckBox isMeat;
    private final JCheckBox isAlwaysEdible;
    private final JComboBox<String> animation;
    private final MCItemHolder eatResultItem;
    private final SearchableComboBox<Model> baubleModel;
    private final VComboBox<String> baubleModelTexture;
    private final Model adefault;

    public CuriosBaubleGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.recipeRemainder = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.enchantability = new JSpinner(new SpinnerNumberModel(0, -100, 128000, 1));
        this.useDuration = new JSpinner(new SpinnerNumberModel(0, -100, 128000, 1));
        this.toolType = new JSpinner(new SpinnerNumberModel(1.0, -100.0, 128000.0, 0.1));
        this.damageCount = new JSpinner(new SpinnerNumberModel(0, 0, 128000, 1));
        this.immuneToFire = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.destroyAnyBlock = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.stayInGridWhenCrafting = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.damageOnCrafting = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.adefault = new Model.BuiltInModel("Default");
        this.baubleModel = new SearchableComboBox(new Model[]{this.adefault});
        this.hasGlow = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.creativeTab = new DataListComboBox(this.mcreator);
        this.baubleModelTexture = new SearchableComboBox();
        this.page1group = new ValidationGroup();
        this.damageVsEntity = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 128000.0, 0.1));
        this.enableMeleeDamage = new JCheckBox();
        this.guiBoundTo = new JComboBox();
        this.inventorySize = new JSpinner(new SpinnerNumberModel(9, 0, 256, 1));
        this.inventoryStackSize = new JSpinner(new SpinnerNumberModel(64, 1, 1024, 1));
        this.isFood = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.nutritionalValue = new JSpinner(new SpinnerNumberModel(4, -1000, 1000, 1));
        this.saturation = new JSpinner(new SpinnerNumberModel(0.3, -1000.0, 1000.0, 0.1));
        this.isMeat = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.isAlwaysEdible = L10N.checkbox("elementgui.common.enable", new Object[0]);
        this.animation = new JComboBox(new String[]{"none", "eat", "block", "bow", "crossbow", "drink", "spear"});
        this.eatResultItem = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        this.baubleModelTexture.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        this.baubleModelTexture.setRenderer(new WTextureComboBoxRenderer.TypeTextures(this.mcreator.getWorkspace(), TextureType.ENTITY));
        this.baubleModel.setPreferredSize(new Dimension(400, 42));
        this.baubleModel.setRenderer(new ModelComboBoxRenderer());
        ComponentUtils.deriveFont(this.baubleModel, 16.0F);
        ComponentUtils.deriveFont(this.baubleModelTexture, 16.0F);
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
        this.curioTick = new ProcedureSelector(this.withEntry("curios/curio_tick"), this.mcreator, L10N.t("elementgui.curiosbauble.curio_tick", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onEquip = new ProcedureSelector(this.withEntry("curios/on_equip"), this.mcreator, L10N.t("elementgui.curiosbauble.on_equip", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.onUnequip = new ProcedureSelector(this.withEntry("curios/on_unequip"), this.mcreator, L10N.t("elementgui.curiosbauble.on_unequip", new Object[0]), Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        this.glowCondition = (new ProcedureSelector(this.withEntry("item/condition_glow"), this.mcreator, L10N.t("elementgui.item.condition_glow", new Object[0]), Side.CLIENT, true, BuiltInTypes.LOGIC, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"))).makeInline();
        specialInformation = new StringListProcedureSelector(this.withEntry("item/special_information"), mcreator, L10N.t("elementgui.common.special_information"), AbstractProcedureSelector.Side.CLIENT, new JStringListField(mcreator, null), 0, Dependency.fromString("x:number/y:number/z:number/entity:entity/world:world/itemstack:itemstack"));

        customProperties = new JItemPropertiesStatesList(mcreator, this);
        customProperties.setPreferredSize(new Dimension(0, 0)); // prevent resizing beyond the editor tab

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
        JPanel pane2 = new JPanel(new BorderLayout(10, 10));
        JPanel cipp = new JPanel(new BorderLayout(10, 10));
        JPanel pane3 = new JPanel(new BorderLayout(10, 10));
        JPanel foodProperties = new JPanel(new BorderLayout(10, 10));
        JPanel advancedProperties = new JPanel(new BorderLayout(10, 10));
        JPanel pane4 = new JPanel(new BorderLayout(10, 10));
        this.texture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.ITEM));
        this.texture.setOpaque(false);
        JPanel destal2 = new JPanel(new BorderLayout(0, 10));
        destal2.setOpaque(false);
        JPanel destal = new JPanel(new GridLayout(1, 2, 15, 15));
        destal.setOpaque(false);
        JComponent destal1 = PanelUtils.join(0, new Component[]{HelpUtils.wrapWithHelpButton(this.withEntry("item/glowing_effect"), L10N.label("elementgui.item.glowing_effect", new Object[0])), this.hasGlow, this.glowCondition});
        destal.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/special_information"), L10N.label("elementgui.item.tooltip_tip", new Object[0])));
        destal.add(this.specialInformation);
        this.hasGlow.setOpaque(false);
        this.hasGlow.setSelected(false);
        this.hasGlow.addActionListener((e) -> {
            this.updateGlowElements();
        });
        destal2.add("Center", PanelUtils.northAndCenterElement(destal, destal1, 10, 10));
        ComponentUtils.deriveFont(this.specialInformation, 16.0F);
        ComponentUtils.deriveFont(this.renderType, 16.0F);
        JPanel rent = new JPanel();
        rent.setLayout(new BoxLayout(rent, 3));
        rent.setOpaque(false);
        rent.add(PanelUtils.join(new Component[]{HelpUtils.wrapWithHelpButton(this.withEntry("item/model"), L10N.label("elementgui.common.item_model", new Object[0])), PanelUtils.join(new Component[]{this.renderType})}));
        this.renderType.setPreferredSize(new Dimension(350, 42));
        this.renderType.setRenderer(new ModelComboBoxRenderer());
        rent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR"), 1), L10N.t("elementgui.item.item_3d_model", new Object[0]), 0, 0, this.getFont().deriveFont(12.0F), (Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR")));
        destal2.add("North", PanelUtils.totalCenterInPanel(
                PanelUtils.join(ComponentUtils.squareAndBorder(texture, L10N.t("elementgui.item.texture")), rent)));
        JPanel sbbp2 = new JPanel(new BorderLayout());
        sbbp2.setOpaque(false);
        sbbp2.add("West", destal2);
        pane2.add("Center", PanelUtils.totalCenterInPanel(PanelUtils.centerInPanel(sbbp2)));
        pane2.setOpaque(false);
        cipp.setOpaque(false);
        cipp.add("Center", customProperties);
        JPanel subpane2 = new JPanel(new GridLayout(15, 2, 2, 2));
        ComponentUtils.deriveFont(this.name, 16.0F);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/gui_name"), L10N.label("elementgui.common.name_in_gui", new Object[0])));
        subpane2.add(this.name);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/rarity"), L10N.label("elementgui.common.rarity", new Object[0])));
        subpane2.add(this.rarity);
        subpane2.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/creative_tab"), L10N.label("elementgui.common.creative_tab", new Object[0])));
        subpane2.add(this.creativeTab);
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
        JPanel events = new JPanel(new GridLayout(5, 3, 10, 10));
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
        events.add(this.curioTick);
        events.add(this.onEquip);
        events.add(this.onUnequip);
        pane4.add("Center", PanelUtils.totalCenterInPanel(events));
        pane4.setOpaque(false);
        JPanel inventoryProperties = new JPanel(new GridLayout(3, 2, 35, 2));
        inventoryProperties.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR"), 1), L10N.t("elementgui.common.page_inventory", new Object[0]), 4, 0, this.getFont(), (Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR")));
        inventoryProperties.setOpaque(false);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/bind_gui"), L10N.label("elementgui.item.bind_gui", new Object[0])));
        inventoryProperties.add(this.guiBoundTo);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/inventory_size"), L10N.label("elementgui.item.inventory_size", new Object[0])));
        inventoryProperties.add(this.inventorySize);
        inventoryProperties.add(HelpUtils.wrapWithHelpButton(this.withEntry("item/inventory_stack_size"), L10N.label("elementgui.common.max_stack_size", new Object[0])));
        inventoryProperties.add(this.inventoryStackSize);
        advancedProperties.add("Center", PanelUtils.totalCenterInPanel(inventoryProperties));
        this.texture.setValidator(new TextureSelectionButtonValidator(this.texture));
        this.page1group.addValidationElement(this.texture);
        this.name.setValidator(new TextFieldValidator(this.name, L10N.t("elementgui.item.error_item_needs_name", new Object[0])));
        this.name.enableRealtimeValidation();


        JPanel pane5 = new JPanel(new BorderLayout(10, 10));
        JPanel bprops = new JPanel(new GridLayout(10, 2, 2, 2));

        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_type"), L10N.label("elementgui.curiosbauble.slot_type", new Object[0])));
        bprops.add(slotType);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/add_slots"), L10N.label("elementgui.curiosbauble.add_slots", new Object[0])));
        bprops.add(addSlot);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_amount"), L10N.label("elementgui.curiosbauble.slot_amount", new Object[0])));
        bprops.add(slotAmount);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/piglins"), L10N.label("elementgui.curiosbauble.piglins", new Object[0])));
        bprops.add(friendlyPigs);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/endermask"), L10N.label("elementgui.curiosbauble.endermask", new Object[0])));
        bprops.add(enderMask);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/model"), L10N.label("elementgui.curiosbauble.has_model", new Object[0])));
        bprops.add(hasModel);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/java_model"), L10N.label("elementgui.curiosbauble.java_model", new Object[0])));
        bprops.add(baubleModel);

        JButton importmobtexture = new JButton(UIRES.get("18px.add"));
        importmobtexture.setToolTipText(L10N.t("elementgui.ranged_item.bullet_model_tooltip", new Object[0]));
        importmobtexture.setOpaque(false);
        importmobtexture.addActionListener((e) -> {
            TextureImportDialogs.importMultipleTextures(this.mcreator, TextureType.ENTITY);
            this.baubleModelTexture.removeAllItems();
            this.baubleModelTexture.addItem("");
            List<File> textures = this.mcreator.getFolderManager().getTexturesList(TextureType.ENTITY);
            Iterator var3 = textures.iterator();

            while(var3.hasNext()) {
                File element = (File)var3.next();
                if (element.getName().endsWith(".png")) {
                    this.baubleModelTexture.addItem(element.getName());
                }
            }

        });

        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/model_texture"), L10N.label("elementgui.curiosbauble.model_texture", new Object[0])));
        bprops.add(baubleModelTexture, importmobtexture);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/rotate_model"), L10N.label("elementgui.curiosbauble.rotate_model", new Object[0])));
        bprops.add(rotateModel);
        bprops.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/translate_model"), L10N.label("elementgui.curiosbauble.translate_model", new Object[0])));
        bprops.add(translateModel);

        pane5.add("Center", PanelUtils.totalCenterInPanel(bprops));
        pane5.setOpaque(false);
        bprops.setOpaque(false);
        this.addSlot.setOpaque(false);
        this.friendlyPigs.setOpaque(false);
        this.enderMask.setOpaque(false);
        this.hasModel.setOpaque(false);
        this.rotateModel.setOpaque(false);
        this.translateModel.setOpaque(false);

        this.slotAmount.setEnabled(false);
        this.baubleModel.setEnabled(false);
        this.baubleModelTexture.setEnabled(false);
        this.rotateModel.setEnabled(false);
        this.translateModel.setEnabled(false);

        this.slotType.addActionListener((e) -> {
            if (this.slotType.getSelectedItem() != null)
                this.addSlot.setEnabled(this.slotType.getSelectedItem().equals("HEAD") || this.slotType.getSelectedItem().equals("NECKLACE") || this.slotType.getSelectedItem().equals("BACK") || this.slotType.getSelectedItem().equals("BODY") || this.slotType.getSelectedItem().equals("BRACELET") || this.slotType.getSelectedItem().equals("HANDS") || this.slotType.getSelectedItem().equals("RING") || this.slotType.getSelectedItem().equals("BELT") || this.slotType.getSelectedItem().equals("CHARM") || this.slotType.getSelectedItem().equals("CURIO"));
            this.slotAmount.setEnabled(this.addSlot.isSelected() && this.addSlot.isEnabled());
        });

        this.addSlot.addActionListener((e) -> {
            this.slotAmount.setEnabled(this.addSlot.isSelected());
        });

        this.hasModel.addActionListener((e) -> {
            this.baubleModel.setEnabled(this.hasModel.isSelected());
            this.baubleModelTexture.setEnabled(this.hasModel.isSelected());
            this.rotateModel.setEnabled(this.hasModel.isSelected());
            this.translateModel.setEnabled(this.hasModel.isSelected());
        });

        this.baubleModelTexture.setValidator(() -> {
            return this.adefault.equals(this.baubleModel.getSelectedItem()) || this.baubleModelTexture.getSelectedItem() != null && !((String)this.baubleModelTexture.getSelectedItem()).equals("") ? Validator.ValidationResult.PASSED : new Validator.ValidationResult(Validator.ValidationResultType.ERROR, L10N.t("elementgui.ranged_item.error_custom_model_needs_texture", new Object[0]));
        });
        this.page1group.addValidationElement(baubleModelTexture);


        this.addPage(L10N.t("elementgui.common.page_visual", new Object[0]), pane2);
        this.addPage(L10N.t("elementgui.item.page_item_states"), cipp);
        this.addPage(L10N.t("elementgui.common.page_properties", new Object[0]), pane3);
        this.addPage(L10N.t("elementgui.item.food_properties", new Object[0]), foodProperties);
        this.addPage(L10N.t("elementgui.common.page_advanced_properties", new Object[0]), advancedProperties);
        this.addPage(L10N.t("elementgui.curiosbauble.properties", new Object[0]), pane5);
        this.addPage(L10N.t("elementgui.common.page_triggers", new Object[0]), pane4);
        if (!this.isEditingMode()) {
            String readableNameFromModElement = StringUtils.machineToReadableName(this.modElement.getName());
            this.name.setText(readableNameFromModElement);
        }

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

    private void updateGlowElements() {
        this.glowCondition.setEnabled(this.hasGlow.isSelected());
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        this.onRightClickedInAir.refreshListKeepSelected();
        this.onCrafted.refreshListKeepSelected();
        this.onRightClickedOnBlock.refreshListKeepSelected();
        this.onEntityHitWith.refreshListKeepSelected();
        this.onItemInInventoryTick.refreshListKeepSelected();
        this.onItemInUseTick.refreshListKeepSelected();
        this.specialInformation.refreshListKeepSelected();
        this.onStoppedUsing.refreshListKeepSelected();
        this.onEntitySwing.refreshListKeepSelected();
        this.onDroppedByPlayer.refreshListKeepSelected();
        this.customProperties.reloadDataLists();
        this.onFinishUsingItem.refreshListKeepSelected();
        this.curioTick.refreshListKeepSelected();
        this.onEquip.refreshListKeepSelected();
        this.onUnequip.refreshListKeepSelected();
        this.glowCondition.refreshListKeepSelected();
        ComboBoxUtil.updateComboBoxContents(this.creativeTab, ElementUtil.loadAllTabs(this.mcreator.getWorkspace()), new DataListEntry.Dummy("MISC"));
        ComboBoxUtil.updateComboBoxContents(renderType, ListUtils.merge(Arrays.asList(CuriosBaubleGUI.builtinitemmodels), (Collection)Model.getModelsWithTextureMaps(this.mcreator.getWorkspace()).stream().filter((el) -> {
            return el.getType() == Type.JSON || el.getType() == Type.OBJ;
        }).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.guiBoundTo, ListUtils.merge(Collections.singleton("<NONE>"), (Collection)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == ModElementType.GUI;
        }).map(ModElement::getName).collect(Collectors.toList())), "<NONE>");
        ComboBoxUtil.updateComboBoxContents(this.baubleModelTexture, ListUtils.merge(Collections.singleton(""), (Collection)this.mcreator.getFolderManager().getTexturesList(TextureType.ENTITY).stream().map(File::getName).filter((s) -> {
            return s.endsWith(".png");
        }).collect(Collectors.toList())), "");
        ComboBoxUtil.updateComboBoxContents(this.baubleModel, ListUtils.merge(Collections.singletonList(this.adefault), (Collection)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> {
            return el.getType() == Type.JAVA || el.getType() == Type.MCREATOR;
        }).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(this.slotType, ListUtils.merge(List.of("HEAD", "NECKLACE", "BACK", "BODY", "BRACELET", "HANDS", "RING", "BELT", "CHARM", "CURIO"), (List)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.CURIOSSLOT;
        }).map(ModElement::getName).collect(Collectors.toList())));
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("curios_api"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.curiosbauble.needs_api", new Object[0]));
        if (page == 0)
            return new AggregatedValidationResult(page1group);
        else if (page == 1)
            return customProperties.getValidationResult();
        else if (page == 2)
            return new AggregatedValidationResult(name);
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void afterGeneratableElementStored() {
        this.mcreator.getWorkspace().getModElements().stream().forEach((var) -> {
            if (var.getType() == PluginElementTypes.CURIOSSLOT) {
                try {
                    mcreator.getGenerator().generateElement(var.getGeneratableElement(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                var.reinit(mcreator.getWorkspace());
            }
        });
    }

    public void openInEditingMode(CuriosBauble item) {
        this.name.setText(item.name);
        this.baubleModelTexture.setSelectedItem(item.baubleModelTexture);
        Model model = item.getEntityModel();
        if (model != null && model.getType() != null && model.getReadableName() != null) {
            this.baubleModel.setSelectedItem(model);
        }
        this.rarity.setSelectedItem(item.rarity);
        this.slotType.setSelectedItem(item.slotType);
        this.addSlot.setSelected(item.addSlot);
        this.customProperties.setProperties(item.customProperties);
        this.customProperties.setStates(item.states);
        this.slotAmount.setValue(item.slotAmount);
        this.friendlyPigs.setSelected(item.friendlyPigs);
        this.rotateModel.setSelected(item.rotateModel);
        this.translateModel.setSelected(item.translateModel);
        this.hasModel.setSelected(item.hasModel);
        this.enderMask.setSelected(item.enderMask);
        this.texture.setTexture(item.texture);
        this.specialInformation.setSelectedProcedure(item.specialInformation);
        this.onRightClickedInAir.setSelectedProcedure(item.onRightClickedInAir);
        this.onRightClickedOnBlock.setSelectedProcedure(item.onRightClickedOnBlock);
        this.onCrafted.setSelectedProcedure(item.onCrafted);
        this.onEntityHitWith.setSelectedProcedure(item.onEntityHitWith);
        this.onItemInInventoryTick.setSelectedProcedure(item.onItemInInventoryTick);
        this.onItemInUseTick.setSelectedProcedure(item.onItemInUseTick);
        this.onStoppedUsing.setSelectedProcedure(item.onStoppedUsing);
        this.onEntitySwing.setSelectedProcedure(item.onEntitySwing);
        this.onDroppedByPlayer.setSelectedProcedure(item.onDroppedByPlayer);
        this.curioTick.setSelectedProcedure(item.curioTick);
        this.onEquip.setSelectedProcedure(item.onEquip);
        this.onUnequip.setSelectedProcedure(item.onUnequip);
        this.creativeTab.setSelectedItem(item.creativeTab);
        this.stackSize.setValue(item.stackSize);
        this.enchantability.setValue(item.enchantability);
        this.toolType.setValue(item.toolType);
        this.useDuration.setValue(item.useDuration);
        this.damageCount.setValue(item.damageCount);
        this.recipeRemainder.setBlock(item.recipeRemainder);
        this.immuneToFire.setSelected(item.immuneToFire);
        this.destroyAnyBlock.setSelected(item.destroyAnyBlock);
        this.stayInGridWhenCrafting.setSelected(item.stayInGridWhenCrafting);
        this.damageOnCrafting.setSelected(item.damageOnCrafting);
        this.hasGlow.setSelected(item.hasGlow);
        this.glowCondition.setSelectedProcedure(item.glowCondition);
        this.damageVsEntity.setValue(item.damageVsEntity);
        this.enableMeleeDamage.setSelected(item.enableMeleeDamage);
        this.guiBoundTo.setSelectedItem(item.guiBoundTo);
        this.inventorySize.setValue(item.inventorySize);
        this.inventoryStackSize.setValue(item.inventoryStackSize);
        this.isFood.setSelected(item.isFood);
        this.isMeat.setSelected(item.isMeat);
        this.isAlwaysEdible.setSelected(item.isAlwaysEdible);
        this.onFinishUsingItem.setSelectedProcedure(item.onFinishUsingItem);
        this.nutritionalValue.setValue(item.nutritionalValue);
        this.saturation.setValue(item.saturation);
        this.animation.setSelectedItem(item.animation);
        this.eatResultItem.setBlock(item.eatResultItem);
        this.updateGlowElements();
        this.updateFoodPanel();
        Model modelBauble = item.getItemModel();
        if (model != null) {
            this.renderType.setSelectedItem(modelBauble);
        }

        this.addSlot.setEnabled(this.slotType.getSelectedItem().equals("HEAD") || this.slotType.getSelectedItem().equals("NECKLACE") || this.slotType.getSelectedItem().equals("BACK") || this.slotType.getSelectedItem().equals("BODY") || this.slotType.getSelectedItem().equals("BRACELET") || this.slotType.getSelectedItem().equals("HANDS") || this.slotType.getSelectedItem().equals("RING") || this.slotType.getSelectedItem().equals("BELT") || this.slotType.getSelectedItem().equals("CHARM") || this.slotType.getSelectedItem().equals("CURIO"));
        this.slotAmount.setEnabled(this.addSlot.isSelected() && this.addSlot.isEnabled());
        this.baubleModel.setEnabled(this.hasModel.isSelected());
        this.baubleModelTexture.setEnabled(this.hasModel.isSelected());
        this.rotateModel.setEnabled(this.hasModel.isSelected());
        this.translateModel.setEnabled(this.hasModel.isSelected());
    }

    public CuriosBauble getElementFromGUI() {
        CuriosBauble item = new CuriosBauble(this.modElement);
        item.name = this.name.getText();
        item.rarity = (String)this.rarity.getSelectedItem();
        item.rotateModel = this.rotateModel.isSelected();
        item.translateModel = this.translateModel.isSelected();
        item.slotType = (String)this.slotType.getSelectedItem();
        item.addSlot = this.addSlot.isSelected();
        item.baubleModel = ((Model)Objects.requireNonNull((Model)this.baubleModel.getSelectedItem())).getReadableName();
        item.baubleModelTexture = (String)this.baubleModelTexture.getSelectedItem();
        item.friendlyPigs = this.friendlyPigs.isSelected();
        item.enderMask = this.enderMask.isSelected();
        item.slotAmount = (int)this.slotAmount.getValue();
        item.hasModel = this.hasModel.isSelected();
        item.creativeTab = new TabEntry(this.mcreator.getWorkspace(), this.creativeTab.getSelectedItem());
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
        item.hasGlow = this.hasGlow.isSelected();
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
        item.curioTick = this.curioTick.getSelectedProcedure();
        item.onEquip = this.onEquip.getSelectedProcedure();
        item.onUnequip = this.onUnequip.getSelectedProcedure();
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
        item.specialInformation = specialInformation.getSelectedProcedure();
        item.texture = this.texture.getTextureHolder();
        item.renderType = Item.encodeModelType(Objects.requireNonNull(renderType.getSelectedItem()).getType());

        item.customProperties = customProperties.getProperties();
        item.states = customProperties.getStates();

        item.customModelName = ((Model)Objects.requireNonNull((Model)this.renderType.getSelectedItem())).getReadableName();

        return item;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
