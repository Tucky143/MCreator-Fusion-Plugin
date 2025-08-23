package net.tucky143.fusion.parts;

import net.mcreator.element.parts.EntityEntry;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JEntriesList;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.tucky143.fusion.elements.Attribute;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI for adding entities to {@link Attribute}
 */
public class JEntries extends JEntriesList {
    /**
     * List of entities that user added
     */
    private final List<JEntry> entryList = new ArrayList<>();
    private final JPanel entries = new JPanel();

    @SuppressWarnings("unused")
    public JEntries(MCreator mcreator, IHelpContext gui) {
        super(mcreator, new BorderLayout(), gui);
        this.setOpaque(false);
        JPanel topbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topbar.setBackground((Color) UIManager.get("MCreatorLAF.LIGHT_ACCENT"));
        this.add.setText(L10N.t("elementgui.attribute.add_entry"));
        topbar.add(this.add);
        this.add("North", topbar);
        this.entries.setLayout(new BoxLayout(this.entries, BoxLayout.PAGE_AXIS));
        this.entries.setOpaque(false);
        this.add.addActionListener((e) -> {
            JEntry entry = new JEntry(mcreator, gui, this.entries, this.entryList);
            this.registerEntryUI(entry);
        });
        this.add("Center", new JScrollPane(PanelUtils.pullElementUp(this.entries)));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((Color) UIManager.get("MCreatorLAF.BRIGHT_COLOR"), 1), L10N.t("elementgui.attribute.entity_list_add"), 0, 0, this.getFont().deriveFont(12.0F), (Color) UIManager.get("MCreatorLAF.BRIGHT_COLOR")));
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, (int) ((double) mcreator.getSize().height * 0.6)));
    }

    public List<EntityEntry> getEntities() {
        return this.entryList.stream().map(JEntry::getEntity).toList();
    }

    public void setEntities(List<EntityEntry> entities) {
        entities.forEach((e) -> {
            JEntry entry = new JEntry(this.mcreator, this.gui, this.entries, this.entryList);
            this.registerEntryUI(entry);
            entry.setEntity(e);
        });
    }
}
