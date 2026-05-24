package me.kristijanbobovec.museum.util;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import me.kristijanbobovec.museum.Main;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

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

    public static void handleBrowse(Consumer<String> setOriginalPath, Consumer<String>  setImagePathField) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All images", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        fileChooser.setTitle("Choose the image");

        Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(Main.getPrimaryStage()));

        file.ifPresent(f -> {
            Path path = f.toPath();
            String originalUrl = path.toString();
            String assetsUrl = ImageHelper.buildStringURL(path.getFileName().toString());

            setOriginalPath.accept(originalUrl);
            setImagePathField.accept(assetsUrl);
        });
    }
}
