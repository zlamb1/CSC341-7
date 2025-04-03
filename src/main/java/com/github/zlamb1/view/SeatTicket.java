package com.github.zlamb1.view;

import com.github.zlamb1.ISeatDescriptor;
import com.github.zlamb1.io.ImageStore;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class SeatTicket extends JPanel {
    protected static ImageStore checkImageStore = new ImageStore("check.png");

    protected ISeatDescriptor seatDescriptor;

    protected int topBarHeight = 25;
    protected int bottomBarHeight = 15;
    protected int checkBoxSize = 15;

    protected JLabel nameLabel;
    protected JLabel windowCheckBox;
    protected JLabel aisleCheckBox;

    public SeatTicket() {
        setSeatDescriptor(null);
        setBorder(BorderFactory.createEmptyBorder(topBarHeight, 0, bottomBarHeight, 0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setOpaque(false);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameLabel = new JLabel();
        nameLabel.setForeground(Color.BLACK);
        namePanel.add(nameLabel);

        add(namePanel);

        JPanel windowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        windowPanel.setOpaque(false);
        windowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel windowLabel = new JLabel("Window Seat: ");
        windowLabel.setForeground(Color.BLACK);
        windowPanel.add(windowLabel);

        windowCheckBox = new JLabel();
        windowCheckBox.setPreferredSize(new Dimension(checkBoxSize, checkBoxSize));
        windowCheckBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        windowPanel.add(windowCheckBox);

        add(windowPanel);

        JPanel aislePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        aislePanel.setOpaque(false);
        aislePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel aisleLabel = new JLabel("Aisle Seat: ");
        aisleLabel.setForeground(Color.BLACK);
        aislePanel.add(aisleLabel);

        aisleCheckBox = new JLabel();
        aisleCheckBox.setPreferredSize(new Dimension(checkBoxSize, checkBoxSize));
        aisleCheckBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        aislePanel.add(aisleCheckBox);

        add(aislePanel);
    }

    public ISeatDescriptor getSeatDescriptor() {
        return seatDescriptor;
    }

    private static ImageIcon cachedCheckImage;
    private static int cachedCheckImageSize;

    public void setSeatDescriptor(ISeatDescriptor seatDescriptor) {
        this.seatDescriptor = seatDescriptor;
        setVisible(seatDescriptor != null);

        if (seatDescriptor != null) {
            int checkIconSize = checkBoxSize - 2;
            if (cachedCheckImage == null || checkIconSize != cachedCheckImageSize) {
                cachedCheckImageSize = checkIconSize;
                Image checkImage = checkImageStore.getImage(checkIconSize, checkIconSize);
                cachedCheckImage = new ImageIcon(checkImage);
            }

            nameLabel.setText("Seat: " + seatDescriptor.getName());
            windowCheckBox.setIcon(seatDescriptor.isWindowSeat() ? cachedCheckImage : null);
            aisleCheckBox.setIcon(seatDescriptor.isAisleSeat() ? cachedCheckImage : null);
        }
    }

    protected void onPropertyChange(PropertyChangeEvent evt) {
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, topBarHeight, getWidth(), getHeight() - topBarHeight - bottomBarHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, topBarHeight, 0, getHeight() - bottomBarHeight);
        g2d.drawLine(getWidth() - 1, topBarHeight, getWidth() - 1, getHeight() - bottomBarHeight);

        g2d.setColor(new Color(225, 40, 60));
        g2d.fill(new RoundRect(getWidth(), topBarHeight, (float) topBarHeight, 0.0f));

        g2d.setColor(Color.WHITE);
        String boardingPassString = "BOARDING PASS";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
        g2d.drawString(boardingPassString, 5, topBarHeight / 2 + g.getFontMetrics().getAscent() - g.getFontMetrics().getHeight() / 2);

        g2d.setColor(new Color(225, 40, 60));
        g2d.translate(0, getHeight() - bottomBarHeight);
        g2d.fill(new RoundRect(getWidth(), bottomBarHeight, 0.0f, (float) bottomBarHeight));
        g2d.translate(0, -(getHeight() - bottomBarHeight));
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
}
