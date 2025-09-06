//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.elements;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import javax.swing.ImageIcon;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.Sound;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.element.parts.procedure.LogicProcedure;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.element.parts.procedure.StringListProcedure;
import net.mcreator.element.types.interfaces.IItem;
import net.mcreator.element.types.interfaces.ITabContainedElement;
import net.mcreator.io.FileIO;
import net.mcreator.minecraft.MCItem;
import net.mcreator.minecraft.MinecraftImageGenerator.Preview;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;
import net.mcreator.workspace.references.TextureReference;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.TexturedModel;
import net.mcreator.workspace.resources.Model.Type;
import net.tucky143.fusion.utils.DyeableImageUtils;

public class DyeableArmor extends GeneratableElement implements IItem, ITabContainedElement {
    public boolean enableHelmet;
    @TextureReference(TextureType.ITEM)
    public TextureHolder textureHelmet;
    public boolean enableBody;
    @TextureReference(TextureType.ITEM)
    public TextureHolder textureBody;
    public boolean enableLeggings;
    @TextureReference(TextureType.ITEM)
    public TextureHolder textureLeggings;
    public boolean enableBoots;
    @TextureReference(TextureType.ITEM)
    public TextureHolder textureBoots;
    public Procedure onHelmetTick;
    public Procedure onBodyTick;
    public Procedure onLeggingsTick;
    public Procedure onBootsTick;
    public List<TabEntry> creativeTabs = new ArrayList();
    @TextureReference(
        value = TextureType.ARMOR,
        files = {"%s_layer_1", "%s_layer_2"}
    )
    public String armorTextureFile;
    public String helmetName;
    public String bodyName;
    public String leggingsName;
    public String bootsName;
    public StringListProcedure helmetSpecialInformation;
    public StringListProcedure bodySpecialInformation;
    public StringListProcedure leggingsSpecialInformation;
    public StringListProcedure bootsSpecialInformation;
    public String helmetModelName = "Default";
    public String helmetModelPart;
    @TextureReference(
        value = TextureType.ENTITY,
        defaultValues = {"From armor"}
    )
    public String helmetModelTexture;
    public String bodyModelName = "Default";
    public String bodyModelPart;
    public String armsModelPartL;
    public String armsModelPartR;
    @TextureReference(
        value = TextureType.ENTITY,
        defaultValues = {"From armor"}
    )
    public String bodyModelTexture;
    public String leggingsModelName = "Default";
    public String leggingsModelPartL;
    public String leggingsModelPartR;
    @TextureReference(
        value = TextureType.ENTITY,
        defaultValues = {"From armor"}
    )
    public String leggingsModelTexture;
    public String bootsModelName = "Default";
    public String bootsModelPartL;
    public String bootsModelPartR;
    @TextureReference(
        value = TextureType.ENTITY,
        defaultValues = {"From armor"}
    )
    public String bootsModelTexture;
    public int helmetItemRenderType = 0;
    public String helmetItemCustomModelName = "Normal";
    public int bodyItemRenderType = 0;
    public String bodyItemCustomModelName = "Normal";
    public int leggingsItemRenderType = 0;
    public String leggingsItemCustomModelName = "Normal";
    public int bootsItemRenderType = 0;
    public String bootsItemCustomModelName = "Normal";
    public boolean helmetImmuneToFire;
    public boolean bodyImmuneToFire;
    public boolean leggingsImmuneToFire;
    public boolean bootsImmuneToFire;
    public LogicProcedure helmetGlowCondition;
    public LogicProcedure bodyGlowCondition;
    public LogicProcedure leggingsGlowCondition;
    public LogicProcedure bootsGlowCondition;
    public LogicProcedure helmetPiglinNeutral;
    public LogicProcedure bodyPiglinNeutral;
    public LogicProcedure leggingsPiglinNeutral;
    public LogicProcedure bootsPiglinNeutral;
    public int maxDamage;
    public int damageValueHelmet;
    public int damageValueBody;
    public int damageValueLeggings;
    public int damageValueBoots;
    public int enchantability;
    public double toughness;
    public double knockbackResistance;
    public Sound equipSound;
    @ModElementReference
    public List<MItemBlock> repairItems;
    @TextureReference(TextureType.ITEM)
    public TextureHolder helmetOverlayTexture;
    public boolean helmetIsDyeable;
    public Color helmetDefaultColor;
    public boolean helmetCanWaterRemoveColor;
    public boolean helmetCanLavaRemoveColor;
    public boolean helmetCanPowderSnowRemoveColor;
    @TextureReference(TextureType.ITEM)
    public TextureHolder bodyOverlayTexture;
    public boolean bodyIsDyeable;
    public Color bodyDefaultColor;
    public boolean bodyCanWaterRemoveColor;
    public boolean bodyCanLavaRemoveColor;
    public boolean bodyCanPowderSnowRemoveColor;
    @TextureReference(TextureType.ITEM)
    public TextureHolder leggingsOverlayTexture;
    public boolean leggingsIsDyeable;
    public Color leggingsDefaultColor;
    public boolean leggingsCanWaterRemoveColor;
    public boolean leggingsCanLavaRemoveColor;
    public boolean leggingsCanPowderSnowRemoveColor;
    @TextureReference(TextureType.ITEM)
    public TextureHolder bootsOverlayTexture;
    public boolean bootsIsDyeable;
    public Color bootsDefaultColor;
    public boolean bootsCanWaterRemoveColor;
    public boolean bootsCanLavaRemoveColor;
    public boolean bootsCanPowderSnowRemoveColor;
    @TextureReference(
        value = TextureType.ARMOR,
        files = {"%s_layer_1_overlay", "%s_layer_2_overlay"}
    )
    public String armorOverlayTextureFile;
    public List<String> mixins = List.of("HumanoidArmorLayerMixin");

