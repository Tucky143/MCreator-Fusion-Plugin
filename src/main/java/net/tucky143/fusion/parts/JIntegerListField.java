package net.tucky143.fusion.parts;

import com.formdev.flatlaf.FlatClientProperties;
import net.mcreator.ui.component.TechnicalButton;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VTextField;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class JIntegerListField extends JPanel {

    private final DefaultListModel<Integer> entriesModel = new DefaultListModel<>();

    private final TechnicalButton edit = new TechnicalButton(UIRES.get("18px.edit"));
    private final TechnicalButton clear = new TechnicalButton(UIRES.get("18px.removeall"));

    private final List<ChangeListener> changeListeners = new ArrayList<>();

    private boolean uniqueEntries = false;

    private final JList<Integer> entriesList;
    private final JScrollPane pane;

    private final JPanel controls;

    /**
     * Sole constructor.
     *
     * @param parent    The parent window that the editor dialog would be shown over.
     * @param validator Function that returns a validator for each list entry when editing them in the dialog,
     *                  {@code null} means no validation.
     */
    public JIntegerListField(Window parent, @Nullable Function<VTextField, Validator> validator) {
        super(new BorderLayout());

        entriesList = new JList<>(entriesModel);
        entriesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        entriesList.setVisibleRowCount(1);
        entriesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        entriesList.setCellRenderer(new CustomListCellRenderer());

        pane = new JScrollPane(PanelUtils.totalCenterInPanel(entriesList));
        pane.setPreferredSize(getPreferredSize());
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setWheelScrollingEnabled(false);
        pane.addMouseWheelListener(new MouseAdapter() {
            @Override public void mouseWheelMoved(MouseWheelEvent evt) {
                int value = pane.getHorizontalScrollBar().getValue();
                if (evt.getWheelRotation() == 1) {
                    value += pane.getHorizontalScrollBar().getBlockIncrement() * evt.getScrollAmount();
                    if (value > pane.getHorizontalScrollBar().getMaximum())
                        value = pane.getHorizontalScrollBar().getMaximum();
                } else if (evt.getWheelRotation() == -1) {
                    value -= pane.getHorizontalScrollBar().getBlockIncrement() * evt.getScrollAmount();
                    if (value < 0)
                        value = 0;
                }
                pane.getHorizontalScrollBar().setValue(value);
            }
        });

        edit.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        edit.addActionListener(e -> {
            List<Integer> newTextList = IntegerListEditorDialog.open(parent, entriesModel.elements(), null, uniqueEntries);
            if (newTextList != null)
                setNumberList(newTextList);
        });

        clear.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        clear.addActionListener(e -> {
            entriesModel.clear();
            changeListeners.forEach(l -> l.stateChanged(new ChangeEvent(this)));
        });

        controls = PanelUtils.totalCenterInPanel(PanelUtils.join(edit, clear));
        controls.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Theme.current().getInterfaceAccentColor()));
        controls.setOpaque(true);
        controls.setBackground(Theme.current().getSecondAltBackgroundColor());

        add("Center", pane);
        add("East", controls);
    }

    public void hideButtons() {
        controls.setVisible(false);
    }

    public void disableItemCentering() {
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(entriesList);
        verticalBox.add(Box.createVerticalGlue());
        entriesList.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        pane.setViewportView(verticalBox);
    }

    @Override public void setEnabled(boolean b) {
        super.setEnabled(b);
        edit.setEnabled(b);
        clear.setEnabled(b);
    }

    /**
     * @param listener Listener object to be registered for listening to value changes of this component.
     */
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    /**
     * @param uniqueEntries Whether duplicate string entries in the list are forbidden.
     * @return This field instance.
     */
    public JIntegerListField setUniqueEntries(boolean uniqueEntries) {
        this.uniqueEntries = uniqueEntries;
        return this;
    }

    /**
     * @return List of string entries stored in this component.
     */
    public List<Integer> getNumberList() {
        return Collections.list(entriesModel.elements());
    }

    /**
     * @param newTextList List of string entries to be stored in this component.
     */
    public void setNumberList(Collection<Integer> newTextList) {
        entriesModel.clear();
        entriesModel.addAll(newTextList);
        changeListeners.forEach(l -> l.stateChanged(new ChangeEvent(this)));
    }

    private static class CustomListCellRenderer extends JLabel implements ListCellRenderer<Integer> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setOpaque(true);
            setBackground(isSelected ? Theme.current().getForegroundColor() : Theme.current().getAltBackgroundColor());
            setForeground(
                    isSelected ? Theme.current().getSecondAltBackgroundColor() : Theme.current().getForegroundColor());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 5, 0, 5, Theme.current().getBackgroundColor()),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setText(value.toString());
            return this;
        }
    }
}
