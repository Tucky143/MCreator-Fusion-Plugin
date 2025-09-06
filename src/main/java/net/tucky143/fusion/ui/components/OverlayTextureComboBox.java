//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.ui.components;

import java.awt.Component;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.FilenameUtilsPatched;
import net.mcreator.util.ListUtils;
import net.mcreator.util.image.EmptyIcon;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.resources.Texture;
import net.tucky143.fusion.Launcher;
import net.tucky143.fusion.parts.PluginActions;

public class OverlayTextureComboBox extends JPanel {
    private final Texture empty;
    private final MCreator mcreator;
    private final TextureType textureType;
    private final boolean showEmpty;
    private final String defaultTextureName;
    private boolean addPNGExtension;
    private final SearchableComboBox<Texture> comboBox;

    public OverlayTextureComboBox(MCreator mcreator, TextureType textureType) {
        this.mcreator = mcreator;
        this.textureType = textureType;
        this.showEmpty = true;
        this.defaultTextureName = "";
        this.empty = new Texture.Dummy(textureType, this.defaultTextureName);
        this.comboBox = new SearchableComboBox();
        this.comboBox.setRenderer(new Renderer());
        this.comboBox.setPrototypeDisplayValue(new Texture.Dummy(textureType, "XXXXXXXXXXXXXXXXXXXXXXXXX"));
        ComponentUtils.deriveFont(this.comboBox, 16.0F);
        JButton importTexture = new JButton(UIRES.get("18px.add"));
        importTexture.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, UIManager.getColor("Component.borderColor")), BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        importTexture.setOpaque(false);
        importTexture.setAction(PluginActions.importArmorOverlayTexture);
        importTexture.addActionListener((e) -> this.reload());
        this.add(this.comboBox, "Center");
        this.add(importTexture, "East");
        this.reload();
    }

    public void reload() {
        if (this.showEmpty) {
            ComboBoxUtil.updateComboBoxContents(this.comboBox, ListUtils.merge(Collections.singleton(this.empty), this.mcreator.getFolderManager().getTexturesList(TextureType.ARMOR).parallelStream().map((e) -> Texture.fromName(this.mcreator.getWorkspace(), TextureType.ARMOR, e.getName())).filter(Objects::nonNull).filter((e) -> e.getTextureName().endsWith("_overlay")).distinct().toList()), this.empty);
        } else {
            ComboBoxUtil.updateComboBoxContents(this.comboBox, this.mcreator.getFolderManager().getTexturesList(TextureType.ARMOR).parallelStream().map((e) -> Texture.fromName(this.mcreator.getWorkspace(), TextureType.ARMOR, e.getName())).filter(Objects::nonNull).filter((e) -> e.getTextureName().endsWith("_overlay")).distinct().toList());
        }

    }

    public void setTextureFromTextureName(@Nullable String textureName) {
        if (textureName != null && !textureName.isBlank()) {
            textureName = FilenameUtilsPatched.removeExtension(textureName);
            this.comboBox.setSelectedItem(Texture.fromName(this.mcreator.getWorkspace(), this.textureType, textureName));
        }

    }

    public String getTextureName() {
        Texture selected = (Texture)this.comboBox.getSelectedItem();
        if (selected != null && !selected.equals(this.empty)) {
            String textureName = selected.getTextureName();
            return textureName + (this.addPNGExtension ? ".png" : "");
        } else {
            return this.defaultTextureName;
        }
    }

    public boolean hasTexture() {
        return this.getTexture() != null && !this.getTexture().equals(this.empty);
    }

    public Texture getTexture() {
        return (Texture)this.comboBox.getSelectedItem();
    }

    public SearchableComboBox<Texture> getComboBox() {
        return this.comboBox;
    }

    public void setAddPNGExtension(boolean addPNGExtension) {
        this.addPNGExtension = addPNGExtension;
    }

    private class Renderer extends JLabel implements ListCellRenderer<Texture> {
        public Renderer() {
            this.setOpaque(true);
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
            this.setHorizontalTextPosition(4);
            this.setHorizontalAlignment(2);
        }

        public Component getListCellRendererComponent(JList<? extends Texture> list, Texture value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            } else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }

            if (value != null) {
                this.setText(value.getTextureName());
                String textureName = value.getTextureName();
                String name = textureName;
                if (textureName.endsWith("_overlay")) {
                    name = textureName.substring(0, textureName.lastIndexOf("_overlay")) + "_layer_1_overlay";
                }

                ImageIcon imageIcon = new ImageIcon(OverlayTextureComboBox.this.mcreator.getFolderManager().getTextureFile(name, TextureType.ARMOR).getAbsolutePath());
                if (imageIcon != null) {
                    this.setIcon(new ImageIcon(ImageUtils.resize(imageIcon.getImage(), 30)));
                } else {
                    this.setIcon(new EmptyIcon(30, 30));
                }
            }

            return this;
        }
    }
}
