package com.github.zlamb1.view;

import java.awt.geom.Path2D;

public class RoundRect extends Path2D.Float {
    public RoundRect(int width, int height, float radius) {
        this(width, height, radius, radius);
    }

    public RoundRect(int width, int height, float topRadius, float bottomRadius) {
        this(width, height, topRadius, topRadius, bottomRadius, bottomRadius);
    }

    public RoundRect(int width, int height, float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        moveTo(topLeftRadius, 0);
        lineTo(width - topRightRadius, 0);
        curveTo(width, 0, width, 0, width, topRightRadius);
        lineTo(width, height - bottomRightRadius);
        curveTo(width, height, width, height, width - bottomRightRadius, height);
        lineTo(bottomLeftRadius, height);
        curveTo(0, height, 0, height, 0, height - bottomLeftRadius);
        lineTo(0, topLeftRadius);
        curveTo(0, 0, 0, 0, topLeftRadius, 0);
        closePath();
    }
}
