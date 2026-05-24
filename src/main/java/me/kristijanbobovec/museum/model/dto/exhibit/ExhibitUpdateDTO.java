package me.kristijanbobovec.museum.model.dto.exhibit;


import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.dto.Dto;

public record ExhibitUpdateDTO(String name, String description, int creationYear, String imagePath, int museumId, Integer museumCollectionId) implements Dto {
    public Exhibit mapToExhibit() {
        return new Exhibit(name, description, creationYear, imagePath, museumId, museumCollectionId);
    }

}
