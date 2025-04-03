package com.github.zlamb1.svg;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.attributes.ViewBox;
import com.github.weisj.jsvg.parser.DefaultParserProvider;
import com.github.weisj.jsvg.parser.DomProcessor;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SVGStore {
    protected String path;
    protected Color defaultColor;

    protected SVGDocument svgDocument;
    protected CustomColorProcessor customColorProcessor;

    public SVGStore(String path) {
        this(path, null);
    }

    public SVGStore(String path, Color defaultColor) {
        this.path = path;
        this.defaultColor = defaultColor;
    }

    public void setSVGColor(Color color) {
        customColorProcessor.setColor(color);
    }

    public SVGDocument createDocument() {
        if (path == null)
            throw new SVGPanel.SVGLoadException("SVG Path Cannot Be Null");
        com.github.weisj.jsvg.parser.SVGLoader loader = new com.github.weisj.jsvg.parser.SVGLoader();
        URL svgUrl = SVGPanel.class.getClassLoader().getResource(path);
        if (svgUrl == null)
            throw new SVGPanel.SVGLoadException("SVG File Not Found");

        customColorProcessor = new CustomColorProcessor(defaultColor);
        svgDocument = loader.load(svgUrl, new DefaultParserProvider() {
            @Override
            public DomProcessor createPreProcessor() {
            return customColorProcessor;
            }
        });

        return svgDocument;
    }

    public void paintSVG(JComponent comp, Graphics2D g2d, int width, int height) {
        paintSVG(comp, g2d, 0, 0, width, height);
    }

    public void paintSVG(JComponent comp, Graphics2D g2d, int x, int y, int width, int height) {
        svgDocument.render(comp, g2d, new ViewBox(x, y, width, height));
    }

}
