package net.tucky143.fusion.ui.modgui.legacy;

import net.mcreator.minecraft.ElementUtil;
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
import net.tucky143.fusion.elements.legacy.LegacyAnvilRecipe;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class LegacyAnvilRecipeGUI extends ModElementGUI<LegacyAnvilRecipe> {
    private final Object[] noParams = new Object[0];

    private final ValidationGroup page1group = new ValidationGroup();
    private MCItemHolder leftitem;
    private MCItemHolder rightitem;
    private final JSpinner rightcost;
    private final JSpinner xpcost;
    private MCItemHolder output;

    public LegacyAnvilRecipeGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        rightcost = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
        xpcost = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        leftitem = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);
        rightitem = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);
        output = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);

        // Global
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.setOpaque(false);


        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 2, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/anvil/left_item"), L10N.label("elementGui.anvilRecipe.left_item", noParams)));
        leftitem.setPreferredSize(new Dimension(200, 34));
        mainPanel.add(leftitem);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/anvil/right_item"), L10N.label("elementGui.anvilRecipe.right_item", noParams)));
        rightitem.setPreferredSize(new Dimension(200, 34));
        mainPanel.add(rightitem);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/anvil/right_cost"), L10N.label("elementGui.anvilRecipe.right_item_cost", noParams)));
        rightcost.setPreferredSize(new Dimension(200, 34));
        mainPanel.add(rightcost);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/anvil/xp_cost"), L10N.label("elementGui.anvilRecipe.xp_cost", noParams)));
        xpcost.setPreferredSize(new Dimension(200, 34));
        mainPanel.add(xpcost);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/anvil/output"), L10N.label("elementGui.anvilRecipe.output", noParams)));
        output.setPreferredSize(new Dimension(200, 34));
        mainPanel.add(output);

        leftitem.setValidator(new MCItemHolderValidator(leftitem));
        page1group.addValidationElement(leftitem);
        rightitem.setValidator(new MCItemHolderValidator(rightitem));
        page1group.addValidationElement(rightitem);
        output.setValidator(new MCItemHolderValidator(output));
        page1group.addValidationElement(output);

        globalPanel.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(globalPanel);

    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(LegacyAnvilRecipe recipe) {
        leftitem.setBlock(recipe.leftitem);
        rightitem.setBlock(recipe.rightitem);
        rightcost.setValue(recipe.rightcost);
        xpcost.setValue(recipe.xpcost);
        output.setBlock(recipe.output);
    }

    public LegacyAnvilRecipe getElementFromGUI() {
        LegacyAnvilRecipe recipe = new LegacyAnvilRecipe(this.modElement);
        recipe.leftitem = leftitem.getBlock();
        recipe.rightitem = rightitem.getBlock();
        recipe.rightcost = (int) rightcost.getValue();
        recipe.xpcost = (int) xpcost.getValue();
        recipe.output = output.getBlock();
        return recipe;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
