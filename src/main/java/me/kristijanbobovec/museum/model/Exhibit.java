package me.kristijanbobovec.museum.model;

import java.util.Objects;
import java.util.Optional;

public class Exhibit implements Named {

    private int idExhibit;

    private String name;

    private String description;

    private int creationYear;

    private String imagePath;

    private MuseumCollection museumCollection;

    private Museum museum;

    private int museumId;

    private Integer museumCollectionId;

    public Exhibit(String name, String description, int creationYear, String imagePath, int museumId, Integer museumCollectionId) {
        this.name = name;
        this.description = description;
        this.creationYear = creationYear;
        this.imagePath = imagePath;
        this.museumId = museumId;
        this.museumCollectionId = museumCollectionId;
    }


    public Exhibit(String name, String description, int creationYear, String imagePath, MuseumCollection museumCollection, Museum museum) {
        this(name, description, creationYear, imagePath, museum.getIdMuseum(), Optional.ofNullable(museumCollection).map(MuseumCollection::getIdCollection).orElse(null));
        this.museumCollection = museumCollection;
        this.museum = museum;
    }

    public Exhibit(int idExhibit, String name, String description, int creationYear, String imagePath, MuseumCollection museumCollection, Museum museum) {
        this(name, description, creationYear, imagePath, museumCollection, museum);
        this.idExhibit = idExhibit;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    public Integer getMuseumCollectionId() {
        return museumCollectionId;
    }

    public void setMuseumCollectionId(Integer museumCollectionId) {
        this.museumCollectionId = museumCollectionId;
    }

    @Override
    public String toString() {
        return "Exhibit{" +
                "idExhibit=" + idExhibit +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationYear=" + creationYear +
                ", imagePath='" + imagePath + '\'' +
                ", museumCollection=" + museumCollection +
                ", museum=" + museum +
                ", museumId=" + museumId +
                ", museumCollectionId=" + museumCollectionId +
                '}';
    }
}
