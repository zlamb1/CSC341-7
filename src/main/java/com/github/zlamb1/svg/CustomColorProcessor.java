package com.github.zlamb1.svg;

import com.github.weisj.jsvg.parser.AttributeNode;
import com.github.weisj.jsvg.parser.DomProcessor;
import com.github.weisj.jsvg.parser.ParsedElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class CustomColorProcessor implements DomProcessor {
    protected java.util.List<DynamicAWTSvgPaint> dynamicColors;
    protected Color defaultColor;

    public CustomColorProcessor() {
        this(null);
    }

    public CustomColorProcessor(Color defaultColor) {
        this.dynamicColors = new ArrayList<>();
        this.defaultColor = defaultColor;
    }

    public void setColor(Color color) {
        for (DynamicAWTSvgPaint dynamicColor : dynamicColors) {
            dynamicColor.setColor(color);
        }
    }

    @Override
    public void process(ParsedElement root) {
        processImpl(root);
        root.children().forEach(this::process);
    }

    private void processImpl(ParsedElement element) {
        AttributeNode attributeNode = element.attributeNode();

        DynamicAWTSvgPaint dynamicColor = new DynamicAWTSvgPaint(
            defaultColor == null ? attributeNode.getColor("fill", Color.BLACK) : defaultColor
        );

        dynamicColors.add(dynamicColor);

        String uniqueIdForDynamicColor = UUID.randomUUID().toString();
        element.registerNamedElement(uniqueIdForDynamicColor, dynamicColor);
        attributeNode.attributes().put("fill", uniqueIdForDynamicColor);
    }
}
