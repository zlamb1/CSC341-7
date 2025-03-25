package com.github.zlamb1.io;

import com.github.zlamb1.view.AppView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage loadImage(String resourcePath) {
        try {
            URL url = AppView.class.getClassLoader().getResource(resourcePath);
            if (url == null)
                return null;
            return ImageIO.read(url);
        } catch (IOException exc) {
            return null;
        }
    }

    public static Image loadImage(String resourcePath, int width, int height) {
        try {
            URL url = AppView.class.getClassLoader().getResource(resourcePath);
            if (url == null)
                return null;
            BufferedImage image = ImageIO.read(url);
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException exc) {
            return null;
        }
    }
}
