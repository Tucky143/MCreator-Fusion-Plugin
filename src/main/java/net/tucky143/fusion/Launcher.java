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
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.mcreator.ui.modgui.BiomeGUI;
import net.mcreator.ui.modgui.ModElementGUI;
import net.tucky143.fusion.parts.PluginActions;
import net.tucky143.fusion.parts.PluginElementTypes;
import net.tucky143.fusion.parts.PluginEventTriggers;
import net.tucky143.fusion.ui.modgui.EndBiomeGUI;

import javax.swing.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static net.mcreator.element.parts.IWorkspaceDependent.LOG;

public class Launcher extends JavaPlugin {

	public static PluginActions ACTION_REGISTRY;
	public static Set<Plugin> PLUGIN_INSTANCE = new HashSet<>();
    public static final BlocklyEditorType CONFIG_EDITOR = new BlocklyEditorType("config", "cfg", "config_start");

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

			if (event.getModElementGUI() instanceof BiomeGUI biome) {
				if (EndBiomeGUI.isEndBiome(biome.getElementFromGUI().getModElement().getName(), null, event.getMCreator())) {
					try {
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnBiome"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnBiomeNether"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnInCaves"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("underwaterBlock"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genTemperature"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genHumidity"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genContinentalness"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genErosion"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genWeirdness"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("treesPerChunk"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		addListener(MCreatorLoadedEvent.class, event -> {
			ACTION_REGISTRY = new PluginActions(event.getMCreator());
			SwingUtilities.invokeLater(() -> PluginEventTriggers.modifyMenus(event.getMCreator()));
		});

		LOG.info("Plugin was loaded");
	}
}