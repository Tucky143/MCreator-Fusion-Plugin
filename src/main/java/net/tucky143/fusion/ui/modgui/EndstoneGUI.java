package net.tucky143.fusion.ui.modgui;

import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.Endstone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class EndstoneGUI extends ModElementGUI<Endstone> {
    private final ValidationGroup page1group = new ValidationGroup();
    private MCItemHolder block;

    public EndstoneGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        block = new MCItemHolder(mcreator, ElementUtil::loadBlocks);

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(1, 1, 0, 2));
        mainPanel.setOpaque(false);

        JPanel blockPanel = PanelUtils.join(new Component[]{HelpUtils.wrapWithHelpButton(this.withEntry("endbiome/endstone"), L10N.label("elementgui.endstone.block", new Object[0])), PanelUtils.centerInPanel(block)});
        mainPanel.add(blockPanel);

        block.setValidator(new MCItemHolderValidator(block));
        page1group.addValidationElement(block);

        pane1.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(pane1);
    }

    protected AggregatedValidationResult validatePage(int i) {
        if (PluginLoader.INSTANCE.getPlugins().stream().filter((plugin) -> { return plugin.getID().equals("forge_mixins"); }).toList().isEmpty() && mcreator.getGenerator().getGeneratorConfiguration().getGeneratorFlavor() != GeneratorFlavor.FABRIC)
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.endstone.needs_mixins", new Object[0]));
        return new AggregatedValidationResult(page1group);
    }

    protected void openInEditingMode(Endstone endstone) {
        block.setBlock(endstone.block);
    }

    public Endstone getElementFromGUI() {
        Endstone endstone = new Endstone(this.modElement);
        endstone.block = block.getBlock();
        return endstone;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

}
