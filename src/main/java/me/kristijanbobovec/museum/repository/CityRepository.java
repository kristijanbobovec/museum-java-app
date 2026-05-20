package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.City;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CityRepository extends Repository<City> {
    List<City> getAllWithDetails() throws SQLException;
    Optional<City> getByIdWithDetails(int id) throws SQLException;
}
