package me.kristijanbobovec.museum.model.dto.country;


import me.kristijanbobovec.museum.model.Country;

public record CountryUpdateDTO(String name) {
    public Country mapToCountry() {
        return new Country(name);
    }

}
