package com.github.zlamb1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SeatFilter extends JPanel {
    protected java.util.List<FilterListener> filterListeners = new ArrayList<>();

    protected JCheckBox windowCheckBox, aisleCheckBox;

    public interface FilterListener {
        void onFilter(Filter filter);
    }

    public static class Filter {
        protected boolean window, aisle;

        public Filter(boolean window, boolean aisle) {
            this.window = window;
            this.aisle = aisle;
        }

        public boolean filterByWindowSeats() {
            return window;
        }

        public boolean filterByAisleSeats() {
            return aisle;
        }
    }

    public SeatFilter() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = InsetHelper.createEastInsets(5);

        JLabel windowLabel = new JLabel("Window Seat");
        windowLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                windowCheckBox.setSelected(!windowCheckBox.isSelected());
            }
        });

        add(windowLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = InsetHelper.createEmptyInsets();

        windowCheckBox = new JCheckBox();
        windowCheckBox.addItemListener(e -> dispatchFilterEvent());

        add(windowCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = InsetHelper.createEastInsets(5);

        JLabel aisleLabel = new JLabel("Aisle Seat");
        aisleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                aisleCheckBox.setSelected(!aisleCheckBox.isSelected());
            }
        });

        add(aisleLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = InsetHelper.createEmptyInsets();

        aisleCheckBox = new JCheckBox();
        aisleCheckBox.addItemListener(e -> dispatchFilterEvent());

        add(aisleCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weighty = 1.0;

        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        add(emptyPanel, gbc);
    }

    protected void dispatchFilterEvent() {
        Filter filter = new Filter(windowCheckBox.isSelected(), aisleCheckBox.isSelected());
        for (FilterListener filterListener : filterListeners) {
            filterListener.onFilter(filter);
        }
    }

    public void addFilterListener(FilterListener listener) {
        filterListeners.add(listener);
    }

    public void removeFilterListener(FilterListener listener) {
        filterListeners.remove(listener);
    }
}
