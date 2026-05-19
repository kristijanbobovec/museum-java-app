package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Museum;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CityRepositoryImpl implements CityRepository{
    @Override
    public List<Museum> getAllWithDetails() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<Museum> getByIdWithDetails(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public City mapRow(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public void mapForInsert(CallableStatement cs, City item) throws SQLException {

    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, City originalItem, City toUpdate) throws SQLException {

    }
}
