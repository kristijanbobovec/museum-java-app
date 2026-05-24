package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.ImagePath;
import me.kristijanbobovec.museum.repository.ImagePathRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class ImagePathService {
    private final ImagePathRepository imagePathRepository;

    public ImagePathService(ImagePathRepository imagePathRepository) {
        this.imagePathRepository = imagePathRepository;
    }

    private void deleteIfNotReferences(String classpathPath) throws SQLException, IOException {
        if (countReferences(classpathPath) > 0) return;
        Files.deleteIfExists(resolveStoragePath(classpathPath));
    }

    private void copyToAssets(ImagePath imagePath) throws IOException {
        Path dest = resolveStoragePath(imagePath.getAssetsPath());
        if (Files.exists(dest)) return;
        Files.copy(Path.of(imagePath.getOriginalPath()), dest);
    }

    public void remove(String imagePath) throws SQLException, IOException {
        if (imagePath == null || imagePath.isEmpty()) return;
        deleteIfNotReferences(imagePath);
    }

    public void create(ImagePath imagePath) throws SQLException, IOException {
        if (imagePath.getAssetsPath() == null || imagePath.getAssetsPath().isEmpty()) return;
        if (imagePath.getOriginalPath() == null || imagePath.getOriginalPath().isEmpty()) return;
        copyToAssets(imagePath);
    }

    public void update(String oldPath, ImagePath newPath) throws SQLException, IOException {
        if (oldPath != null && !oldPath.isEmpty() && !oldPath.equals(newPath.getAssetsPath()))
            deleteIfNotReferences(oldPath);

        if (newPath.getAssetsPath() != null && !newPath.getAssetsPath().isEmpty() && !newPath.getAssetsPath().equals(oldPath))
            create(newPath);
    }

    private Path resolveStoragePath(String classpathPath) throws IOException {
        String fileName = Path.of(classpathPath).getFileName().toString();
        URL dirUrl = getClass().getResource("/assets/images/");
        if (dirUrl == null) throw new IOException("Assets images directory not found");
        try {
            return Path.of(dirUrl.toURI()).resolve(fileName);
        } catch (URISyntaxException e) {
            throw new IOException("Cannot resolve storage path for: " + classpathPath, e);
        }
    }

    private int countReferences(String path) throws SQLException {
        return imagePathRepository.countReferences(path);
    }



}
