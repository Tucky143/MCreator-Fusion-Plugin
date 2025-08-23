package net.tucky143.fusion.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.procedure.NumberProcedure;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;

public class ParticleModel extends GeneratableElement {
    public String particle;
    public String model;
    public String rendertype;
    public NumberProcedure modelScale;
    public NumberProcedure modelRotationX;
    public NumberProcedure modelRotationY;
    public NumberProcedure modelRotationZ;
    public ParticleModel(ModElement element) {
        super(element);
    }

    public Model getModel() {
        return Model.getModelByParams(this.getModElement().getWorkspace(), this.model, Model.Type.JAVA);
    }

}
