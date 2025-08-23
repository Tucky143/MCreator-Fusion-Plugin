package net.tucky143.fusion.parts;

import net.mcreator.generator.mapping.MappableElement;
import net.mcreator.minecraft.DataListEntry;
import net.mcreator.minecraft.MCItem;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.TechnicalButton;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.BlockItemIcons;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.Validator;
import net.mcreator.util.FilenameUtilsPatched;
import net.mcreator.util.StringUtils;
import net.mcreator.util.image.ImageUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public abstract class JItemListFieldMulti<T> extends JPanel implements IValidable {
    private final TechnicalButton add;
    private final TechnicalButton remove;
    private final TechnicalButton removeall;
    private final TechnicalButton addtag;
    private final JToggleButton include;
    private final JToggleButton exclude;
    private Validator validator;
    private Validator.ValidationResult currentValidationResult;
    private final DefaultListModel<T> elementsListModel;
    protected final JList<T> elementsList;
    protected final MCreator mcreator;
    private final List<ChangeListener> listeners;

    protected JItemListFieldMulti(MCreator mcreator) {
        this(mcreator, false);
    }

    protected JItemListFieldMulti(MCreator mcreator, boolean excludeButton) {
        this(mcreator, excludeButton, false);
    }

    protected JItemListFieldMulti(MCreator mcreator, boolean excludeButton, boolean allowTags) {
        this.add = new TechnicalButton(UIRES.get("18px.add"));
        this.remove = new TechnicalButton(UIRES.get("18px.remove"));
        this.removeall = new TechnicalButton(UIRES.get("18px.removeall"));
        this.addtag = new TechnicalButton(UIRES.get("18px.addtag"));
        this.include = L10N.togglebutton("elementgui.common.include", new Object[0]);
        this.exclude = L10N.togglebutton("elementgui.common.exclude", new Object[0]);
        this.validator = null;
        this.currentValidationResult = null;
        this.elementsListModel = new DefaultListModel();
        this.elementsList = new JList(this.elementsListModel);
        this.listeners = new ArrayList();
        this.mcreator = mcreator;
        this.setLayout(new BorderLayout());
        this.elementsList.setSelectionMode(1);
        this.elementsList.setVisibleRowCount(1);
        this.elementsList.setLayoutOrientation(2);
        this.elementsList.setCellRenderer(new CustomListCellRenderer());
        this.add.setOpaque(false);
        this.add.setMargin(new Insets(0, 0, 0, 0));
        this.add.setBorder(BorderFactory.createEmptyBorder());
        this.add.setContentAreaFilled(false);
        this.remove.setOpaque(false);
        this.remove.setMargin(new Insets(0, 0, 0, 0));
        this.remove.setBorder(BorderFactory.createEmptyBorder());
        this.remove.setContentAreaFilled(false);
        this.removeall.setOpaque(false);
        this.removeall.setMargin(new Insets(0, 0, 0, 0));
        this.removeall.setBorder(BorderFactory.createEmptyBorder());
        this.removeall.setContentAreaFilled(false);
        this.addtag.setOpaque(false);
        this.addtag.setMargin(new Insets(0, 0, 0, 0));
        this.addtag.setBorder(BorderFactory.createEmptyBorder());
        this.addtag.setContentAreaFilled(false);
        this.add.addActionListener((e) -> {
            List<T> list = this.getElementsToAdd();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                T el = (T) var3.next();
                this.elementsListModel.addElement(el);
            }

            if (!list.isEmpty()) {
                this.listeners.forEach((l) -> {
                    l.stateChanged(new ChangeEvent(e.getSource()));
                });
            }

        });
        this.remove.addActionListener((e) -> {
            List<T> elements = this.elementsList.getSelectedValuesList();
            Iterator var3 = elements.iterator();

            while(var3.hasNext()) {
                T element = (T) var3.next();
                if (element != null) {
                    this.elementsListModel.removeElement(element);
                    this.listeners.forEach((l) -> {
                        l.stateChanged(new ChangeEvent(e.getSource()));
                    });
                }
            }

        });
        this.removeall.addActionListener((e) -> {
            this.elementsListModel.removeAllElements();
            this.listeners.forEach((l) -> {
                l.stateChanged(new ChangeEvent(e.getSource()));
            });
        });
        this.addtag.addActionListener((e) -> {
            List<T> list = this.getTagsToAdd();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                T el = (T) var3.next();
                this.elementsListModel.addElement(el);
            }

            this.listeners.forEach((l) -> {
                l.stateChanged(new ChangeEvent(e.getSource()));
            });
        });
        this.include.addActionListener((e) -> {
            this.listeners.forEach((l) -> {
                l.stateChanged(new ChangeEvent(e.getSource()));
            });
        });
        this.exclude.addActionListener((e) -> {
            this.listeners.forEach((l) -> {
                l.stateChanged(new ChangeEvent(e.getSource()));
            });
        });
        final JScrollPane pane = new JScrollPane(PanelUtils.totalCenterInPanel(this.elementsList));
        pane.setHorizontalScrollBarPolicy(31);
        pane.setVerticalScrollBarPolicy(21);
        pane.setWheelScrollingEnabled(false);
        pane.addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent evt) {
                int amount = evt.getScrollAmount();
                int value;
                if (evt.getWheelRotation() == 1) {
                    value = pane.getHorizontalScrollBar().getValue() + pane.getHorizontalScrollBar().getBlockIncrement() * amount;
                    if (value > pane.getHorizontalScrollBar().getMaximum()) {
                        value = pane.getHorizontalScrollBar().getMaximum();
                    }

                    pane.getHorizontalScrollBar().setValue(value);
                } else if (evt.getWheelRotation() == -1) {
                    value = pane.getHorizontalScrollBar().getValue() - pane.getHorizontalScrollBar().getBlockIncrement() * amount;
                    if (value < 0) {
                        value = 0;
                    }

                    pane.getHorizontalScrollBar().setValue(value);
                }

            }
        });
        pane.setPreferredSize(this.getPreferredSize());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(this.add);
        if (allowTags) {
            buttonsPanel.add(this.addtag);
        }

        buttonsPanel.add(this.remove);
        buttonsPanel.add(this.removeall);
        JComponent buttons = PanelUtils.totalCenterInPanel(buttonsPanel);
        buttons.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Theme.current().getInterfaceAccentColor()));
        buttons.setOpaque(true);
        buttons.setBackground(Theme.current().getSecondAltBackgroundColor());
        if (excludeButton) {
            this.include.setSelected(true);
            ButtonGroup group = new ButtonGroup();
            group.add(this.include);
            group.add(this.exclude);
            this.include.setMargin(new Insets(0, 1, 0, 1));
            this.exclude.setMargin(new Insets(0, 1, 0, 1));
            JComponent incexc = PanelUtils.totalCenterInPanel(PanelUtils.join(new Component[]{this.include, this.exclude}));
            incexc.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.current().getInterfaceAccentColor()));
            this.add(incexc, "West");
        }

        this.add(pane, "Center");
        this.add(buttons, "East");
    }

    protected abstract List<T> getElementsToAdd();

    protected List<T> getTagsToAdd() {
        return List.of();
    }

    public void setEnabled(boolean enabled) {
        this.add.setEnabled(enabled);
        this.remove.setEnabled(enabled);
        this.removeall.setEnabled(enabled);
        this.addtag.setEnabled(enabled);
        this.include.setEnabled(enabled);
        this.exclude.setEnabled(enabled);
    }

    public void addChangeListener(ChangeListener changeListener) {
        this.listeners.add(changeListener);
    }

    public List<T> getListElements() {
        List<T> retval = new ArrayList();

        for (int i = 0; i < this.elementsListModel.size(); ++i) {
            T element = this.elementsListModel.get(i);
            if (element instanceof MappableElement mappableElement && !mappableElement.isValidReference())
                continue;
            retval.add(element);
        }

        return retval;
    }

    public void setListElements(@Nullable List<T> elements) {
        if (elements != null) {
            this.elementsListModel.removeAllElements();
            for (T element : elements) {
                if (element instanceof MappableElement mappableElement && !mappableElement.isValidReference())
                    continue;
                elementsListModel.addElement(element);
            }

        }
    }

    public boolean isExclusionMode() {
        return this.exclude.isSelected();
    }

    public void setExclusionMode(boolean isExcluded) {
        this.exclude.setSelected(isExcluded);
        this.include.setSelected(!isExcluded);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (this.currentValidationResult != null) {
            if (this.currentValidationResult.getValidationResultType() == Validator.ValidationResultType.WARNING) {
                g.drawImage(UIRES.get("18px.warning").getImage(), 0, 0, 13, 13, (ImageObserver)null);
                g.setColor(new Color(238, 229, 113));
            } else if (this.currentValidationResult.getValidationResultType() == Validator.ValidationResultType.ERROR) {
                g.drawImage(UIRES.get("18px.remove").getImage(), 0, 0, 13, 13, (ImageObserver)null);
                g.setColor(new Color(204, 108, 108));
            } else if (this.currentValidationResult.getValidationResultType() == Validator.ValidationResultType.PASSED) {
                g.drawImage(UIRES.get("18px.ok").getImage(), 0, 0, 13, 13, (ImageObserver)null);
                g.setColor(new Color(79, 192, 121));
            }

            if (this.currentValidationResult.getValidationResultType() == Validator.ValidationResultType.ERROR || this.currentValidationResult.getValidationResultType() == Validator.ValidationResultType.WARNING) {
                g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
            }
        }

    }

    public Validator.ValidationResult getValidationStatus() {
        Validator.ValidationResult validationResult = this.validator == null ? null : this.validator.validateIfEnabled(this);
        this.currentValidationResult = validationResult;
        this.repaint();
        return validationResult;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public Validator getValidator() {
        return this.validator;
    }

    class CustomListCellRenderer extends JLabel implements ListCellRenderer<T> {
        CustomListCellRenderer() {
        }

        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
            this.setOpaque(true);
            this.setBackground(isSelected ? Theme.current().getForegroundColor() : Theme.current().getAltBackgroundColor());
            this.setForeground(isSelected ? Theme.current().getSecondAltBackgroundColor() : Theme.current().getForegroundColor());
            this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, Theme.current().getBackgroundColor()), BorderFactory.createEmptyBorder(2, 5, 2, 5)));
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
            this.setIcon((Icon)null);
            if (value instanceof MappableElement mappableElement) {
                Optional<DataListEntry> dataListEntryOpt = mappableElement.getDataListEntry();
                if (dataListEntryOpt.isPresent()) {
                    DataListEntry dataListEntry = (DataListEntry)dataListEntryOpt.get();
                    this.setText(dataListEntry.getReadableName());
                    if (dataListEntry.getTexture() != null) {
                        this.setIcon(new ImageIcon(ImageUtils.resizeAA(BlockItemIcons.getIconForItem(dataListEntry.getTexture()).getImage(), 18)));
                    }
                } else {
                    String unmappedValue = mappableElement.getUnmappedValue();
                    this.setText(unmappedValue.replace("CUSTOM:", "").replace("Blocks.", "").replace("Items.", "").replace("#", ""));
                    if (unmappedValue.startsWith("CUSTOM:")) {
                        this.setIcon(new ImageIcon(ImageUtils.resizeAA(MCItem.getBlockIconBasedOnName(mcreator.getWorkspace(), unmappedValue).getImage(), 18)));
                    } else if (unmappedValue.startsWith("#")) {
                        this.setIcon(new ImageIcon(ImageUtils.resizeAA(MCItem.TAG_ICON.getImage(), 18)));
                    }
                }
                
            } else if (value instanceof File) {
                this.setText(FilenameUtilsPatched.removeExtension(((File)value).getName()));
            } else {
                this.setText(StringUtils.machineToReadableName(value.toString().replace("CUSTOM:", "")));
                if (value.toString().contains("CUSTOM:")) {
                    this.setIcon(new ImageIcon(ImageUtils.resizeAA(MCItem.getBlockIconBasedOnName(mcreator.getWorkspace(), value.toString()).getImage(), 18)));
                }
            }

            return this;
        }
    }
}

