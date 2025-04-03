package com.github.zlamb1.svg;

import com.github.weisj.jsvg.SVGDocument;

import javax.swing.*;
import java.awt.*;

public class SVGButton extends JButton {
    protected SVGStore svgStore;
    protected SVGDocument svgDocument;

    protected Dimension svgSize;

    public SVGButton(String path, Color color) {
        super();
        svgStore = new SVGStore(path, color);
        svgDocument = svgStore.createDocument();
    }

    public void setSVGColor(Color color) {
        svgStore.setSVGColor(color);
    }

    public void setSVGSize(Dimension svgSize) {
        this.svgSize = svgSize;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        svgStore.paintSVG(this, (Graphics2D) g, getWidth(), getHeight());
    }
}
