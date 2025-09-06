package net.tucky143.fusion.parts;

import javafx.stage.FileChooser;
import net.mcreator.io.FileIO;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.action.ActionRegistry;
import net.mcreator.ui.action.BasicAction;
import net.mcreator.ui.action.VisitURIAction;
import net.mcreator.ui.action.impl.workspace.resources.TextureAction;
import net.mcreator.ui.dialogs.file.FileChooserType;
import net.mcreator.ui.dialogs.file.FileDialogs;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.FilenameUtilsPatched;
import net.mcreator.util.image.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class PluginActions extends ActionRegistry {
    public final BasicAction importGeckoLibModel;
    public final BasicAction importDisplaySettings;
    public final BasicAction tutorial;
    public final BasicAction convertion_to_geckolib;
    public final BasicAction convertion_from_geckolib;
    public static BasicAction importArmorOverlayTexture;

    public PluginActions(MCreator mcreator) {
        super(mcreator);
        this.importGeckoLibModel = new PluginModelActions.GECKOLIB(this).setIcon(UIRES.get("16px.importgeckolibmodel"));
        this.importDisplaySettings = new PluginModelActions.DISPLAYSETTINGS(this).setIcon(UIRES.get("16px.importgeckolibmodel"));
        this.tutorial = (new VisitURIAction(this, L10N.t("action.tutorial", new Object[0]), "https://mcreator.net/forum/93274/tutorial-how-use-nerdys-geckolib-plugin-40-20224")).setIcon(new ImageIcon(ImageUtils.resizeAA(UIRES.get("16px.questionmark").getImage(), 14, 14)));
        this.convertion_to_geckolib = PluginDialogs.Entity2GeckoLib.getAction(this);
        this.convertion_from_geckolib = PluginDialogs.GeckoLib2Entity.getAction(this);
        this.importArmorOverlayTexture = (new TextureAction(this, L10N.t("workspace.textures.armor_overlay.import", new Object[0]), (e) -> this.importArmorOverlayTexture(mcreator), TextureType.ARMOR)).setIcon(UIRES.get("16px.importarmor"));
    }

    private void importArmorOverlayTexture(MCreator mcreator) {
        AtomicReference<File> f1 = new AtomicReference((Object)null);
        AtomicReference<File> f2 = new AtomicReference((Object)null);
        JPanel dialogContent = new JPanel(new GridLayout(3, 2, 20, 2));
        JButton b1 = new JButton("...");
        JButton b2 = new JButton("...");
        dialogContent.add(L10N.label("dialog.textures_import.armor_needs_two_files.overlay", new Object[0]));
        dialogContent.add(L10N.label("dialog.textures_import.armor_layers.overlay", new Object[0]));
        dialogContent.add(L10N.label("dialog.textures_import.armor_part_one.overlay", new Object[0]));
        dialogContent.add(b1);
        dialogContent.add(L10N.label("dialog.textures_import.armor_part_two.overlay", new Object[0]));
        dialogContent.add(b2);
        b1.addActionListener((ex) -> {
            File[] files = FileDialogs.getFileChooserDialog(mcreator, FileChooserType.OPEN, false, (String)null, new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Armor layer 1 overlay texture files", new String[]{"*layer_1_overlay.png"})});
            if (files != null && files.length > 0) {
                f1.set(files[0]);
                b1.setText(FilenameUtilsPatched.removeExtension(((File)f1.get()).getName()));
            }

        });
        b2.addActionListener((ex) -> {
            File[] files = FileDialogs.getFileChooserDialog(mcreator, FileChooserType.OPEN, false, (String)null, new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Armor layer 2 overlay texture files", new String[]{"*layer_2_overlay.png"})});
            if (files != null && files.length > 0) {
                f2.set(files[0]);
                b2.setText(FilenameUtilsPatched.removeExtension(((File)f2.get()).getName()));
            }

        });
        int ret = JOptionPane.showConfirmDialog(mcreator, dialogContent, L10N.t("dialog.textures_import.import_armor_texture.overlay", new Object[0]), 2, -1, (Icon)null);
        if (ret == 0) {
            if (f1.get() != null && f2.get() != null) {
                File armor$0 = new File(mcreator.getFolderManager().getTexturesFolder(TextureType.ARMOR), ((File)f1.get()).getName());
                File armor$1 = new File(mcreator.getFolderManager().getTexturesFolder(TextureType.ARMOR), ((File)f1.get()).getName().replace("layer_1", "layer_2"));
                FileIO.copyFile((File)f1.get(), armor$0);
                FileIO.copyFile((File)f2.get(), armor$1);
                JPanel currentTab = mcreator.getTabs().getCurrentTab().getContent();
                mcreator.reloadWorkspaceTabContents();
                if (currentTab instanceof ModElementGUI) {
                    ModElementGUI<?> e = (ModElementGUI<?>)currentTab;
                    e.reloadDataLists();
                }
            } else {
                JOptionPane.showMessageDialog(mcreator, L10N.t("dialog.textures_import.error_both_texture_files_not_selected.overlay", new Object[0]), (String)null, 0);
            }
        }
        }
}
