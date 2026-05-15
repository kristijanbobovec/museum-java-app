package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.repository.MuseumRepository;

import java.sql.SQLException;
import java.util.List;

public class MuseumService {
    private final MuseumRepository museumRepository;

    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    public List<Museum> getAllWithoutDetails() throws SQLException {
        return museumRepository.getAllWithDetails();
    }

}
