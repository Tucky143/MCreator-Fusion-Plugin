package net.tucky143.fusion.ui.modgui;

import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.util.ListUtils;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.elements.JeiRecipe;
import net.tucky143.fusion.elements.JeiRecipeType;
import net.tucky143.fusion.parts.MCItemListFieldMulti;
import net.tucky143.fusion.parts.PluginElementTypes;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class JeiRecipeGUI extends ModElementGUI<JeiRecipe> {
    private final SearchableComboBox<String> category = new SearchableComboBox<>();
    private MCItemHolder result;
    private final JSpinner count = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
    private MCItemListFieldMulti ingredients;
    private int ingredientCount = 0;

    public JeiRecipeGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        ingredients = new MCItemListFieldMulti(this.mcreator, ElementUtil::loadBlocksAndItemsAndTags, false, true);
        result = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);

        this.category.addActionListener((e) -> {
            if (this.category.getSelectedItem() != null) {
                if (!this.category.getSelectedItem().equals("No category")) {
                    ingredientCount = this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
                                return var.getType() == PluginElementTypes.JEIRECIPETYPE;
                            }).map(type -> (JeiRecipeType) type.getGeneratableElement())
                            .collect(Collectors.toList())
                            .stream()
                            .filter((var) -> {
                                return var.getModElement().getRegistryName().equals(this.category.getSelectedItem());
                            })
                            .findFirst()
                            .get()
                            .getIngredientCount();
                }
            }
        });

        JPanel pane3 = new JPanel(new BorderLayout());
        pane3.setOpaque(false);

        JPanel mainPane = new JPanel(new GridLayout(2, 1, 2, 2));
        mainPane.setOpaque(false);

        JPanel ingredientsPane = new JPanel(new BorderLayout());
        ingredientsPane.setOpaque(false);
        ingredientsPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.jeirecipe.ingredients", new Object[0]), 2, 0, this.getFont(), Theme.current().getForegroundColor()));
        ingredients.setPreferredSize(new Dimension(500, 30));
        ingredientsPane.add(PanelUtils.totalCenterInPanel(ingredients));

        JPanel selp = new JPanel(new GridLayout(3, 2, 10, 10));
        selp.setOpaque(false);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_category"), L10N.label("elementgui.jeirecipe.category", new Object[0])));
        selp.add(category);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/recipe_result"), L10N.label("elementgui.jeirecipe.result", new Object[0])));
        selp.add(result);
        selp.add(HelpUtils.wrapWithHelpButton(this.withEntry("jei/result_count"), L10N.label("elementgui.jeirecipe.result_count", new Object[0])));
        selp.add(count);

        mainPane.add(selp);
        mainPane.add(ingredientsPane);

        pane3.add(PanelUtils.totalCenterInPanel(mainPane));
        this.addPage(pane3);

    }

    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(this.category, ListUtils.merge(Collections.singleton("No category"), (Collection)this.mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.JEIRECIPETYPE;
        }).map(ModElement::getRegistryName).collect(Collectors.toList())), "No category");
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("jei"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jei.needs_api", new Object[0]));
        else if (category.getSelectedItem().equals("No category"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeirecipe.no_category", new Object[0]));
        else if (ingredients.getListElements().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeirecipe.no_ingredients", new Object[0]));
        else if (result.getBlock().isEmpty())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeirecipe.no_result", new Object[0]));
        else if (ingredients.getListElements().size() != ingredientCount) {
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.jeirecipe.ingredient_mismatch", new Object[0]) + " (" + ingredients.getListElements().size() + "/" + (ingredientCount) + ")");
        }
        return new AggregatedValidationResult.PASS();
    }

    public void openInEditingMode(JeiRecipe tab) {
        category.setSelectedItem(tab.category);
        result.setBlock(tab.result);
        count.setValue(tab.count);
        ingredients.setListElements(tab.ingredients);
    }

    public JeiRecipe getElementFromGUI() {
        JeiRecipe tab = new JeiRecipe(this.modElement);
        tab.category = category.getSelectedItem();
        tab.result = result.getBlock();
        tab.count = (int) count.getValue();
        tab.ingredients = ingredients.getListElements();
        return tab;
    }

    @Override public @Nullable URI contextURL() throws URISyntaxException {
        return null;
    }

}
