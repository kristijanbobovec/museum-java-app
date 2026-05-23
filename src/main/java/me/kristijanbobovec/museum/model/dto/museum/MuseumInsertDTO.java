package me.kristijanbobovec.museum.model.dto.museum;


import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.dto.Dto;

public record MuseumInsertDTO(String name, String address, int establishmentYear, String description, String imagePath, int cityId) implements Dto {
    public Museum mapToMuseum() {
        return new Museum(name, address, establishmentYear, description, imagePath, cityId);
    }

}
