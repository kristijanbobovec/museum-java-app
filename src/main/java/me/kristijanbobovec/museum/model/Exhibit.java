package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class Exhibit {
    private int idExhibit;
    private String name;
    private String description;
    private int creationYear;
    private MuseumCollection museumCollection;
    private Museum museum;


    public Exhibit(String name, String description, int creationYear, MuseumCollection museumCollection, Museum museum) {
        this.name = name;
        this.description = description;
        this.creationYear = creationYear;
        this.museumCollection = museumCollection;
        this.museum = museum;
    }

    public Exhibit(int idExhibit, String name, String description, int creationYear, MuseumCollection museumCollection, Museum museum) {
        this(name, description, creationYear, museumCollection, museum);
        this.idExhibit = idExhibit;
    }

    @Override
    public String toString() {
        return "Exhibit{" +
                "idExhibit=" + idExhibit +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationYear=" + creationYear +
                ", collection=" + museumCollection +
                ", museum=" + museum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Exhibit exhibit)) return false;
        return idExhibit == exhibit.idExhibit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idExhibit);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }


    public int getIdExhibit() {
        return idExhibit;
    }

    public String getDescription() {
        return description;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public MuseumCollection getMuseumCollection() {
        return museumCollection;
    }

    public void setMuseumCollection(MuseumCollection museumCollection) {
        this.museumCollection = museumCollection;
    }

    public Museum getMuseum() {
        return museum;
    }
}
