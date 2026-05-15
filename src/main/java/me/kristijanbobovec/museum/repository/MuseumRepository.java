package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Museum;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MuseumRepository extends Repository<Museum>{
    List<Museum> getAllWithDetails() throws SQLException;
    Optional<Museum> getByIdWithDetails(int id) throws SQLException;
}
