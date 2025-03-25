package com.github.zlamb1.io;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Utility class to lazy load an image at variable size.
 */
public class ImageStore {
    protected String resourcePath;

    protected HashMap<Dimension, Image> images = new HashMap<>();
    protected BufferedImage image;

    public ImageStore(String resourcePath) {
        setResourcePath(resourcePath);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        image = null;
    }

    public Image getImage(int width, int height) {
        if (image == null) {
            image = ImageLoader.loadImage(resourcePath);
            if (image == null)
                return null;
            images.put(new Dimension(image.getWidth(null), image.getHeight(null)), image);
        }

        Dimension key = new Dimension(width, height);
        Image imageWithSize;
        if (images.containsKey(key)) {
            imageWithSize = images.get(key);
        } else {
            imageWithSize = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            images.put(key, imageWithSize);
        }

        return imageWithSize;
    }
}