    public DyeableArmor(ModElement element) {
        super(element);
    }

    public BufferedImage generateModElementPicture() {
        List<Image> armorPieces = new ArrayList();
        if (this.enableHelmet) {
            armorPieces.add(DyeableImageUtils.createDyeableImage(this.textureHelmet.getImage(TextureType.ITEM), this.helmetOverlayTexture.isEmpty() ? null : this.helmetOverlayTexture.getImage(TextureType.ITEM), this.helmetIsDyeable ? this.helmetDefaultColor : null));
        }

        if (this.enableBody) {
            armorPieces.add(DyeableImageUtils.createDyeableImage(this.textureBody.getImage(TextureType.ITEM), this.bodyOverlayTexture.isEmpty() ? null : this.bodyOverlayTexture.getImage(TextureType.ITEM), this.bodyIsDyeable ? this.bodyDefaultColor : null));
        }

        if (this.enableLeggings) {
            armorPieces.add(DyeableImageUtils.createDyeableImage(this.textureLeggings.getImage(TextureType.ITEM), this.leggingsOverlayTexture.isEmpty() ? null : this.leggingsOverlayTexture.getImage(TextureType.ITEM), this.leggingsIsDyeable ? this.leggingsDefaultColor : null));
        }

        if (this.enableBoots) {
            armorPieces.add(DyeableImageUtils.createDyeableImage(this.textureBoots.getImage(TextureType.ITEM), this.bootsOverlayTexture.isEmpty() ? null : this.bootsOverlayTexture.getImage(TextureType.ITEM), this.bootsIsDyeable ? this.bootsDefaultColor : null));
        }

        return Preview.generateArmorPreviewPicture(armorPieces);
    }

    public List<Part> getDyeableParts() {
        List<Part> res = new ArrayList();
        if (this.helmetIsDyeable && this.enableHelmet) {
            res.add(DyeableArmor.Part.HELMET);
        }

        if (this.bodyIsDyeable && this.enableBody) {
            res.add(DyeableArmor.Part.CHESTPLATE);
        }

        if (this.leggingsIsDyeable && this.enableLeggings) {
            res.add(DyeableArmor.Part.LEGGINGS);
        }

        if (this.bootsIsDyeable && this.enableBoots) {
            res.add(DyeableArmor.Part.BOOTS);
        }

        return res;
    }

    public boolean hasDyeablePart() {
        return !this.getDyeableParts().isEmpty();
    }

    public boolean canWaterRemoveAnyColor() {
        return this.helmetCanWaterRemoveColor && this.enableHelmet || this.bodyCanWaterRemoveColor && this.enableBody || this.leggingsCanWaterRemoveColor && this.enableLeggings || this.bootsCanWaterRemoveColor && this.enableBoots;
    }

    public boolean canLavaRemoveAnyColor() {
        return this.helmetCanLavaRemoveColor && this.enableHelmet || this.bodyCanLavaRemoveColor && this.enableBody || this.leggingsCanLavaRemoveColor && this.enableLeggings || this.bootsCanLavaRemoveColor && this.enableBoots;
    }

