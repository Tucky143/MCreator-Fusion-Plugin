package net.tucky143.fusion.ui.modgui;

import net.mcreator.blockly.BlocklyCompileNote;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.blockly.data.ToolboxBlock;
import net.mcreator.blockly.data.ToolboxType;
import net.mcreator.blockly.java.BlocklyToJava;
import net.mcreator.generator.blockly.BlocklyBlockCodeGenerator;
import net.mcreator.generator.blockly.OutputBlockCodeGenerator;
import net.mcreator.generator.blockly.ProceduralBlockCodeGenerator;
import net.mcreator.generator.template.TemplateGenerator;
import net.mcreator.generator.template.TemplateGeneratorException;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.blockly.BlocklyEditorToolbar;
import net.mcreator.ui.blockly.BlocklyPanel;
import net.mcreator.ui.blockly.CompileNotesPanel;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.IBlocklyPanelHolder;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.elements.VariableElement;
import net.tucky143.fusion.Launcher;
import net.tucky143.fusion.elements.Config;
import net.mcreator.ui.modgui.ModElementGUI;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class ConfigGUI extends ModElementGUI<Config> implements IBlocklyPanelHolder {
    private final VTextField file = new VTextField(13);
    private final JComboBox<String> configType = new JComboBox(new String[]{"COMMON", "SERVER", "CLIENT"});

    private final ValidationGroup pages = new ValidationGroup();
    private final CompileNotesPanel compileNotesPanel = new CompileNotesPanel();
    private final List<BlocklyChangedListener> blocklyChangedListeners = new ArrayList<>();
    private BlocklyPanel blocklyPanel;
    private Map<String, ToolboxBlock> externalBlocks;

    public ConfigGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        this.externalBlocks = BlocklyLoader.INSTANCE.getBlockLoader(Launcher.CONFIG_EDITOR).getDefinedBlocks();
        this.blocklyPanel = new BlocklyPanel(this.mcreator, Launcher.CONFIG_EDITOR);
        this.blocklyPanel.addTaskToRunAfterLoaded(() -> {
            BlocklyLoader.INSTANCE.getBlockLoader(Launcher.CONFIG_EDITOR).loadBlocksAndCategoriesInPanel(this.blocklyPanel, ToolboxType.EMPTY);
            Iterator it = this.mcreator.getWorkspace().getVariableElements().iterator();

            while(it.hasNext()) {
                VariableElement variable = (VariableElement) it.next();
                this.blocklyPanel.addGlobalVariable(variable.getName(), variable.getType().getBlocklyVariableType());
            }

            blocklyPanel.addChangeListener(
                    changeEvent -> new Thread(() -> regenerateConfig(changeEvent.getSource() instanceof BlocklyPanel), "ConfigRegenerate").start());

            if (!this.isEditingMode()) {
                this.blocklyPanel.setXML("<xml xmlns=\"https://developers.google.com/blockly/xml\"><block type=\"config_start\" deletable=\"false\" x=\"40\" y=\"40\"></block></xml>");
            }

        });

        JPanel pane3 = new JPanel(new BorderLayout());
        pane3.setOpaque(false);

        this.file.setValidator(new TextFieldValidator(this.file, L10N.t("elementgui.config.notempty", new Object[0])));
        this.file.enableRealtimeValidation();

        JPanel northPanel = new JPanel(new GridLayout(2, 2, 10, 2));
        northPanel.setOpaque(false);
        northPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("config/name"), L10N.label("elementgui.config.name", new Object[0])));
        northPanel.add(this.file);
        northPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("config/type"), L10N.label("elementgui.config.type", new Object[0])));
        northPanel.add(this.configType);

        JPanel cfgpan = new JPanel(new BorderLayout(0, 5));
        cfgpan.setOpaque(false);
        cfgpan.add("North", northPanel);

        JPanel bpb = new JPanel(new GridLayout());
        bpb.setOpaque(false);
        bpb.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.config.editor", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));
        BlocklyEditorToolbar blocklyEditorToolbar = new BlocklyEditorToolbar(this.mcreator, Launcher.CONFIG_EDITOR, this.blocklyPanel);
        blocklyEditorToolbar.setTemplateLibButtonWidth(157);
        bpb.add(PanelUtils.northAndCenterElement(blocklyEditorToolbar, blocklyPanel));
        cfgpan.add("Center", bpb);
        cfgpan.add("South", compileNotesPanel);

        this.blocklyPanel.setPreferredSize(new Dimension(150, 150));

        JPanel merged = new JPanel(new BorderLayout(0, 0));
        merged.setOpaque(false);
        merged.add("Center", ComponentUtils.applyPadding(cfgpan, 10, true, true, true, true));


        this.addPage(merged);
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new IValidable[]{this.file});
    }

    public void reloadDataLists() {
        super.reloadDataLists();
    }

    private synchronized void regenerateConfig(boolean jsEventTriggeredChange) {
        BlocklyBlockCodeGenerator blocklyBlockCodeGenerator = new BlocklyBlockCodeGenerator(this.externalBlocks, this.mcreator.getGeneratorStats().getBlocklyBlocks(Launcher.CONFIG_EDITOR));

        BlocklyToJava blocklyToJava;
        try {
            blocklyToJava = new BlocklyToJava(this.mcreator.getWorkspace(), this.modElement, Launcher.CONFIG_EDITOR, this.blocklyPanel.getXML(), (TemplateGenerator)null, new ProceduralBlockCodeGenerator(blocklyBlockCodeGenerator), new OutputBlockCodeGenerator(blocklyBlockCodeGenerator));
        } catch (TemplateGeneratorException var4) {
            return;
        }

        List<BlocklyCompileNote> compileNotesArrayList = blocklyToJava.getCompileNotes();
        SwingUtilities.invokeLater(() -> {
            this.compileNotesPanel.updateCompileNotes(compileNotesArrayList);
            this.blocklyChangedListeners.forEach((listener) -> {
                listener.blocklyChanged(this.blocklyPanel);
            });
        });
    }

    public void addBlocklyChangedListener(BlocklyChangedListener blocklyChangedListener) {
        this.blocklyChangedListeners.add(blocklyChangedListener);
    }

    public Set<BlocklyPanel> getBlocklyPanels() {
        return Set.of(this.blocklyPanel);
    }


    public void openInEditingMode(Config config) {
        this.file.setText(config.file);
        this.configType.setSelectedIndex(config.configType);
        this.blocklyPanel.addTaskToRunAfterLoaded(() -> {
            if (config.pools == null)
                this.blocklyPanel.setXML(config.config);
            else
                this.blocklyPanel.setXML(config.listsToXML());
            this.regenerateConfig(false);
        });
    }

    public Config getElementFromGUI() {
        Config config = new Config(this.modElement);
        config.file = this.file.getText();
        config.configType = this.configType.getSelectedIndex();
        config.pools = null;
        config.config = this.blocklyPanel.getXML();

        return config;
    }

    @Override public URI contextURL() throws URISyntaxException {
        return new URI(MCreatorApplication.SERVER_DOMAIN + "/wiki/gui-editor");
    }

}