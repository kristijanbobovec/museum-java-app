package me.kristijanbobovec.museum.util;

import javafx.scene.image.Image;

import java.net.URL;

public class ImageHelper {
    private ImageHelper() {}

    public static Image loadImage(URL url) {
        return new Image(url.toExternalForm());
    }
}
