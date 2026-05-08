package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class Country {
    private int idCountry;
    private String name;
    public Country(String name) {
        this.name = name;
    }
    public Country(int idCountry, String name) {
        this(name);
        this.idCountry = idCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Country country)) return false;
        return idCountry == country.idCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCountry() {
        return idCountry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCountry);
    }

    @Override
    public String toString() {
        return "Country{" +
                "idCountry=" + idCountry +
                ", name='" + name + '\'' +
                '}';
    }
}
