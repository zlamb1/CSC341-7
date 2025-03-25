package com.github.zlamb1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Field extends JPanel {
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    protected JPanel fieldLabelPanel;
    protected JTextField jTextField;
    protected JLabel hintLabel;

    public Field(String label) {
        this(label, Orientation.VERTICAL, 3);
    }

    public Field(String label, Orientation orientation, int gap) {
        this(label, orientation, gap, new JTextField());
    }

    public Field(String label, Orientation orientation, int gap, JTextField textField) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fieldLabelPanel = new JPanel();

        JLabel jLabel = new JLabel(label);

        if (orientation == Orientation.VERTICAL) {
            jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        jTextField = textField;
        jTextField.setPreferredSize(new Dimension(100, 35));

        if (orientation == Orientation.VERTICAL) {
            jTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        BoxLayout layout = new BoxLayout(fieldLabelPanel, orientation == Orientation.VERTICAL ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
        fieldLabelPanel.setLayout(layout);

        fieldLabelPanel.add(jLabel);

        if (orientation == Orientation.HORIZONTAL) {
            add(Box.createHorizontalGlue());
        }

        if (gap > 0) {
            Dimension dim = new Dimension(
                orientation == Orientation.HORIZONTAL ? gap : 0,
                orientation == Orientation.VERTICAL ? gap : 0
            );
            add(Box.createRigidArea(dim));
        }

        addTextField();

        add(fieldLabelPanel);

        hintLabel = new JLabel();
        hintLabel.setForeground(new Color(225, 0, 0));
        hintLabel.setFont(hintLabel.getFont().deriveFont(Font.PLAIN, 12));
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(hintLabel);
    }

    public String getHint() {
        return hintLabel.getText();
    }

    public void setHint(String hint) {
        this.hintLabel.setText(hint);
    }

    public Color getHintForeground() {
        return hintLabel.getForeground();
    }

    public void setHintForeground(Color c) {
        hintLabel.setForeground(c);
    }

    public String getValue() {
        return jTextField.getText();
    }

    public void addActionListener(ActionListener actionListener) {
        jTextField.addActionListener(actionListener);
    }

    public void removeActionListeners() {
        for (ActionListener actionListener : jTextField.getActionListeners()) {
            jTextField.removeActionListener(actionListener);
        }
    }

    public void clear() {
        jTextField.setText(null);
        setHint(null);
    }

    protected void addTextField() {
        fieldLabelPanel.add(jTextField);
    }
}
