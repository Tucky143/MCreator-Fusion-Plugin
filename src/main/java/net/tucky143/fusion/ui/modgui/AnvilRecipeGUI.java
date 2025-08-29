package net.tucky143.fusion.ui.modgui;

import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.AnvilRecipe;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class AnvilRecipeGUI extends ModElementGUI<AnvilRecipe> {

    private final ValidationGroup page1group = new ValidationGroup();
    private MCItemHolder leftitem;
    private MCItemHolder rightitem;
    private final JSpinner rightcost;
    private final JSpinner xpcost;
    private MCItemHolder output;

    public AnvilRecipeGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
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

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/left_item"), L10N.label("elementgui.anvilrecipe.left_item", new Object[0])));
        mainPanel.add(leftitem);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/right_item"), L10N.label("elementgui.anvilrecipe.right_item", new Object[0])));
        mainPanel.add(rightitem);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/right_cost"), L10N.label("elementgui.anvilrecipe.right_item_cost", new Object[0])));
        mainPanel.add(rightcost);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/xp_cost"), L10N.label("elementgui.anvilrecipe.xp_cost", new Object[0])));
        mainPanel.add(xpcost);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/output"), L10N.label("elementgui.anvilrecipe.output", new Object[0])));
        mainPanel.add(output);

        leftitem.setValidator(new MCItemHolderValidator(leftitem));
        page1group.addValidationElement(leftitem);
        rightitem.setValidator(new MCItemHolderValidator(rightitem));
        page1group.addValidationElement(rightitem);
        output.setValidator(new MCItemHolderValidator(output));
        page1group.addValidationElement(output);

        pane1.add(PanelUtils.totalCenterInPanel(mainPanel));
        addPage(pane1).validate(page1group);

    }

    public void openInEditingMode(AnvilRecipe recipe) {
        leftitem.setBlock(recipe.leftitem);
        rightitem.setBlock(recipe.rightitem);
        rightcost.setValue(recipe.rightcost);
        xpcost.setValue(recipe.xpcost);
        output.setBlock(recipe.output);
    }

    public AnvilRecipe getElementFromGUI() {
        AnvilRecipe recipe = new AnvilRecipe(this.modElement);
        recipe.leftitem = leftitem.getBlock();
        recipe.rightitem = rightitem.getBlock();
        recipe.rightcost = (int) rightcost.getValue();
        recipe.xpcost = (int) xpcost.getValue();
        recipe.output = output.getBlock();
        return recipe;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

}
