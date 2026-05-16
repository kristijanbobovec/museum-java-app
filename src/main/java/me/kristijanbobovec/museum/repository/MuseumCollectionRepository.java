package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.MuseumCollection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MuseumCollectionRepository {
    List<MuseumCollection> getByMuseumId(int museumId) throws SQLException;
    Optional<MuseumCollection> getById(int id) throws SQLException;
    Optional<MuseumCollection> insert(MuseumCollection museumCollection) throws SQLException;
    boolean update(int id, MuseumCollection museumCollection) throws SQLException;
    boolean delete(int id) throws SQLException;
}
