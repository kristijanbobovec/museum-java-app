package me.kristijanbobovec.museum.model.dto.museumcollection;

import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.model.dto.Dto;

public record MuseumCollectionInsertDTO(String name) implements Dto {
    public MuseumCollection mapToMuseumCollection() {
        return new MuseumCollection(name);
    };
}
