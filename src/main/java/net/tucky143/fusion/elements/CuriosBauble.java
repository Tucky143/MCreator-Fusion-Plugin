package net.tucky143.fusion.elements;

import java.awt.image.BufferedImage;
import java.util.*;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.element.parts.procedure.StringListProcedure;
import net.mcreator.element.types.Item;
import net.mcreator.element.types.interfaces.IItem;
import net.mcreator.element.types.interfaces.IItemWithModel;
import net.mcreator.element.types.interfaces.IItemWithTexture;
import net.mcreator.element.types.interfaces.ITabContainedElement;
import net.mcreator.minecraft.DataListEntry;
import net.mcreator.minecraft.DataListLoader;
import net.mcreator.minecraft.MCItem;
import net.mcreator.ui.minecraft.states.StateMap;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Texture;
import net.mcreator.workspace.resources.TexturedModel;
import net.mcreator.workspace.resources.Model.Type;
import net.mcreator.element.types.interfaces.*;

public class CuriosBauble extends GeneratableElement implements IItem, IItemWithModel, ITabContainedElement, ISpecialInfoHolder, IItemWithTexture {
    public int renderType;
    public TextureHolder texture;
    public String customModelName;
    public String name;
    public String rarity;
    public String slotType;
    public String baubleModel;
    public String baubleModelTexture;
    public TabEntry creativeTab;
    public int stackSize;
    public int enchantability;
    public int useDuration;
    public double toolType;
    public int damageCount;
    public MItemBlock recipeRemainder;
    public boolean destroyAnyBlock;
    public boolean immuneToFire;
    public boolean stayInGridWhenCrafting;
    public boolean damageOnCrafting;
    public boolean enableMeleeDamage;
    public boolean addSlot;
    public double damageVsEntity;
    public StringListProcedure specialInformation;
    public boolean hasGlow;
    public Procedure glowCondition;

    public Map<String, Procedure> customProperties;
    public List<Item.StateEntry> states;

    public String guiBoundTo;
    public int inventorySize;
    public int inventoryStackSize;
    public int slotAmount;
    public Procedure onRightClickedInAir;
    public Procedure onRightClickedOnBlock;
    public Procedure onCrafted;
    public Procedure onEntityHitWith;
    public Procedure onItemInInventoryTick;
    public Procedure onItemInUseTick;
    public Procedure onStoppedUsing;
    public Procedure onEntitySwing;
    public Procedure onDroppedByPlayer;
    public Procedure onFinishUsingItem;
    public Procedure curioTick;
    public Procedure onEquip;
    public Procedure onUnequip;
    public boolean isFood;
    public boolean friendlyPigs;
    public boolean enderMask;
    public boolean rotateModel;
    public boolean translateModel;
    public int nutritionalValue;
    public double saturation;
    public MItemBlock eatResultItem;
    public boolean isMeat;
    public boolean isAlwaysEdible;
    public boolean hasModel;
    public String animation;

    private CuriosBauble() {
        this((ModElement)null);
    }

    public CuriosBauble(ModElement element) {
        super(element);
        this.rarity = "COMMON";
        this.inventorySize = 9;
        this.inventoryStackSize = 64;
        this.saturation = 0.30000001192092896;
        this.animation = "eat";
        this.slotAmount = 1;
        this.slotType = "CURIO";
        this.customProperties = new LinkedHashMap<>();
        this.states = new ArrayList<>();
    }

    public List<Item.StateEntry> getModels() {
        List<Item.StateEntry> models = new ArrayList<>();
        List<String> builtinProperties = DataListLoader.loadDataList("itemproperties").stream()
                .filter(e -> e.isSupportedInWorkspace(getModElement().getWorkspace())).map(DataListEntry::getName)
                .toList();

        states.forEach(state -> {
            Item.StateEntry model = new Item.StateEntry();
            model.setWorkspace(getModElement().getWorkspace());
            model.renderType = state.renderType;
            model.texture = state.texture;
            model.customModelName = state.customModelName;

            model.stateMap = new StateMap();
            state.stateMap.forEach((prop, value) -> {
                if (customProperties.containsKey(prop.getName().replace("CUSTOM:", "")) || builtinProperties.contains(
                        prop.getName()))
                    model.stateMap.put(prop, value);
            });

            // only add this state if at least one supported property is present
            if (!model.stateMap.isEmpty())
                models.add(model);
        });
        return models;
    }

    @Override public List<MCItem> providedMCItems() {
        return List.of(new MCItem.Custom(this.getModElement(), null, "item"));
    }

    public Model getEntityModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.baubleModel.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.baubleModel, modelType);
    }

    public BufferedImage generateModElementPicture() {
        return ImageUtils.resizeAndCrop(Texture.fromName(getModElement().getWorkspace(), TextureType.ITEM, this.texture.getRawTextureName()).getTextureIcon(getModElement().getWorkspace()).getImage(), 32);
    }

    public Model getItemModel() {
        return Model.getModelByParams(getModElement().getWorkspace(), customModelName, Item.decodeModelType(renderType));
    }

    public Map<String, TextureHolder> getTextureMap() {
        if (getItemModel() instanceof TexturedModel textured && textured.getTextureMapping() != null)
            return textured.getTextureMapping().getTextureMap();
        return new HashMap<>();
    }

    public List<TabEntry> getCreativeTabs() {
        return List.of(this.creativeTab);
    }

    @Override
    public List<MCItem> getCreativeTabItems() {
        return providedMCItems();
    }

    public TextureHolder getTexture() {
        return this.texture;
    }
    public StringListProcedure getSpecialInfoProcedure() {
        return specialInformation;
    }

    public boolean hasNormalModel() {
        return Item.decodeModelType(renderType) == Model.Type.BUILTIN && customModelName.equals("Normal");
    }

    public boolean hasToolModel() {
        return Item.decodeModelType(renderType) == Model.Type.BUILTIN && customModelName.equals("Tool");
    }

    public boolean hasInventory() {
        return this.guiBoundTo != null && !this.guiBoundTo.isEmpty() && !this.guiBoundTo.equals("<NONE>");
    }

    public boolean hasNonDefaultAnimation() {
        return this.isFood ? !this.animation.equals("eat") : !this.animation.equals("none");
    }

    public boolean hasEatResultItem() {
        return this.isFood && this.eatResultItem != null && !this.eatResultItem.isEmpty();
    }

    public boolean hasModel() {
        return this.hasModel && !baubleModel.equals("Default");
    }

}
