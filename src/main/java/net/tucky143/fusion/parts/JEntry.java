package net.tucky143.fusion.parts;

import net.tucky143.fusion.utils.EntityUtils;
import net.mcreator.element.parts.EntityEntry;
import net.mcreator.minecraft.DataListEntry;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.workspace.Workspace;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * Single entity entry in {@link JEntries}
 */
public class JEntry extends JPanel {
    private final JComboBox<String> entityType = new SearchableComboBox();
    private final Workspace workspace;

    @SuppressWarnings("unused")
    public JEntry(MCreator mcreator, IHelpContext gui, JPanel parent, List<JEntry> entryList) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.workspace = mcreator.getWorkspace();
        JComponent container = PanelUtils.expandHorizontally(this);
        parent.add(container);
        entryList.add(this);

        List<DataListEntry> entities = EntityUtils.loadEntities(this.workspace);
        entities.forEach((e) -> {
            this.entityType.addItem(e.getName());
        });
        this.add(L10N.label("dialog.spawn_list_entry.entity"));
        this.add(this.entityType);

        JButton remove = new JButton(UIRES.get("16px.clear"));
        remove.setText(L10N.t("dialog.entity_list_entry.remove_entity"));
        remove.addActionListener((e) -> {
            parent.remove(container);
            entryList.remove(this);
            parent.revalidate();
            parent.repaint();
        });
        this.add(remove);
        parent.revalidate();
        parent.repaint();

    }

    public EntityEntry getEntity() {
        return new EntityEntry(this.workspace, Objects.requireNonNull(this.entityType.getSelectedItem()).toString());
    }

    public void setEntity(EntityEntry e) {
        this.entityType.setSelectedItem(e.getUnmappedValue());
    }
}
