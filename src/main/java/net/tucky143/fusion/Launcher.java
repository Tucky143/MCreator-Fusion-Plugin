package net.tucky143.fusion;

import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.generator.Generator;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.WorkspaceBuildStartedEvent;
import net.mcreator.plugin.events.ui.ModElementGUIEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.action.BasicAction;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.mcreator.ui.component.TransparentToolBar;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.modgui.BiomeGUI;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.workspace.AbstractWorkspacePanel;
import net.tucky143.fusion.parts.PluginActions;
import net.tucky143.fusion.parts.PluginElementTypes;
import net.tucky143.fusion.parts.PluginEventTriggers;
import net.tucky143.fusion.ui.dialogs.AddOtherTagsDialog;
import net.tucky143.fusion.ui.modgui.CuriosSlotGUI;

import javax.swing.*;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mcreator.element.parts.IWorkspaceDependent.LOG;

public class Launcher extends JavaPlugin {

	public static PluginActions ACTION_REGISTRY;
	public static Set<Plugin> PLUGIN_INSTANCE = new HashSet<>();
    public static final BlocklyEditorType CONFIG_EDITOR = new BlocklyEditorType("config", "cfg", "config_start");
    public static final List<String> DYEABLE_ARMOR_SUPPORTED_VERSIONS = List.of("neoforge-1.20.6", "forge-1.20.1");
    public static PluginActions ACTIONS;

	public static void disableComponent(ModElementGUI gui, Field field) throws Exception {
		field.setAccessible(true);
		((JComponent)field.get(gui)).setEnabled(false);
	}

	public Launcher(Plugin plugin) {
		super(plugin);

		addListener(PreGeneratorsLoadingEvent.class, e -> {
			PluginElementTypes.load();
            BlocklyLoader.INSTANCE.addBlockLoader(CONFIG_EDITOR);
        });

		addListener(ModElementGUIEvent.BeforeLoading.class, event -> SwingUtilities.invokeLater(() -> {
			PluginEventTriggers.dependencyWarning(event.getMCreator(), event.getModElementGUI());
			PluginEventTriggers.interceptProcedurePanel(event.getMCreator(), event.getModElementGUI());
		}));

		addListener(WorkspaceBuildStartedEvent.class, event -> {
			Generator currentGenerator = event.getMCreator().getGenerator();

			if (currentGenerator.getGeneratorConfiguration().getGeneratorFlavor() == GeneratorFlavor.FORGE) {
				GradlePropertiesUpdater.main(event.getMCreator().getWorkspaceFolder().toString());
			} else {
				GradlePropertiesUpdater.SetTrue(event.getMCreator().getWorkspaceFolder().toString());
			}
		});

		addListener(ModElementGUIEvent.AfterLoading.class, event -> {
			PluginEventTriggers.interceptProcedurePanel(event.getMCreator(), event.getModElementGUI());
		});

		addListener(MCreatorLoadedEvent.class, event -> {
			ACTION_REGISTRY = new PluginActions(event.getMCreator());
            SwingUtilities.invokeLater(() -> {
                try {
                    PluginEventTriggers.modifyMenus(event.getMCreator());
                } catch (Exception ignored) {}
            });
            AddOtherTagsDialog.init(event);

            Generator currentGenerator = event.getMCreator().getGenerator();
            if (currentGenerator.getGeneratorConfiguration().getGeneratorFlavor() == GeneratorFlavor.FORGE) {
                GradlePropertiesUpdater.main(event.getMCreator().getWorkspaceFolder().toString());
            } else {
                GradlePropertiesUpdater.SetTrue(event.getMCreator().getWorkspaceFolder().toString());
            }
		});

        this.addListener(ModElementGUIEvent.BeforeLoading.class, (e) -> {
            String generatorName = e.getMCreator().getGeneratorConfiguration().getGeneratorName();
            if (DYEABLE_ARMOR_SUPPORTED_VERSIONS.contains(generatorName)) {
                ACTIONS = new PluginActions(e.getMCreator());
                for(Component component : e.getMCreator().getWorkspacePanel().getComponents()) {
                    if (component instanceof TransparentToolBar) {
                        var bar = ((TransparentToolBar) component).add(ACTIONS.importArmorOverlayTexture);
                        JButton importOverlay = AbstractWorkspacePanel.createToolBarButton(L10N.t("workspace.textures.armor_overlay.import", new Object[0]), UIRES.get("16px.importarmor"));
                        bar.add(new JToolBar.Separator());
                        bar.add(importOverlay);
                    }
                }
            }

        });

        addListener(ModElementGUIEvent.AfterLoading.class, event -> {
            if (event.getModElementGUI() instanceof CuriosSlotGUI slotGUI) {
                    try {
                        disableComponent(slotGUI, slotGUI.getClass().getDeclaredField("items"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });

		LOG.info("Plugin was loaded");
	}
}