package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.MuseumCollection;

import java.sql.SQLException;
import java.util.List;

public interface MuseumCollectionRepository extends Repository<MuseumCollection> {
    List<MuseumCollection> getByMuseumId(int museumId) throws SQLException;
}
