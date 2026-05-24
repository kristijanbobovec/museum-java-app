package me.kristijanbobovec.museum.model.dto.curator;


import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.dto.Dto;

public record CuratorUpdateDTO(String firstName, String lastName, int museumId) implements Dto {
    public Curator mapToCurator() {
        return new Curator(firstName, lastName, museumId);
    }
}
