package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class ImagePath {
    private String originalPath;
    private String assetsPath;

    public ImagePath(String originalPath, String assetsPath) {
        this.originalPath = originalPath;
        this.assetsPath = assetsPath;
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public void setAssetsPath(String assetsPath) {
        this.assetsPath = assetsPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImagePath imagePath)) return false;
        return Objects.equals(originalPath, imagePath.originalPath) && Objects.equals(assetsPath, imagePath.assetsPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalPath, assetsPath);
    }

    @Override
    public String toString() {
        return "ImagePath{" +
                "originalPath='" + originalPath + '\'' +
                ", assetsPath='" + assetsPath + '\'' +
                '}';
    }
}
