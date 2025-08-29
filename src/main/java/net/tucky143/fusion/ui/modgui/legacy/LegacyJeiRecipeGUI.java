package net.tucky143.fusion.ui.modgui.legacy;

import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.util.ListUtils;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.legacy.LegacyJeiRecipe;
import net.tucky143.fusion.parts.PluginElementTypes;
import net.tucky143.fusion.parts.legacy.JeiRecipeList;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class LegacyJeiRecipeGUI extends ModElementGUI<LegacyJeiRecipe> {
    private final Object[] noParams = new Object[0];

    private final SearchableComboBox<String> category;
    private MCItemHolder output;
    private final JSpinner outputAmount;
    private JeiRecipeList recipeList;

    public LegacyJeiRecipeGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        category = new SearchableComboBox<>();
        outputAmount = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
        recipeList = new JeiRecipeList(this.mcreator, this);

        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        output = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.setOpaque(false);

        // Output
        JPanel outputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        outputPanel.setOpaque(false);
        outputPanel.setPreferredSize(new Dimension(400, 100));
        outputPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe/category"), L10N.label("elementGui.jeiRecipe.category", noParams)));
        outputPanel.add(category);
        outputPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe/output"), L10N.label("elementGui.jeiRecipe.output", noParams)));
        outputPanel.add(output);
        outputPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe/output_amount"), L10N.label("elementGui.jeiRecipe.output_amount", noParams)));
        outputPanel.add(outputAmount);

        // Recipe ingredients
        JComponent recipePanel = PanelUtils.northAndCenterElement(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe/entries"), L10N.label("elementGui.jeiRecipe.entries", noParams)), this.recipeList);
        recipePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        globalPanel.add(PanelUtils.northAndCenterElement(PanelUtils.join(0, new Component[]{outputPanel}), recipePanel));

        this.output.setValidator(new MCItemHolderValidator(this.output));

        addPage(L10N.t("elementgui.common.page_properties", noParams), globalPanel);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        recipeList.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(this.category, ListUtils.merge(Collections.singleton("No category"), (Collection)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.LEGACYJEIRECIPETYPE;
        }).map(ModElement::getRegistryName).collect(Collectors.toList())), "No category");
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("jei")) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jei.needs_api", noParams));
        } else if (category.getSelectedItem().equals("No category")) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jeiRecipe.no_category", noParams));
        } else if (output.getBlock().isEmpty()) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementGui.jeiRecipe.no_result", noParams));
        }
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void openInEditingMode(LegacyJeiRecipe recipe) {
        category.setSelectedItem(recipe.category);
        output.setBlock(recipe.output);
        outputAmount.setValue(recipe.outputAmount);
        recipeList.setEntries(recipe.inputs);
    }

    public LegacyJeiRecipe getElementFromGUI() {
        LegacyJeiRecipe recipe = new LegacyJeiRecipe(this.modElement);
        recipe.category = category.getSelectedItem();
        recipe.output = output.getBlock();
        recipe.outputAmount = (int) outputAmount.getValue();
        recipe.inputs = recipeList.getEntries();
        return recipe;
    }

    @Override
    public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }
}
