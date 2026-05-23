package me.kristijanbobovec.museum.util;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageHelper {
    private ImageHelper() {}

    private static final Map<URL, Image> cache = new HashMap<>();

    public static Image loadImage(URL url) {
        if (url == null) return null;
        return cache.computeIfAbsent(url, u -> new Image(u.toExternalForm()));
    }

    public static String buildStringURL(String imageName) {
        return "/assets/images/%s".formatted(imageName);
    }
}
