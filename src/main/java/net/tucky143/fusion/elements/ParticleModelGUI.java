package net.tucky143.fusion.elements;

import net.mcreator.blockly.data.Dependency;
import net.mcreator.element.ModElementType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.AbstractProcedureSelector;
import net.mcreator.ui.procedure.NumberProcedureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.tucky143.fusion.parts.PluginElementTypes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class ParticleModelGUI extends ModElementGUI<ParticleModel> {
    private final ValidationGroup page1group = new ValidationGroup();
    private final SearchableComboBox<String> particle;
    private final SearchableComboBox<Model> model;
    private final JComboBox<String> rendertype;
    private NumberProcedureSelector modelScale;
    private NumberProcedureSelector modelRotationX;
    private NumberProcedureSelector modelRotationY;
    private NumberProcedureSelector modelRotationZ;


    public ParticleModelGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        particle = new SearchableComboBox<>();
        model = new SearchableComboBox<>();
        rendertype = new JComboBox<>(new String[]{"Cutout", "Translucent", "Glowing", "End portal"});
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        this.model.setPreferredSize(new Dimension(400, 42));
        this.model.setRenderer(new ModelComboBoxRenderer());
        ComponentUtils.deriveFont(this.model, 16.0F);
        ComponentUtils.deriveFont(this.particle, 16.0F);
        ComponentUtils.deriveFont(this.rendertype, 16.0F);

        this.modelScale = new NumberProcedureSelector((IHelpContext)null, this.mcreator, L10N.t("elementgui.common.value", new Object[0]), AbstractProcedureSelector.Side.CLIENT, new JSpinner(new SpinnerNumberModel(1, 0.1, Integer.MAX_VALUE, 0.1)), 75, Dependency.fromString("x:number/y:number/z:number/world:world"));
        this.modelRotationX = new NumberProcedureSelector((IHelpContext)null, this.mcreator, L10N.t("elementgui.common.value", new Object[0]), AbstractProcedureSelector.Side.CLIENT, new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)), 75, Dependency.fromString("x:number/y:number/z:number/world:world"));
        this.modelRotationY = new NumberProcedureSelector((IHelpContext)null, this.mcreator, L10N.t("elementgui.common.value", new Object[0]), AbstractProcedureSelector.Side.CLIENT, new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)), 75, Dependency.fromString("x:number/y:number/z:number/world:world"));
        this.modelRotationZ = new NumberProcedureSelector((IHelpContext)null, this.mcreator, L10N.t("elementgui.common.value", new Object[0]), AbstractProcedureSelector.Side.CLIENT, new JSpinner(new SpinnerNumberModel(0, 0., Integer.MAX_VALUE, 1)), 75, Dependency.fromString("x:number/y:number/z:number/world:world"));

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);

        JPanel panels = new JPanel(new BorderLayout());
        panels.setOpaque(false);

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/particle"), L10N.label("elementgui.particlemodel.particle", new Object[0])));
        mainPanel.add(particle);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/model"), L10N.label("elementgui.particlemodel.model", new Object[0])));
        mainPanel.add(model);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/render_type"), L10N.label("elementgui.particlemodel.render_type", new Object[0])));
        mainPanel.add(rendertype);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.particlemodel.custom_model", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));

        JPanel properties = new JPanel(new GridLayout(4, 2, 0, 2));
        properties.setOpaque(false);

        properties.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/model_scale"), L10N.label("elementgui.particlemodel.model_scale", new Object[0])));
        properties.add(modelScale);
        properties.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/model_rotation"), L10N.label("elementgui.particlemodel.model_rotation_x", new Object[0])));
        properties.add(modelRotationX);
        properties.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/model_rotation"), L10N.label("elementgui.particlemodel.model_rotation_y", new Object[0])));
        properties.add(modelRotationY);
        properties.add(HelpUtils.wrapWithHelpButton(this.withEntry("particlemodel/model_rotation"), L10N.label("elementgui.particlemodel.model_rotation_z", new Object[0])));
        properties.add(modelRotationZ);
        properties.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.particlemodel.scale_rotation", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));

        panels.add("Center", PanelUtils.northAndCenterElement(mainPanel, properties));

        pane1.add(PanelUtils.totalCenterInPanel(panels));
        addPage(pane1);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(particle, this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == ModElementType.PARTICLE;
        }).map(ModElement::getName).collect(Collectors.toList()));
        ComboBoxUtil.updateComboBoxContents(this.model, (List<Model>)Model.getModels(this.mcreator.getWorkspace()).stream().filter((el) -> {
            return el.getType() == Model.Type.JAVA;
        }).collect(Collectors.toList()));
        modelScale.refreshListKeepSelected();
        modelRotationX.refreshListKeepSelected();
        modelRotationY.refreshListKeepSelected();
        modelRotationZ.refreshListKeepSelected();
    }

    public static boolean particleHasModel(String particle, ModElement particleModel, MCreator mcreator) {
        List<ParticleModel> particleModels = mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.PARTICLEMODEL && var != particleModel;
        }).map(model -> ((ParticleModel)model.getGeneratableElement())).toList();
        if (particle != null) {
            for (ParticleModel model : particleModels)
                if (model.particle.equals(particle))
                    return true;
        }
        return false;
    }

    protected void afterGeneratableElementStored() {
        try {
            mcreator.getGenerator().generateElement(mcreator.getWorkspace().getModElementByName(particle.getSelectedItem()).getGeneratableElement(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (particle.getSelectedItem() == null)
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.particlemodel.needs_particle", new Object[0]));
        if (model.getSelectedItem() == null)
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.particlemodel.needs_model", new Object[0]));
        if (particleHasModel(particle.getSelectedItem(), this.getModElement(), mcreator))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.particlemodel.has_model", new Object[0]));
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(ParticleModel particleModel) {
        particle.setSelectedItem(particleModel.particle);
        Model entityModel = particleModel.getModel();
        if (entityModel != null && entityModel.getType() != null && entityModel.getReadableName() != null) {
            this.model.setSelectedItem(entityModel);
        }
        rendertype.setSelectedItem(particleModel.rendertype);
        modelScale.setSelectedProcedure(particleModel.modelScale);
        modelRotationX.setSelectedProcedure(particleModel.modelRotationX);
        modelRotationY.setSelectedProcedure(particleModel.modelRotationY);
        modelRotationZ.setSelectedProcedure(particleModel.modelRotationZ);
    }

    public ParticleModel getElementFromGUI() {
        ParticleModel particleModel = new ParticleModel(this.modElement);
        particleModel.particle = particle.getSelectedItem();
        particleModel.model = this.model.getSelectedItem() != null ? ((Model)this.model.getSelectedItem()).getReadableName() : "";
        particleModel.rendertype = (String) rendertype.getSelectedItem();
        particleModel.modelScale = modelScale.getSelectedProcedure();
        particleModel.modelRotationX = modelRotationX.getSelectedProcedure();
        particleModel.modelRotationY = modelRotationY.getSelectedProcedure();
        particleModel.modelRotationZ = modelRotationZ.getSelectedProcedure();
        return particleModel;
    }

    @Override
    @Nullable
    public URI contextURL() throws URISyntaxException {
        return null;
    }

}
