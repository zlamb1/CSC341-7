package com.github.zlamb1.view;

import java.awt.*;

public class InsetHelper {
    public static Insets createEmptyInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public static Insets createInsets(int size) {
        return new Insets(size, size, size, size);
    }

    public static Insets createInsets(int horizontal, int vertical) {
        return new Insets(vertical, horizontal, vertical, horizontal);
    }

    public static Insets createHorizontalInsets(int size) {
        return new Insets(0, size, 0, size);
    }

    public static Insets createVerticalInsets(int size) {
        return new Insets(size, 0, size, 0);
    }

    public static Insets createNorthInsets(int size) {
        return new Insets(size, 0, 0, 0);
    }

    public static Insets createSouthInsets(int size) {
        return new Insets(0, 0, size, 0);
    }

    public static Insets createWestInsets(int size) {
        return new Insets(0, size, 0, 0);
    }

    public static Insets createEastInsets(int size) {
        return new Insets(0, 0, 0, size);
    }
}
