package com.github.zlamb1.svg;

import com.github.weisj.jsvg.attributes.paint.SimplePaintSVGPaint;

import java.awt.*;

public class DynamicAWTSvgPaint implements SimplePaintSVGPaint {
    private Color color;

    DynamicAWTSvgPaint(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color color() {
        return color;
    }

    @Override
    public Paint paint() {
        return color;
    }
}