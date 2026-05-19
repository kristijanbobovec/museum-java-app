package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRepositoryImpl implements CountryRepository {
    @Override
    public Country mapRow(ResultSet rs) throws SQLException {
        return new Country(rs.getInt("IDCountry"), rs.getString("Name"));
    }
}
