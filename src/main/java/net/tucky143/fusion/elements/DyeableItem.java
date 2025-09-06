//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.elements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.ProjectileEntry;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.element.parts.procedure.LogicProcedure;
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
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;
import net.mcreator.workspace.references.ResourceReference;
import net.mcreator.workspace.references.TextureReference;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.TexturedModel;
import net.mcreator.workspace.resources.Model.Type;
import net.tucky143.fusion.utils.DyeableImageUtils;

public class DyeableItem extends GeneratableElement implements IItem, IItemWithModel, ITabContainedElement, IItemWithTexture {
    public int renderType;
    @TextureReference(TextureType.ITEM)
    public TextureHolder texture;
    @Nonnull
    public String customModelName;
    @ModElementReference
    public Map<String, Procedure> customProperties = new LinkedHashMap();
    @TextureReference(TextureType.ITEM)
    @ResourceReference("model")
    public List<Item.StateEntry> states = new ArrayList();
    public String name;
    public String rarity = "COMMON";
    public List<TabEntry> creativeTabs = new ArrayList();
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
    public double damageVsEntity;
    public StringListProcedure specialInformation;
    public LogicProcedure glowCondition;
    @Nullable
    @ModElementReference(
        defaultValues = {"<NONE>"}
    )
    public String guiBoundTo;
    public int inventorySize = 9;
    public int inventoryStackSize = 64;
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
    public boolean enableRanged;
    public boolean shootConstantly;
    public boolean rangedItemChargesPower;
    public ProjectileEntry projectile;
    public Procedure onRangedItemUsed;
    public Procedure rangedUseCondition;
    public boolean isFood;
    public int nutritionalValue;
    public double saturation = (double)0.3F;
    public MItemBlock eatResultItem;
    public boolean isMeat;
    public boolean isAlwaysEdible;
    public String animation = "eat";
    @TextureReference(TextureType.ITEM)
    public TextureHolder overlayTexture;
    public boolean isDyeable;
    public Color defaultColor;
    public boolean canWaterRemoveColor;
    public boolean canLavaRemoveColor;
    public boolean canPowderSnowRemoveColor;

    public DyeableItem(ModElement element) {
        super(element);
    }

    public boolean canRemoveColor() {
        return this.canWaterRemoveColor || this.canLavaRemoveColor || this.canPowderSnowRemoveColor;
    }

    public boolean hasOverlayTexture() {
        return !this.overlayTexture.isEmpty();
    }

    public BufferedImage generateModElementPicture() {
        return DyeableImageUtils.createDyeableImage(this.texture.getImage(TextureType.ITEM), this.overlayTexture.isEmpty() ? null : this.overlayTexture.getImage(TextureType.ITEM), this.isDyeable ? this.defaultColor : null);
    }

    public Model getItemModel() {
        return Model.getModelByParams(this.getModElement().getWorkspace(), this.customModelName, Item.decodeModelType(this.renderType));
    }

    public Map<String, TextureHolder> getTextureMap() {
        Model var2 = this.getItemModel();
        if (var2 instanceof TexturedModel textured) {
            if (textured.getTextureMapping() != null) {
                return textured.getTextureMapping().getTextureMap();
            }
        }

        return new HashMap();
    }

    public TextureHolder getTexture() {
        return this.texture;
    }

    public List<MCItem> providedMCItems() {
        return List.of(new MCItem.Custom(this.getModElement(), (String)null, "item"));
    }

    public List<TabEntry> getCreativeTabs() {
        return this.creativeTabs;
    }

    public List<MCItem> getCreativeTabItems() {
        return this.providedMCItems();
    }

    public boolean hasNormalModel() {
        return Item.decodeModelType(this.renderType) == Type.BUILTIN && this.customModelName.equals("Normal");
    }

    public boolean hasToolModel() {
        return Item.decodeModelType(this.renderType) == Type.BUILTIN && this.customModelName.equals("Tool");
    }

    public boolean hasRangedItemModel() {
        return Item.decodeModelType(this.renderType) == Type.BUILTIN && this.customModelName.equals("Ranged item");
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

    public List<Item.StateEntry> getModels() {
        List<Item.StateEntry> models = new ArrayList();
        List<String> builtinProperties = DataListLoader.loadDataList("itemproperties").stream().filter((e) -> e.isSupportedInWorkspace(this.getModElement().getWorkspace())).map(DataListEntry::getName).toList();
        this.states.forEach((state) -> {
            Item.StateEntry model = new Item.StateEntry();
            model.setWorkspace(this.getModElement().getWorkspace());
            model.renderType = state.renderType;
            model.texture = state.texture;
            model.customModelName = state.customModelName;
            model.stateMap = new StateMap();
            state.stateMap.forEach((prop, value) -> {
                if (this.customProperties.containsKey(prop.getName().replace("CUSTOM:", "")) || builtinProperties.contains(prop.getName())) {
                    model.stateMap.put(prop, value);
                }

            });
            if (!model.stateMap.isEmpty()) {
                models.add(model);
            }

        });
        return models;
    }
}
