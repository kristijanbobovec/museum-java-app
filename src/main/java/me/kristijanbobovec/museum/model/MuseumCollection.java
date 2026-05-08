package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class MuseumCollection {
    private int idMuseumCollection;
    private String name;

    public MuseumCollection(String name) {
        this.name = name;
    }

    public MuseumCollection(int idCollection, String name) {
        this(name);
        this.idMuseumCollection = idCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MuseumCollection that)) return false;
        return idMuseumCollection == that.idMuseumCollection;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMuseumCollection);
    }

    @Override
    public String toString() {
        return "MuseumCollection{" +
                "idCollection=" + idMuseumCollection +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCollection() {
        return idMuseumCollection;
    }

    public String getName() {
        return name;
    }
}
