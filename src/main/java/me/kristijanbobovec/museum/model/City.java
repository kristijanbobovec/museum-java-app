package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class City {
    private int idCity;
    private String name;
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City(int idCity, String name, Country country) {
        this(name, country);
        this.idCity = idCity;
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
