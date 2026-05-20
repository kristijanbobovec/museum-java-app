package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class City {
    private int idCity;
    private String name;
    private int countryId;
    private Country country;

    public City(int idCity, String name, int countryId, Country country) {
        this(idCity, name, countryId);
        this.country = country;
    }

    public City(int idCity, String name, int countryId) {
        this(name, countryId);
        this.idCity = idCity;
    }

    public City(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getIdCity() {
        return idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof City city)) return false;
        return idCity == city.idCity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCity);
    }

    @Override
    public String toString() {
        return "City{" +
                "idCity=" + idCity +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
