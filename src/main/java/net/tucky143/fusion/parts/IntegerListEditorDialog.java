package net.tucky143.fusion.parts;

import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.MCreatorDialog;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.Validator;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class IntegerListEditorDialog {

    /**
     * Opens a dialog to edit string entries contained in the provided list.
     *
     * @param parent        The workspace window this method was called from.
     * @param textList      List of string entries that are about to be edited.
     * @param validator     Function that returns a validator for each list entry, {@code null} means no validation.
     * @param uniqueEntries If {@code true}, duplicate list entries will not be allowed.
     * @return The resulting strings entries list after editing session is complete, or {@code null} if the operation
     * has been canceled (via cancel/close button) or if validation failed for some entries.
     */
    public static List<Integer> open(Window parent, Enumeration<Integer> textList,
                                    @Nullable Function<JSpinner, Validator> validator, boolean uniqueEntries) {
        AtomicReference<List<Integer>> retVal = new AtomicReference<>();
        MCreatorDialog dialog = new MCreatorDialog(parent, L10N.t("dialog.integer_list_editor.title"), true);

        List<ListEntry> entryList = new ArrayList<>();
        JPanel entries = new JPanel(new GridLayout(0, 1, 2, 3));

        while (textList.hasMoreElements())
            new ListEntry(entryList, entries, textList.nextElement(), validator, uniqueEntries);

        // If no entries present, add one to "guide" the user
        if (entryList.isEmpty())
            new ListEntry(entryList, entries, 0, validator, uniqueEntries);

        JButton add = new JButton(UIRES.get("16px.add"));
        add.setText(L10N.t("dialog.list_editor.add"));
        add.addActionListener(e -> new ListEntry(entryList, entries, 0, validator, uniqueEntries));

        JButton clear = new JButton(UIRES.get("16px.clear"));
        clear.setText(L10N.t("dialog.list_editor.clear"));
        clear.addActionListener(e -> {
            entryList.clear();
            entries.removeAll();
            entries.revalidate();
            entries.repaint();
        });

        JScrollPane scrollPane = new JScrollPane(PanelUtils.pullElementUp(entries));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add("North", PanelUtils.join(FlowLayout.LEFT, add, clear));
        listPanel.add("Center", scrollPane);

        JButton ok = new JButton(UIManager.getString("OptionPane.okButtonText"));
        JButton cancel = new JButton(UIManager.getString("OptionPane.cancelButtonText"));
        dialog.getRootPane().setDefaultButton(ok);

        ok.addActionListener(e -> {
            if ((validator != null || uniqueEntries) && !new AggregatedValidationResult(
                    entryList.stream().map(s -> s.valueField).toArray(IValidable[]::new)).validateIsErrorFree()) {
                JOptionPane.showMessageDialog(parent, L10N.t("dialog.list_editor.errors.message"),
                        L10N.t("dialog.list_editor.errors.title"), JOptionPane.ERROR_MESSAGE);
            } else {
                retVal.set(new ArrayList<>());
                for (ListEntry entry : entryList)
                    retVal.get().add((Integer) entry.valueField.getValue());
                dialog.dispose();
            }
        });
        cancel.addActionListener(e -> dialog.dispose());

        dialog.getContentPane().add(PanelUtils.centerAndSouthElement(listPanel, PanelUtils.join(ok, cancel)));
        dialog.setSize(470, 350);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return retVal.get();
    }

    private static final class ListEntry extends JPanel {
        private final JSpinner valueField = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

        private ListEntry(List<ListEntry> entryList, JPanel parent, Integer value,
                          @Nullable Function<JSpinner, Validator> validator, boolean uniqueEntries) {
            super(new BorderLayout(0, 0));
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            valueField.setPreferredSize(new Dimension(0, 28));

            Validator lev = null;

            valueField.setValue(value);

            final JComponent container = PanelUtils.expandHorizontally(this);
            parent.add(container);
            entryList.add(this);

            JButton remove = new JButton(UIRES.get("18px.remove"));
            remove.setOpaque(false);
            remove.setMargin(new Insets(0, 3, 0, 3));
            remove.addActionListener(e -> {
                entryList.remove(this);
                parent.remove(container);
                parent.revalidate();
                parent.repaint();
            });

            add("Center", valueField);
            add("East", remove);

            parent.revalidate();
            parent.repaint();
        }
    }
}
