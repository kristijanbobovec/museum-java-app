package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class ImagePath {
    private int idImagePath;
    private String imagePath;
    private Exhibit exhibit;

    public ImagePath(String imagePath, Exhibit exhibit) {
        this.imagePath = imagePath;
        this.exhibit = exhibit;
    }

    public ImagePath(int idImagePath, String imagePath, Exhibit exhibit) {
        this(imagePath, exhibit);
        this.idImagePath = idImagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getIdImagePath() {
        return idImagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImagePath imagePath)) return false;
        return idImagePath == imagePath.idImagePath;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idImagePath);
    }

    @Override
    public String toString() {
        return "ImagePath{" +
                "idImagePath=" + idImagePath +
                ", imagePath='" + imagePath + '\'' +
                ", exhibit=" + exhibit +
                '}';
    }
}
