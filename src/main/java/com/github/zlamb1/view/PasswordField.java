package com.github.zlamb1.view;

import com.github.zlamb1.io.ImageLoader;
import com.github.zlamb1.io.ImageStore;

import javax.swing.*;
import java.awt.*;

public class PasswordField extends Field {
    protected char defaultEchoChar = '*';
    protected JButton visibilityButton;

    protected boolean cachedVisibility = false;
    protected int cachedButtonSize = -1;

    private static final ImageStore visibilityOnStore = new ImageStore("visibility_on.png");
    private static final ImageStore visibilityOffStore = new ImageStore("visibility_off.png");

    public PasswordField(String label) {
        super(label, Orientation.VERTICAL, 3, new JPasswordField());
        setPasswordHidden(true);
    }

    public char getEchoChar() {
        return ((JPasswordField) jTextField).getEchoChar();
    }

    public void setEchoChar(char c) {
        ((JPasswordField) jTextField).setEchoChar(c);
        if (visibilityButton.isVisible())
            repaint();
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
    protected void paintComponent(final Graphics g) {
        int buttonSize = jTextField.getHeight();
        if ((cachedButtonSize != buttonSize || cachedVisibility != isPasswordHidden()) && buttonSize - 15 > 0) {
            cachedButtonSize = buttonSize;
            cachedVisibility = isPasswordHidden();

            ImageStore store = isPasswordHidden() ? visibilityOffStore : visibilityOnStore;
            Image buttonImage = store.getImage(buttonSize - 15, buttonSize - 15);
            if (buttonImage != null) {
                visibilityButton.setVisible(true);
                visibilityButton.setIcon(new ImageIcon(buttonImage));
                visibilityButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
            } else {
                visibilityButton.setVisible(false);
            }
        }

        super.paintComponent(g);
    }

    @Override
    protected void addTextField() {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        container.add(jTextField, gbc);

        visibilityButton = new JButton();
        visibilityButton.addActionListener((e) -> setPasswordHidden(!isPasswordHidden()));
        visibilityButton.setVisible(false);

        gbc.weightx = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        container.add(visibilityButton, gbc);

        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldLabelPanel.add(container);
    }
}
