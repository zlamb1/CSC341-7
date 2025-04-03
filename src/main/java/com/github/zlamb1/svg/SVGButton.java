package com.github.zlamb1.svg;

import com.github.weisj.jsvg.SVGDocument;

import javax.swing.*;
import java.awt.*;

public class SVGButton extends JButton {
    protected SVGStore svgStore;
    protected SVGDocument svgDocument;

    protected SVGSize svgSize = new SVGSize(0.75f);

    public SVGButton(SVGStore svgStore) {
        super();
        this.svgStore = svgStore;
        svgDocument = svgStore.createDocument();
    }

    public SVGButton(String path, Color color) {
        super();
        svgStore = new SVGStore(path, color);
        svgDocument = svgStore.createDocument();
    }

    public void setSVGStore(SVGStore svgStore) {
        this.svgStore = svgStore;
        svgDocument = svgStore.createDocument();
        repaint();
    }

    public void setSVGColor(Color color) {
        svgStore.setSVGColor(color);
    }

    public void setSVGSize(SVGSize svgSize) {
        this.svgSize = svgSize;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        int width = getWidth(), height = getHeight();

        if (svgSize != null) {
            width = svgSize.computeWidth(getWidth());
            height = svgSize.computeHeight(getHeight());
        }

        int x = (getWidth() / 2) - (width / 2);
        int y = (getHeight() / 2) - (height / 2);

        svgStore.paintSVG(this, (Graphics2D) g, x, y, width, height);
    }
}
