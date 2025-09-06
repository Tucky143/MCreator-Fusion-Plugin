package net.tucky143.fusion.parts;

import net.mcreator.element.ModElementType;
import net.mcreator.generator.GeneratorFlavor;
import net.tucky143.fusion.ui.modgui.*;
import net.tucky143.fusion.ui.modgui.legacy.*;
import net.tucky143.fusion.elements.*;
import net.tucky143.fusion.elements.legacy.*;

import static net.mcreator.element.ModElementTypeLoader.register;
import static net.mcreator.generator.GeneratorFlavor.BaseLanguage.JAVA;

public class PluginElementTypes {
    public static ModElementType<?> PARTICLEMODEL;
    public static ModElementType<?> ANIMATEDBLOCK;
    public static ModElementType<?> ANIMATEDITEM;
    public static ModElementType<?> ANIMATEDENTITY;
    public static ModElementType<?> ANIMATEDARMOR;
    public static ModElementType<?> CURIOSBAUBLE;
    public static ModElementType<?> CURIOSSLOT;
    public static ModElementType<?> ATTRIBUTE;
    public static ModElementType<?> MIXIN;
    public static ModElementType<?> ENDBIOME;
    public static ModElementType<?> ENDSTONE;
    public static ModElementType<?> LOOTMODIFIER;
    public static ModElementType<?> BLOCKSTATES;
    public static ModElementType<?> CONFIG;
    public static ModElementType<?> JEIRECIPETYPE;
    public static ModElementType<?> JEIRECIPE;
    public static ModElementType<?> ANVILRECIPE;
    public static ModElementType<?> JEIINFORMATION;
    public static ModElementType<?> LEGACYJEIRECIPETYPE;
    public static ModElementType<?> LEGACYJEIRECIPE;
    public static ModElementType<?> LEGACYANVILRECIPE;
    public static ModElementType<?> LEGACYJEIINFORMATION;
    public static ModElementType<?> TOAST;
    public static ModElementType<?> DYEABLE_ITEM;
    public static ModElementType<?> DYEABLE_ARMOR;

    public static void load() {

        PARTICLEMODEL = register(
                new ModElementType<>("particlemodel", (Character) null, ParticleModelGUI::new, ParticleModel.class)
        );

        ANIMATEDBLOCK = register(
                new ModElementType<>("animatedblock", (Character) 'D', AnimatedBlockGUI::new, AnimatedBlock.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDITEM = register(
                new ModElementType<>("animateditem", (Character) 'I', AnimatedItemGUI::new, AnimatedItem.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDENTITY = register(
                new ModElementType<>("animatedentity", (Character) 'E', AnimatedEntityGUI::new, AnimatedEntity.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDARMOR = register(
                new ModElementType<>("animatedarmor", (Character) 'A', AnimatedArmorGUI::new, AnimatedArmor.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        CURIOSBAUBLE = register(
                new ModElementType<>("curiosbauble", (Character) 'B', CuriosBaubleGUI::new, CuriosBauble.class)
        );

        CURIOSSLOT = register(
                new ModElementType<>("curiosslot", (Character) 'S', CuriosSlotGUI::new, CuriosSlot.class)
        );

        ATTRIBUTE = register(
                new ModElementType<>("attribute", (Character) null, AttributeGUI::new, Attribute.class)
        );

        MIXIN = register(
                new ModElementType<>("mixin", (Character) 'M', MixinGUI::new, Mixin.class)
        );

        ENDBIOME = register(
                new ModElementType<>("endbiome", (Character) 'E', EndBiomeGUI::new, EndBiome.class)
        );

        ENDSTONE = register(
                new ModElementType<>("endstone", (Character) null, EndstoneGUI::new, Endstone.class)
        );

        LOOTMODIFIER = register(
                new ModElementType<>("lootmodifier", (Character) 'L', LootModifierGUI::new, LootModifier.class)
        );

        BLOCKSTATES = register(
                new ModElementType<>("blockstates", (Character) null, BlockstatesGUI::new, Blockstates.class)
        );

        CONFIG = register(
                new ModElementType<>("config", (Character) null, ConfigGUI::new, Config.class)
        );

        JEIRECIPETYPE = register(
                new ModElementType<>("jeirecipetype", (Character) 'C', JeiRecipeTypeGUI::new, JeiRecipeType.class)
        );

        JEIRECIPE = register(
                new ModElementType<>("jeirecipe", (Character) 'R', JeiRecipeGUI::new, JeiRecipe.class)
        );

        ANVILRECIPE = register(
                new ModElementType<>("anvilrecipe", (Character) 'A', AnvilRecipeGUI::new, AnvilRecipe.class)
        );

        JEIINFORMATION = register(
                new ModElementType<>("jeiinformation", (Character) 'I', JeiInformationGUI::new, JeiInformation.class)
        );

        LEGACYJEIRECIPETYPE = register(
                new ModElementType<>("legacyjeirecipetype", (Character) 'C', LegacyJeiRecipeTypeGUI::new, LegacyJeiRecipeType.class)
        );

        LEGACYJEIRECIPE = register(
                new ModElementType<>("legacyjeirecipe", (Character) 'R', LegacyJeiRecipeGUI::new, LegacyJeiRecipe.class)
        );

        LEGACYANVILRECIPE = register(
                new ModElementType<>("legacyanvilrecipe", (Character) 'A', LegacyAnvilRecipeGUI::new, LegacyAnvilRecipe.class)
        );

        LEGACYJEIINFORMATION = register(
                new ModElementType<>("legacyjeiinformation", (Character) 'I', LegacyJeiInformationGUI::new, LegacyJeiInformation.class)
        );

        TOAST = register(
                new ModElementType<>("toast", (Character) 'T', ToastGUI::new, Toast.class)
        );

        DYEABLE_ITEM = register(
                new ModElementType<>("dyeable_item", (Character)null, DyeableItemGUI::new, DyeableItem.class)
        );

        DYEABLE_ARMOR = register(new ModElementType<>("dyeable_armor", (Character)null, DyeableArmorGUI::new, DyeableArmor.class));

    }

}
