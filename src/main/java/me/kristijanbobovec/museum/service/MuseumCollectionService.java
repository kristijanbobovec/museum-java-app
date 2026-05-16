package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.repository.MuseumCollectionRepository;

import java.sql.SQLException;
import java.util.List;

public class MuseumCollectionService {

    private final MuseumCollectionRepository museumCollectionRepository;

    public MuseumCollectionService(MuseumCollectionRepository museumCollectionRepository) {
        this.museumCollectionRepository = museumCollectionRepository;
    }


    public List<MuseumCollection> getByMuseumId(int museumId) throws SQLException {
        return museumCollectionRepository.getByMuseumId(museumId);
    }


}
