package com.github.zlamb1.view;

import javax.swing.*;
import java.awt.*;

public class SeatButton extends JPanel {
    protected Color disabledBackground = new Color(225, 40, 60);

    public SeatButton() {
        setPreferredSize(new Dimension(25, 25));
        setBackground(new Color(40, 225, 60));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public Color getDisabledBackground() {
        return disabledBackground;
    }

    public void setDisabledBackground(Color disabledBackground) {
        this.disabledBackground = disabledBackground;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        g.setColor(isEnabled() ? getBackground() : getDisabledBackground());
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
