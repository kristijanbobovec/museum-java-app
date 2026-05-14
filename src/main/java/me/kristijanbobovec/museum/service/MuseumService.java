package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.repository.MuseumRepository;

public class MuseumService {
    private final MuseumRepository museumRepository;

    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }


}
