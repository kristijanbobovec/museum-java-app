package me.kristijanbobovec.museum.service;

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

    public void deleteIfNotReferences(String classpathPath) throws SQLException, IOException {
        if (countReferences(classpathPath) > 0) return;
        Files.deleteIfExists(resolveStoragePath(classpathPath));
    }

    public void createIfNotExists(String oPath, String classpathPath) throws IOException {
        Path dest = resolveStoragePath(classpathPath);
        if (Files.exists(dest)) return;
        Files.copy(Path.of(oPath), dest);
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