    public boolean canPowderSnowRemoveAnyColor() {
        return this.helmetCanPowderSnowRemoveColor && this.enableHelmet || this.bodyCanPowderSnowRemoveColor && this.enableBody || this.leggingsCanPowderSnowRemoveColor && this.enableLeggings || this.bootsCanPowderSnowRemoveColor && this.enableBoots;
    }

    public boolean hasDyeableRemovalParts() {
        return !this.getDyeableRemovalParts().isEmpty();
    }

    public List<Part> getDyeableRemovalParts() {
        List<Part> res = new ArrayList();
        if (this.canWaterRemoveColorPart(DyeableArmor.Part.HELMET) || this.canLavaRemoveColorPart(DyeableArmor.Part.HELMET) || this.canPowderSnowRemoveColorPart(DyeableArmor.Part.HELMET)) {
            res.add(DyeableArmor.Part.HELMET);
        }

        if (this.canWaterRemoveColorPart(DyeableArmor.Part.CHESTPLATE) || this.canLavaRemoveColorPart(DyeableArmor.Part.CHESTPLATE) || this.canPowderSnowRemoveColorPart(DyeableArmor.Part.CHESTPLATE)) {
            res.add(DyeableArmor.Part.CHESTPLATE);
        }

        if (this.canWaterRemoveColorPart(DyeableArmor.Part.LEGGINGS) || this.canLavaRemoveColorPart(DyeableArmor.Part.LEGGINGS) || this.canPowderSnowRemoveColorPart(DyeableArmor.Part.LEGGINGS)) {
            res.add(DyeableArmor.Part.LEGGINGS);
        }

        if (this.canWaterRemoveColorPart(DyeableArmor.Part.BOOTS) || this.canLavaRemoveColorPart(DyeableArmor.Part.BOOTS) || this.canPowderSnowRemoveColorPart(DyeableArmor.Part.BOOTS)) {
            res.add(DyeableArmor.Part.BOOTS);
        }

        return res;
    }

