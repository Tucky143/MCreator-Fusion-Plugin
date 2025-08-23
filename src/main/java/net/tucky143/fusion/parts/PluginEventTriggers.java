package net.tucky143.fusion.parts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.mcreator.io.FileIO;
import net.mcreator.io.OS;
import net.mcreator.io.net.WebIO;
import net.mcreator.plugin.MCREvent;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.PluginUpdateInfo;
import net.mcreator.plugin.events.ui.BlocklyPanelRegisterJSObjects;
import net.mcreator.preferences.PreferencesManager;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.mcreator.ui.blockly.BlocklyPanel;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.component.util.ThreadUtil;
import net.mcreator.ui.dialogs.MCreatorDialog;
import net.mcreator.ui.init.BlocklyJavaScriptsLoader;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.modgui.ProcedureGUI;
import net.mcreator.ui.variants.modmaker.ModMaker;
import net.mcreator.util.DesktopUtils;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.VariableTypeLoader;
import net.tucky143.fusion.Launcher;
import net.tucky143.fusion.elements.GeckolibElement;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PluginEventTriggers {

    private static final Set<PluginUpdateInfo> pluginUpdates = new HashSet();

    private static void checkForPluginUpdates() {
        if (MCreatorApplication.isInternet) {
            pluginUpdates.addAll(Launcher.PLUGIN_INSTANCE.parallelStream().map((plugin) -> {
                try {
                    String updateJSON = WebIO.readURLToString(plugin.getInfo().getUpdateJSONURL());
                    JsonObject updateData = JsonParser.parseString(updateJSON).getAsJsonObject().get(plugin.getID()).getAsJsonObject();
                    String version = updateData.get("latest").getAsString();
                    if (!version.equals(plugin.getPluginVersion())) {
                        return new PluginUpdateInfo(plugin, version, updateData.has("changes") ? updateData.get("changes").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList() : null);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

                return null;
            }).filter(Objects::nonNull).toList());
        }

    }

    public static void dependencyWarning(MCreator mcreator, ModElementGUI modElement) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("geckolib") && modElement instanceof GeckolibElement) {
            StringBuilder stringBuilder = new StringBuilder(L10N.t("dialog.geckolib.enable_geckolib"));
            JOptionPane.showMessageDialog(mcreator, stringBuilder.toString(),
                    L10N.t("dialog.geckolib.error_no_dependency"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void interceptProcedurePanel(MCreator mcreator, ModElementGUI modElement) {
        if (modElement instanceof ProcedureGUI procedure) {
            // Use Platform.runLater for better JavaFX thread safety
            Platform.runLater(() -> {
                try {
                    Field panel = ProcedureGUI.class.getDeclaredField("blocklyPanel");
                    panel.setAccessible(true);
                    BlocklyPanel blocklyPanel = (BlocklyPanel) panel.get(procedure);

                    // Check if panel is null or already disposed
                    if (blocklyPanel == null) {
                        return;
                    }

                    Field loaded = BlocklyPanel.class.getDeclaredField("loaded");
                    loaded.setAccessible(true);
                    loaded.set(blocklyPanel, true);

                    Field engine = BlocklyPanel.class.getDeclaredField("webEngine");
                    engine.setAccessible(true);

                    Field listeners = BlocklyPanel.class.getDeclaredField("changeListeners");
                    listeners.setAccessible(true);
                    List<ChangeListener> listenerslist = (List<ChangeListener>) listeners.get(blocklyPanel);

                    JavabridgeReplacement javabridge = new JavabridgeReplacement(mcreator, () -> ThreadUtil.runOnSwingThread(
                            () -> listenerslist.forEach(listener -> listener.stateChanged(new ChangeEvent(blocklyPanel)))));

                    WebView browser = new WebView();
                    browser.setContextMenuEnabled(false);
                    Scene scene = new Scene(browser);
                    java.awt.Color bg = Theme.current().getSecondAltBackgroundColor();
                    scene.setFill(javafx.scene.paint.Color.rgb(bg.getRed(), bg.getGreen(), bg.getBlue()));
                    blocklyPanel.setScene(scene);

                    browser.getChildrenUnmodifiable().addListener(
                            (ListChangeListener<Node>) change -> browser.lookupAll(".scroll-bar")
                                    .forEach(bar -> bar.setVisible(false)));

                    WebEngine webEngine = browser.getEngine();

                    // Use weak references to prevent memory leaks and allow proper cleanup
                    WeakReference<BlocklyPanel> panelRef = new WeakReference<>(blocklyPanel);
                    WeakReference<WebEngine> engineRef = new WeakReference<>(webEngine);
                    WeakReference<Scene> sceneRef = new WeakReference<>(scene);
                    AtomicBoolean isDisposed = new AtomicBoolean(false);

                    // Hook into the panel's close operation to mark as disposed early
                    try {
                        // Try to detect when the panel is being closed
                        Field closeListeners = BlocklyPanel.class.getDeclaredField("closeListeners");
                        closeListeners.setAccessible(true);
                        List<Runnable> closeList = (List<Runnable>) closeListeners.get(blocklyPanel);
                        if (closeList != null) {
                            closeList.add(() -> {
                                isDisposed.set(true);
                                System.out.println("BlocklyPanel close detected, marking as disposed");
                            });
                        }
                    } catch (Exception exception) {
                        // Field might not exist, try alternative approach
                        try {
                            // Monitor the scene's window for close events
                            if (scene.getWindow() != null) {
                                scene.getWindow().setOnCloseRequest(e -> {
                                    isDisposed.set(true);
                                    System.out.println("Scene window close detected, marking as disposed");
                                });
                            }
                        } catch (Exception ex) {
                            // Fallback - just monitor the web engine state
                            System.out.println("Cannot hook into close events, using fallback monitoring");
                        }
                    }

                    webEngine.load(blocklyPanel.getClass().getResource("/blockly/blockly.html").toExternalForm());

                    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
                        // Early disposal check
                        if (isDisposed.get()) {
                            System.out.println("Listener fired but panel is disposed, skipping");
                            return;
                        }

                        // Check if components are still valid before proceeding
                        BlocklyPanel currentPanel = panelRef.get();
                        WebEngine currentEngine = engineRef.get();
                        Scene currentScene = sceneRef.get();

                        if (currentPanel == null || currentEngine == null || currentScene == null) {
                            isDisposed.set(true);
                            return;
                        }

                        // Additional safety checks for panel state
                        try {
                            // Check if the panel is still attached to a valid scene
                            if (currentPanel.getScene() == null || currentPanel.getScene() != currentScene) {
                                isDisposed.set(true);
                                return;
                            }

                            // Check if the scene is still valid and attached
                            if (currentScene.getWindow() == null) {
                                isDisposed.set(true);
                                return;
                            }

                        } catch (Exception e) {
                            // Panel or scene might be disposed
                            isDisposed.set(true);
                            return;
                        }

                        if (newState == Worker.State.SUCCEEDED) {
                            // Double-check disposal state before proceeding
                            if (isDisposed.get()) {
                                return;
                            }

                            try {
                                // Verify document exists and is accessible
                                if (currentEngine.getDocument() == null) {
                                    return;
                                }

                                // Test if document is still in valid state with minimal script
                                Object docState = currentEngine.executeScript("(function(){ try { return document.readyState; } catch(e) { return 'error'; } })()");
                                if (!"complete".equals(docState) && !"interactive".equals(docState)) {
                                    System.out.println("Document not ready, state: " + docState);
                                    return;
                                }

                                // Final check before initialization
                                if (isDisposed.get()) {
                                    return;
                                }

                                // Proceed with initialization
                                initializeBlocklyPanel(currentEngine, currentPanel, currentScene, javabridge, isDisposed);

                            } catch (JSException e) {
                                // Document is not accessible or invalid, skip initialization
                                if (e.getMessage().contains("container is not in current document")) {
                                    isDisposed.set(true);
                                    System.err.println("Container disposed during initialization, marking as disposed");
                                } else {
                                    System.err.println("JavaScript error during initialization: " + e.getMessage());
                                }
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        } else if (newState == Worker.State.FAILED || newState == Worker.State.CANCELLED) {
                            isDisposed.set(true);
                        }
                    });

                    // Add cleanup listener for when panel is closed
                    try {
                        // Hook into panel cleanup if possible
                        if (blocklyPanel.getScene() != null) {
                            blocklyPanel.getScene().getWindow().setOnCloseRequest(e -> isDisposed.set(true));
                        }
                    } catch (Exception e) {
                        // Ignore if not supported
                    }

                    engine.set(blocklyPanel, webEngine);
                    panel.set(procedure, blocklyPanel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void initializeBlocklyPanel(WebEngine webEngine, BlocklyPanel blocklyPanel, Scene scene, JavabridgeReplacement javabridge, AtomicBoolean isDisposed) {
        try {
            // Check disposal state before each major operation
            if (isDisposed.get()) return;

            // Wrap all DOM operations in try-catch to handle disposal gracefully
            safeExecuteScript(webEngine, () -> {
                // load CSS from file to select proper style for OS
                Element styleNode = webEngine.getDocument().createElement("style");
                String css = FileIO.readResourceToString("/blockly/css/mcreator_blockly.css");
                if (PluginLoader.INSTANCE.getResourceAsStream(
                        "themes/" + Theme.current().getID() + "/styles/blockly.css") != null) {
                    css += FileIO.readResourceToString(PluginLoader.INSTANCE,
                            "/themes/" + Theme.current().getID() + "/styles/blockly.css");
                } else {
                    css += FileIO.readResourceToString(PluginLoader.INSTANCE,
                            "/themes/default_dark/styles/blockly.css");
                }
                if (PreferencesManager.PREFERENCES.blockly.transparentBackground.get()
                        && OS.getOS() == OS.WINDOWS) {

                    try {
                        Method comps = BlocklyPanel.class.getDeclaredMethod("makeComponentsTransparent", Scene.class);
                        comps.setAccessible(true);
                        comps.invoke(blocklyPanel, scene);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    css += FileIO.readResourceToString("/blockly/css/mcreator_blockly_transparent.css");
                }
                //remove font declaration if property set so
                if (PreferencesManager.PREFERENCES.blockly.legacyFont.get()) {
                    css = css.replace("font-family: sans-serif;", "");
                }
                Text styleContent = webEngine.getDocument().createTextNode(css);
                styleNode.appendChild(styleContent);
                webEngine.getDocument().getDocumentElement().getElementsByTagName("head").item(0)
                        .appendChild(styleNode);
            }, isDisposed);

            if (isDisposed.get()) return;

            // Register JS bridge with safety checks
            safeExecuteScript(webEngine, () -> {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javabridge", javabridge);
                window.setMember("editorType", BlocklyEditorType.PROCEDURE.registryName());

                // allow plugins to register additional JS objects
                Map<String, Object> domWindowMembers = new HashMap<>();
                MCREvent.event(new BlocklyPanelRegisterJSObjects(blocklyPanel, domWindowMembers));
                domWindowMembers.forEach(window::setMember);
            }, isDisposed);

            if (isDisposed.get()) return;

            // Execute Blockly preferences and scripts in proper order
            safeExecuteScript(webEngine, () -> {
                // @formatter:off
                webEngine.executeScript("var MCR_BLOCKLY_PREF = { "
                        + "'comments' : " + PreferencesManager.PREFERENCES.blockly.enableComments.get() + ","
                        + "'renderer' : '" + PreferencesManager.PREFERENCES.blockly.blockRenderer.get().toLowerCase(Locale.ENGLISH) + "',"
                        + "'collapse' : " + PreferencesManager.PREFERENCES.blockly.enableCollapse.get() + ","
                        + "'trashcan' : " + PreferencesManager.PREFERENCES.blockly.enableTrashcan.get() + ","
                        + "'maxScale' : " + PreferencesManager.PREFERENCES.blockly.maxScale.get() / 100.0 + ","
                        + "'minScale' : " + PreferencesManager.PREFERENCES.blockly.minScale.get() / 100.0 + ","
                        + "'scaleSpeed' : " + PreferencesManager.PREFERENCES.blockly.scaleSpeed.get() / 100.0 + ","
                        + "'saturation' :" + PreferencesManager.PREFERENCES.blockly.colorSaturation.get() / 100.0 + ","
                        + "'value' :" + PreferencesManager.PREFERENCES.blockly.colorValue.get() / 100.0
                        + " };");
                // @formatter:on

                // Blockly core - load in correct order
                webEngine.executeScript(FileIO.readResourceToString("/jsdist/blockly_compressed.js"));
                webEngine.executeScript(
                        FileIO.readResourceToString("/jsdist/msg/" + L10N.getBlocklyLangName() + ".js"));
                webEngine.executeScript(FileIO.readResourceToString("/jsdist/blocks_compressed.js"));

                // Blockly MCreator definitions - this contains workspaceToXML
                webEngine.executeScript(FileIO.readResourceToString("/blockly/js/mcreator_blockly.js"));

                // Load JavaScript files from plugins
                for (String script : BlocklyJavaScriptsLoader.INSTANCE.getScripts())
                    webEngine.executeScript(script);

                //JS code generation for custom variables
                webEngine.executeScript(VariableTypeLoader.INSTANCE.getVariableBlocklyJS());

                // Verify that essential functions are defined
                try {
                    Object result = webEngine.executeScript("typeof workspaceToXML");
                    if (!"function".equals(result)) {
                        System.err.println("workspaceToXML function not properly loaded, type: " + result);
                        // Try to define a fallback
                        webEngine.executeScript(
                                "if (typeof workspaceToXML === 'undefined') {" +
                                        "  window.workspaceToXML = function() {" +
                                        "    if (typeof Blockly !== 'undefined' && Blockly.getMainWorkspace) {" +
                                        "      var workspace = Blockly.getMainWorkspace();" +
                                        "      if (workspace) {" +
                                        "        return Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(workspace));" +
                                        "      }" +
                                        "    }" +
                                        "    return '<xml></xml>';" +
                                        "  };" +
                                        "}"
                        );
                    }

                    // Also check for other essential functions
                    String[] essentialFunctions = {"workspaceToXML", "XMLToWorkspace", "getVarType", "javabridge"};
                    for (String func : essentialFunctions) {
                        Object funcResult = webEngine.executeScript("typeof " + func);
                        System.out.println("Function " + func + " type: " + funcResult);
                    }

                } catch (Exception e) {
                    System.err.println("Error verifying Blockly functions: " + e.getMessage());
                }
            });

            // Run post-load tasks
            try {
                Field tasks = BlocklyPanel.class.getDeclaredField("runAfterLoaded");
                tasks.setAccessible(true);
                List<Runnable> tasklist = (List<Runnable>) tasks.get(blocklyPanel);
                tasklist.forEach(task -> Platform.runLater(task)); // Use Platform.runLater instead of ThreadUtil
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void safeExecuteScript(WebEngine webEngine, Runnable scriptExecution) {
        safeExecuteScript(webEngine, scriptExecution, null);
    }

    private static void safeExecuteScript(WebEngine webEngine, Runnable scriptExecution, AtomicBoolean isDisposed) {
        try {
            // Check disposal state if provided
            if (isDisposed != null && isDisposed.get()) {
                return;
            }

            // Verify engine and document are still valid
            if (webEngine.getDocument() == null) {
                if (isDisposed != null) isDisposed.set(true);
                return;
            }

            // Test if document is accessible with a minimal, safe test
            Object testResult = webEngine.executeScript("(function(){ try { return 'ok'; } catch(e) { return 'error'; } })()");
            if (!"ok".equals(testResult)) {
                if (isDisposed != null) isDisposed.set(true);
                return;
            }

            // Execute the actual script
            scriptExecution.run();

        } catch (JSException e) {
            // Container not in document or other JS errors
            if (e.getMessage().contains("container is not in current document") ||
                    e.getMessage().contains("not in current document")) {
                if (isDisposed != null) isDisposed.set(true);
                System.err.println("Container disposed during script execution: " + e.getMessage());
            } else {
                System.err.println("Script execution failed: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void forceCheckUpdates(MCreator mcreator) {
        checkForPluginUpdates();
        Collection<PluginUpdateInfo> pluginUpdateInfos = pluginUpdates;
        if (!pluginUpdateInfos.isEmpty()) {
            JPanel pan = new JPanel(new BorderLayout(10, 15));
            JPanel plugins = new JPanel(new GridLayout(0, 1, 10, 10));
            pan.add("North", new JScrollPane(PanelUtils.pullElementUp(plugins)));
            pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pan.setPreferredSize(new Dimension(560, 250));
            Iterator var5 = pluginUpdateInfos.iterator();

            while(var5.hasNext()) {
                PluginUpdateInfo pluginUpdateInfo = (PluginUpdateInfo)var5.next();
                StringBuilder usb = new StringBuilder(L10N.t("dialog.plugin_update_notify.version_message", new Object[]{pluginUpdateInfo.plugin().getInfo().getName(), pluginUpdateInfo.plugin().getInfo().getVersion(), pluginUpdateInfo.newVersion()}));
                if (pluginUpdateInfo.recentChanges() != null) {
                    usb.append("<br>").append(L10N.t("dialog.plugin_update_notify.changes_message", new Object[0])).append("<ul>");
                    Iterator var8 = pluginUpdateInfo.recentChanges().iterator();

                    while(var8.hasNext()) {
                        String change = (String)var8.next();
                        usb.append("<li>").append(change).append("</li>");
                    }
                }

                JLabel label = new JLabel(usb.toString());
                JButton update = L10N.button("dialog.plugin_update_notify.update", new Object[0]);
                update.addActionListener((e) -> {
                    DesktopUtils.browseSafe("https://mcreator.net/node/" + pluginUpdateInfo.plugin().getInfo().getPluginPageID());
                });
                plugins.add(PanelUtils.westAndEastElement(label, PanelUtils.join(new Component[]{update})));
            }

            MCreatorDialog dialog = new MCreatorDialog(mcreator, L10N.t("dialog.plugin_update_notify.update_title", new Object[0]));
            dialog.setSize(700, 200);
            dialog.setLocationRelativeTo(mcreator);
            dialog.setModal(true);
            JButton close = L10N.button("dialog.plugin_update_notify.close", new Object[0]);
            close.addActionListener((e) -> {
                dialog.setVisible(false);
            });
            dialog.add("Center", PanelUtils.centerAndSouthElement(pan, PanelUtils.join(new Component[]{close})));
            dialog.setVisible(true);
        }
    }

    public static void modifyMenus(MCreator mcreator) {
        JMenu geckolib = L10N.menu("menubar.geckolib");
        geckolib.setMnemonic('R');
        geckolib.setIcon(new ImageIcon(ImageUtils.resizeAA(UIRES.get("16px.geckolibicon").getImage(), 17, 17)));
        geckolib.add(Launcher.ACTION_REGISTRY.importGeckoLibModel);
        geckolib.add(Launcher.ACTION_REGISTRY.importDisplaySettings);
        geckolib.addSeparator();
        geckolib.add(Launcher.ACTION_REGISTRY.convertion_to_geckolib);
        geckolib.add(Launcher.ACTION_REGISTRY.convertion_from_geckolib);
        geckolib.addSeparator();
        geckolib.add(Launcher.ACTION_REGISTRY.tutorial);

        if (mcreator instanceof ModMaker modmaker) {
            PluginPanelGeckolib panel = new PluginPanelGeckolib(modmaker.getWorkspacePanel());
            panel.setOpaque(false);

            modmaker.getWorkspacePanel().resourcesPan.addResourcesTab(L10N.t("menubar.geckolib", new Object[0]), panel);
            mcreator.getMainMenuBar().add(geckolib);
        }

        forceCheckUpdates(mcreator);
    }

}