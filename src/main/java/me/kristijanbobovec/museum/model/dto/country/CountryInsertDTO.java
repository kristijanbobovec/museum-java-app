package me.kristijanbobovec.museum.model.dto.country;


import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.dto.Dto;

public record CountryInsertDTO(String name) implements Dto {
    public Country mapToCountry() {
        return new Country(name);
    }

}
