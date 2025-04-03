package com.github.zlamb1.view;

import com.github.zlamb1.svg.SVGButton;
import com.github.zlamb1.svg.SVGStore;

import javax.swing.*;
import java.awt.*;

public class PasswordField extends Field {
    protected char defaultEchoChar = '*';
    protected SVGButton visibilityButton;

    protected static SVGStore visibilityOn = new SVGStore("visibility_on.svg"), visibilityOff = new SVGStore("visibility_off.svg");

    public PasswordField(String label) {
        super(label, Orientation.VERTICAL, 3, new JPasswordField());
        setPasswordHidden(true);
    }

    public char getEchoChar() {
        return ((JPasswordField) jTextField).getEchoChar();
    }

    public void setEchoChar(char c) {
        ((JPasswordField) jTextField).setEchoChar(c);
        visibilityButton.setSVGStore(c == 0 ? visibilityOn : visibilityOff);
    }

    public char getDefaultEchoChar() {
        return defaultEchoChar;
    }

    public void setDefaultEchoChar(char c) {
        defaultEchoChar = c;
    }

    public void resetEchoChar() {
        setEchoChar(defaultEchoChar);
    }

    public boolean isPasswordHidden() {
        return getEchoChar() != (char) 0;
    }

    public void setPasswordHidden(boolean b) {
        setEchoChar(b ? defaultEchoChar : (char) 0);
    }

    public String getPassword() {
        char[] password = ((JPasswordField) jTextField).getPassword();
        return new String(password);
    }

    @Override
    protected void addTextField() {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        container.add(jTextField, gbc);

        visibilityButton = new SVGButton(visibilityOn);
        visibilityButton.setSVGColor(visibilityButton.getForeground());

        visibilityButton.addActionListener((e) -> setPasswordHidden(!isPasswordHidden()));

        gbc.weightx = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        container.add(visibilityButton, gbc);

        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldLabelPanel.add(container);
    }
}
