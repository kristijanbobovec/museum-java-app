package me.kristijanbobovec.museum.model.dto.exhibit;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionXMLExport;

public class ExhibitXMLExport {
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private int idExhibit;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "creationYear")
    private int creationYear;

    @JacksonXmlProperty(localName = "imagePath")
    private String imagePath;

    @JacksonXmlProperty(localName = "collection")
    private MuseumCollectionXMLExport museumCollection;

    public int getIdExhibit() {
        return idExhibit;
    }

    public void setIdExhibit(int idExhibit) {
        this.idExhibit = idExhibit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreationYear() {
        return creationYear;
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

    public MuseumCollectionXMLExport getMuseumCollection() {
        return museumCollection;
    }

    public void setMuseumCollection(MuseumCollectionXMLExport museumCollection) {
        this.museumCollection = museumCollection;
    }

    public ExhibitXMLExport() {
    }

    public ExhibitXMLExport(int idExhibit, String name, String description, int creationYear, String imagePath, MuseumCollectionXMLExport museumCollection) {
        this.idExhibit = idExhibit;
        this.name = name;
        this.description = description;
        this.creationYear = creationYear;
        this.imagePath = imagePath;
        this.museumCollection = museumCollection;
    }

    @Override
    public String toString() {
        return "ExhibitXMLExport{" +
                "idExhibit=" + idExhibit +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationYear=" + creationYear +
                ", imagePath='" + imagePath + '\'' +
                ", museumCollection=" + museumCollection +
                '}';
    }
}
