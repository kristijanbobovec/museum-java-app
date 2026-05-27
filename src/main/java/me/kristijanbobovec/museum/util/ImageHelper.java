package me.kristijanbobovec.museum.util;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import me.kristijanbobovec.museum.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ImageHelper {
    private static final Logger log = LoggerFactory.getLogger(ImageHelper.class);

    private ImageHelper() {}

    private static final Map<URI, Image> cache = new HashMap<>();

    public static Image loadImage(URI uri) {
        if (uri == null) return null;
        return cache.computeIfAbsent(uri, u -> new Image(u.toASCIIString()));
    }

    public static Image loadImage(URL url) {
        if (url == null) return null;
        try {
            return loadImage(url.toURI());
        } catch (URISyntaxException e) {
            log.warn("Failed to load an image!", e);
        }
        return null;
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
