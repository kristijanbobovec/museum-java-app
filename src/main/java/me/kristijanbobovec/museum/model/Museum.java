package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class Museum {
    private int idMuseum;
    private String name;
    private String adress;
    private int establishmentYear;
    private String description;
    private String imagePath;
    private City city;

    public Museum(String name, String adress, int establishmentYear, String description, String imagePath, City city) {
        this.name = name;
        this.adress = adress;
        this.establishmentYear = establishmentYear;
        this.description = description;
        this.imagePath = imagePath;
        this.city = city;
    }

    public Museum(int idMuseum, String name, String adress, int establishmentYear, String description, String imagePath, City city) {
        this(name, adress, establishmentYear, description, imagePath, city);
        this.idMuseum = idMuseum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setEstablishmentYear(int establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getIdMuseum() {
        return idMuseum;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Museum museum)) return false;
        return idMuseum == museum.idMuseum;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMuseum);
    }

    public int getEstablishmentYear() {
        return establishmentYear;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public City getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "idMuseum=" + idMuseum +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", establishmentYear=" + establishmentYear +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", city=" + city +
                '}';
    }
}
