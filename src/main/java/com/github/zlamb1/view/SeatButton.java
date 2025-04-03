package com.github.zlamb1.view;

import com.github.zlamb1.ISeatDescriptor;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class SeatButton extends JPanel {
    protected ISeatDescriptor seatDescriptor;
    protected Color selectedBackground = new Color(40, 100, 225);
    protected Color reservedBackground = new Color(225, 40, 60);

    protected boolean selected;
    protected boolean ghosted;

    protected int buttonSize = 25;

    public SeatButton(ISeatDescriptor seatDescriptor) {
        this.seatDescriptor = seatDescriptor;
        setPreferredSize(new Dimension(buttonSize, buttonSize));
        setBackground(new Color(40, 225, 60));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public ISeatDescriptor getSeatDescriptor() {
        return seatDescriptor;
    }

    public Color getReservedBackground() {
        return reservedBackground;
    }

    public void setReservedBackground(Color reservedBackground) {
        this.reservedBackground = reservedBackground;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public boolean isGhosted() {
        return ghosted;
    }

    public void setGhosted(boolean ghosted) {
        this.ghosted = ghosted;
        repaint();
    }

    protected void onPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() != null && evt.getPropertyName().equals("reservedBy")) {
            repaint();
        }
    }

    protected Color getRealBackground() {
        if (selected)
            return selectedBackground;
        if (seatDescriptor.isReserved())
            return getReservedBackground();
        if (ghosted)
            return new Color(40, 120, 60);
        return getBackground();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (seatDescriptor != null)
            seatDescriptor.addPropertyChangeListener(this::onPropertyChange);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (seatDescriptor != null)
            seatDescriptor.removePropertyChangeListener(this::onPropertyChange);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getRealBackground());
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

        InputStream is = SeatButton.class.getClassLoader().getResourceAsStream("Roboto.ttf");

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            g.setFont(font.deriveFont(buttonSize * 0.40F).deriveFont(Collections.singletonMap(
                TextAttribute.WEIGHT, TextAttribute.WEIGHT_ULTRABOLD)));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.setColor(Color.WHITE);

        int x = (getWidth() - g.getFontMetrics().stringWidth(seatDescriptor.getName())) / 2;
        int y = ((getHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent();

        g.drawString(seatDescriptor.getName(), x, y);
    }
}
