package me.kristijanbobovec.museum.model.dto.museumcollection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MuseumCollectionXMLExport {
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private int idMuseumCollection;

    @JacksonXmlProperty(localName = "name")
    private String name;

    public MuseumCollectionXMLExport() {
    }

    public MuseumCollectionXMLExport(int idMuseumCollection, String name) {
        this.idMuseumCollection = idMuseumCollection;
        this.name = name;
    }

    public int getIdMuseumCollection() {
        return idMuseumCollection;
    }

    public void setIdMuseumCollection(int idMuseumCollection) {
        this.idMuseumCollection = idMuseumCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MuseumCollectionXMLExport{" +
                "idMuseumCollection=" + idMuseumCollection +
                ", name='" + name + '\'' +
                '}';
    }
}
