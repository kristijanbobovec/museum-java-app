package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class Museum {
    private int idMuseum;
    private String name;
    private String adress;
    private int establishmentYear;
    private String description;
    private String imagePath;
    private int cityId;
    private City city;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Museum(String name, String adress, int establishmentYear, String description, String imagePath, int cityId) {
        this.name = name;
        this.adress = adress;
        this.establishmentYear = establishmentYear;
        this.description = description;
        this.imagePath = imagePath;
        this.cityId = cityId;
    }

    public Museum(int idMuseum, String name, String adress, int establishmentYear, String description, String imagePath, int cityId) {
        this(name, adress, establishmentYear, description, imagePath, cityId);
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

    @Override
    public String toString() {
        return "Museum{" +
                "idMuseum=" + idMuseum +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", establishmentYear=" + establishmentYear +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
