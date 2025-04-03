package com.github.zlamb1.svg;

import java.awt.*;

public class SVGSize {
    public enum Type {
        PIXEL,
        PERCENTAGE
    }
    protected Type sizeType;
    protected Dimension size;
    protected float percentageX, percentageY;

    public SVGSize(int size) {
        this(new Dimension(size, size));
    }

    public SVGSize(int width, int height) {
        this(new Dimension(width, height));
    }

    public SVGSize(Dimension size) {
        this.sizeType = Type.PIXEL;
        this.size = size;
    }

    public SVGSize(float percentage) {
        this(percentage, percentage);
    }

    public SVGSize(float percentageX, float percentageY) {
        this.sizeType = Type.PERCENTAGE;
        this.percentageX = percentageX;
        this.percentageY = percentageY;
    }

    public Type getSizeType() {
        return sizeType;
    }

    public Dimension getSize() {
        return size;
    }

    public float getPercentageX() {
        return percentageX;
    }

    public float getPercentageY() {
        return percentageY;
    }

    public int computeWidth(int width) {
        return switch (sizeType) {
            case PIXEL -> size.width;
            case PERCENTAGE -> (int) (width * percentageX);
        };
    }

    public int computeHeight(int height) {
        return switch (sizeType) {
            case PIXEL -> size.height;
            case PERCENTAGE -> (int) (height * percentageY);
        };
    }
}