    public boolean canWaterRemoveColorPart(Part part) {
        boolean var10000;
        switch (part.ordinal()) {
            case 0 -> var10000 = this.helmetCanWaterRemoveColor && this.helmetIsDyeable && this.enableHelmet;
            case 1 -> var10000 = this.bodyCanWaterRemoveColor && this.bodyIsDyeable && this.enableBoots;
            case 2 -> var10000 = this.leggingsCanWaterRemoveColor && this.leggingsIsDyeable && this.enableLeggings;
            case 3 -> var10000 = this.bootsCanWaterRemoveColor && this.bootsIsDyeable && this.enableBoots;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public boolean canLavaRemoveColorPart(Part part) {
        boolean var10000;
        switch (part.ordinal()) {
            case 0 -> var10000 = this.helmetCanLavaRemoveColor && this.helmetIsDyeable && this.enableHelmet;
            case 1 -> var10000 = this.bodyCanLavaRemoveColor && this.bodyIsDyeable && this.enableBody;
            case 2 -> var10000 = this.leggingsCanLavaRemoveColor && this.leggingsIsDyeable && this.enableLeggings;
            case 3 -> var10000 = this.bootsCanLavaRemoveColor && this.bootsIsDyeable && this.enableBoots;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public boolean canPowderSnowRemoveColorPart(Part part) {
        boolean var10000;
        switch (part.ordinal()) {
            case 0 -> var10000 = this.helmetCanPowderSnowRemoveColor && this.helmetIsDyeable && this.enableHelmet;
            case 1 -> var10000 = this.bodyCanPowderSnowRemoveColor && this.bodyIsDyeable && this.enableBody;
            case 2 -> var10000 = this.leggingsCanPowderSnowRemoveColor && this.leggingsIsDyeable && this.enableLeggings;
            case 3 -> var10000 = this.bootsCanPowderSnowRemoveColor && this.bootsIsDyeable && this.enableBoots;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public boolean partHasOverlayTexture(String part) {
        boolean var10000;
        switch (part) {
            case "helmet" -> var10000 = !this.helmetOverlayTexture.isEmpty();
            case "body" -> var10000 = !this.bodyOverlayTexture.isEmpty();
            case "leggings" -> var10000 = !this.leggingsOverlayTexture.isEmpty();
            case "boots" -> var10000 = !this.bootsOverlayTexture.isEmpty();
            default -> var10000 = false;
        }

        return var10000;
    }

    public TextureHolder getItemOverlayTextureFor(String part) {
        TextureHolder var10000;
        switch (part) {
            case "helmet" -> var10000 = this.helmetOverlayTexture;
            case "body" -> var10000 = this.bodyOverlayTexture;
            case "leggings" -> var10000 = this.leggingsOverlayTexture;
            case "boots" -> var10000 = this.bootsOverlayTexture;
            default -> var10000 = null;
        }

        return var10000;
    }

    public Color getDefaultColor(Part part) {
        Color var10000;
        switch (part.ordinal()) {
            case 0 -> var10000 = this.helmetDefaultColor;
            case 1 -> var10000 = this.bodyDefaultColor;
            case 2 -> var10000 = this.leggingsDefaultColor;
            case 3 -> var10000 = this.bootsDefaultColor;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public boolean hasArmorOverlayTexture() {
        return !this.armorOverlayTextureFile.isEmpty();
    }

    public boolean isEnable(Part part) {
        boolean var10000;
        switch (part.ordinal()) {
            case 0 -> var10000 = this.enableHelmet;
            case 1 -> var10000 = this.enableBody;
            case 2 -> var10000 = this.enableLeggings;
            case 3 -> var10000 = this.enableBoots;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    @Nullable
    public Model getHelmetModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.helmetModelName.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.helmetModelName, modelType);
    }

    @Nullable
    public Model getBodyModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.bodyModelName.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.bodyModelName, modelType);
    }

    @Nullable
    public Model getLeggingsModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.leggingsModelName.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.leggingsModelName, modelType);
    }

    @Nullable
    public Model getBootsModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.bootsModelName.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.bootsModelName, modelType);
    }

    @Nullable
    public Model getHelmetItemModel() {
        Model.Type modelType = Type.BUILTIN;
        if (this.helmetItemRenderType == 1) {
            modelType = Type.JSON;
        } else if (this.helmetItemRenderType == 2) {
            modelType = Type.OBJ;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.helmetItemCustomModelName, modelType);
    }

    @Nullable
    public Model getBodyItemModel() {
        Model.Type modelType = Type.BUILTIN;
        if (this.bodyItemRenderType == 1) {
            modelType = Type.JSON;
        } else if (this.bodyItemRenderType == 2) {
            modelType = Type.OBJ;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.bodyItemCustomModelName, modelType);
    }

    @Nullable
    public Model getLeggingsItemModel() {
        Model.Type modelType = Type.BUILTIN;
        if (this.leggingsItemRenderType == 1) {
            modelType = Type.JSON;
        } else if (this.leggingsItemRenderType == 2) {
            modelType = Type.OBJ;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.leggingsItemCustomModelName, modelType);
    }

    @Nullable
    public Model getBootsItemModel() {
        Model.Type modelType = Type.BUILTIN;
        if (this.bootsItemRenderType == 1) {
            modelType = Type.JSON;
        } else if (this.bootsItemRenderType == 2) {
            modelType = Type.OBJ;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.bootsItemCustomModelName, modelType);
    }

    public String getItemCustomModelNameFor(String part) {
        String var10000;
        switch (part) {
            case "helmet" -> var10000 = this.helmetItemCustomModelName.split(":")[0];
            case "body" -> var10000 = this.bodyItemCustomModelName.split(":")[0];
            case "leggings" -> var10000 = this.leggingsItemCustomModelName.split(":")[0];
            case "boots" -> var10000 = this.bootsItemCustomModelName.split(":")[0];
            default -> var10000 = "";
        }

        return var10000;
    }

    public Map<String, TextureHolder> getItemModelTextureMap(String part) {
        Model var10000;
        switch (part) {
            case "helmet" -> var10000 = this.getHelmetItemModel();
            case "body" -> var10000 = this.getBodyItemModel();
            case "leggings" -> var10000 = this.getLeggingsItemModel();
            case "boots" -> var10000 = this.getBootsItemModel();
            default -> var10000 = null;
        }

        Model model = var10000;
        return (Map<String, TextureHolder>)(model instanceof TexturedModel && ((TexturedModel)model).getTextureMapping() != null ? ((TexturedModel)model).getTextureMapping().getTextureMap() : new HashMap());
    }

    public TextureHolder getItemTextureFor(String part) {
        TextureHolder var10000;
        switch (part) {
            case "helmet" -> var10000 = this.textureHelmet;
            case "body" -> var10000 = this.textureBody;
            case "leggings" -> var10000 = this.textureLeggings;
            case "boots" -> var10000 = this.textureBoots;
            default -> var10000 = null;
        }

        return var10000;
    }

    public boolean hasHelmetNormalModel() {
        Model helmetItemModel = this.getHelmetItemModel();
        return helmetItemModel == null || helmetItemModel.getType() == Type.BUILTIN && helmetItemModel.getReadableName().equals("Normal");
    }

    public boolean hasHelmetToolModel() {
        Model helmetItemModel = this.getHelmetItemModel();
        if (helmetItemModel == null) {
            return false;
        } else {
            return helmetItemModel.getType() == Type.BUILTIN && helmetItemModel.getReadableName().equals("Tool");
        }
    }

    public boolean hasBodyNormalModel() {
        Model bodyItemModel = this.getBodyItemModel();
        return bodyItemModel == null || bodyItemModel.getType() == Type.BUILTIN && bodyItemModel.getReadableName().equals("Normal");
    }

    public boolean hasBodyToolModel() {
        Model bodyItemModel = this.getBodyItemModel();
        if (bodyItemModel == null) {
            return false;
        } else {
            return bodyItemModel.getType() == Type.BUILTIN && bodyItemModel.getReadableName().equals("Tool");
        }
    }

    public boolean hasLeggingsNormalModel() {
        Model leggingsItemModel = this.getLeggingsItemModel();
        return leggingsItemModel == null || leggingsItemModel.getType() == Type.BUILTIN && leggingsItemModel.getReadableName().equals("Normal");
    }

    public boolean hasLeggingsToolModel() {
        Model leggingsItemModel = this.getLeggingsItemModel();
        if (leggingsItemModel == null) {
            return false;
        } else {
            return leggingsItemModel.getType() == Type.BUILTIN && leggingsItemModel.getReadableName().equals("Tool");
        }
    }

    public boolean hasBootsNormalModel() {
        Model bootsItemModel = this.getBootsItemModel();
        return bootsItemModel == null || bootsItemModel.getType() == Type.BUILTIN && bootsItemModel.getReadableName().equals("Normal");
    }

    public boolean hasBootsToolModel() {
        Model bootsItemModel = this.getBootsItemModel();
        if (bootsItemModel == null) {
            return false;
        } else {
            return bootsItemModel.getType() == Type.BUILTIN && bootsItemModel.getReadableName().equals("Tool");
        }
    }

    public String getArmorModelsCode() {
        Set<Model> models = new HashSet();
        Model model1 = this.getHelmetModel();
        if (model1 != null && model1.getType() == Type.JAVA) {
            models.add(model1);
        }

        Model model2 = this.getBodyModel();
        if (model2 != null && model2.getType() == Type.JAVA) {
            models.add(model2);
        }

        Model model3 = this.getLeggingsModel();
        if (model3 != null && model3.getType() == Type.JAVA) {
            models.add(model3);
        }

        Model model4 = this.getBootsModel();
        if (model4 != null && model4.getType() == Type.JAVA) {
            models.add(model4);
        }

        StringBuilder modelsCode = new StringBuilder();

        for(Model model : models) {
            modelsCode.append(FileIO.readFileToString(model.getFile())).append("\n\n");
        }

        return modelsCode.toString();
    }

    public ImageIcon getIconForMCItem(Workspace workspace, String suffix) {
        ImageIcon var10000;
        switch (suffix) {
            case "helmet" -> var10000 = this.textureHelmet.getImageIcon(TextureType.ITEM);
            case "body" -> var10000 = this.textureBody.getImageIcon(TextureType.ITEM);
            case "legs" -> var10000 = this.textureLeggings.getImageIcon(TextureType.ITEM);
            case "boots" -> var10000 = this.textureBoots.getImageIcon(TextureType.ITEM);
            default -> var10000 = null;
        }

        return var10000;
    }

    public List<MCItem> providedMCItems() {
        ArrayList<MCItem> retval = new ArrayList();
        if (this.enableHelmet) {
            retval.add(new MCItem.Custom(this.getModElement(), "helmet", "item", "Helmet"));
        }

        if (this.enableBody) {
            retval.add(new MCItem.Custom(this.getModElement(), "body", "item", "Chestplate"));
        }

        if (this.enableLeggings) {
            retval.add(new MCItem.Custom(this.getModElement(), "legs", "item", "Leggings"));
        }

        if (this.enableBoots) {
            retval.add(new MCItem.Custom(this.getModElement(), "boots", "item", "Boots"));
        }

        return retval;
    }

    public List<TabEntry> getCreativeTabs() {
        return this.creativeTabs;
    }

    public List<MCItem> getCreativeTabItems() {
        return this.providedMCItems();
    }

    public static enum Part {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
    }
}
