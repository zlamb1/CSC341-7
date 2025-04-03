package com.github.zlamb1.svg;

import com.github.weisj.jsvg.SVGDocument;

import javax.swing.*;
import java.awt.*;

public class SVGPanel extends JPanel {
    public static class SVGLoadException extends RuntimeException {
        public SVGLoadException() {
            this("Failed To Load SVG");
        }

        public SVGLoadException(String message) {
            super(message);
        }
    }

    protected SVGStore svgStore;
    protected SVGDocument svgDocument;

    public SVGPanel(String path) {
        this(path, null);
    }

    public SVGPanel(String path, Color color) {
        svgStore = new SVGStore(path, color);
        svgDocument = svgStore.createDocument();
    }

    public SVGPanel(SVGDocument svgDocument) {
        if (svgDocument == null)
            throw new IllegalArgumentException("SVG Document Cannot Be Null");
        this.svgDocument = svgDocument;
    }

    public void setSVGColor(Color color) {
        svgStore.setSVGColor(color);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        svgStore.paintSVG(this, (Graphics2D) g, getWidth(), getHeight());
    }
}
