package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.EntityEntry;
import net.mcreator.minecraft.MinecraftImageGenerator;
import net.mcreator.workspace.elements.ModElement;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Class stores informations about created mod element, is saved in Workspace
 */
public class Attribute extends GeneratableElement {
    public String description = "";
    public Double minValue;
    public Double maxValue;
    public Double defaultValue;
    public Boolean isPersistent;
    public List<EntityEntry> entities;

    @SuppressWarnings("unused")
    private Attribute() {
        this(null);
    }

    @SuppressWarnings("unused")
    public Attribute(ModElement element) {
        super(element);
    }

    public BufferedImage generateModElementPicture() {
        return MinecraftImageGenerator.Preview.generateKeybindPreviewPicture("Attribute" + this.getModElement().getName());
    }
}

