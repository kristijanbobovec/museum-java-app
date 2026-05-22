package me.kristijanbobovec.museum.model.dto.city;

import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.dto.Dto;

public record CityUpdateDTO(String name, int countryID) implements Dto {
    public City mapToCity() {
        return new City(name, countryID);
    }
}
