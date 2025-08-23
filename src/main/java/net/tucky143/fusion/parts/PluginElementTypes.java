package net.tucky143.fusion.parts;

import net.mcreator.element.ModElementType;
import net.mcreator.generator.GeneratorFlavor;
import net.tucky143.fusion.ui.modgui.*;
import net.tucky143.fusion.elements.*;

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
    }

}
