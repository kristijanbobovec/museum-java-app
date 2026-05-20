package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepositoryImpl implements CityRepository {

    @Override
    public City mapRow(ResultSet rs) throws SQLException {
        return new City(
                rs.getInt("IDCity"),
                rs.getString("Name"),
                rs.getInt("CountryID")
        );
    }

    @Override
    public void mapForInsert(CallableStatement cs, City item) throws SQLException {
        cs.setString(1, item.getName());
        cs.setInt(2, item.getCountryId());
        cs.registerOutParameter(3, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, City originalItem, City toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getName() == null ? originalItem.getName() : toUpdate.getName());
        cs.setInt(3, toUpdate.getCountryId() == 0 ? originalItem.getCountryId() : toUpdate.getCountryId());
    }

    @Override
    public List<City> getAllWithDetails() throws SQLException {
        List<City> cities = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetAllCitiesWithDetails = conn.prepareCall(SQLQueries.GETALL_CITIES_WITH_DETAILS)) {
            ResultSet rsCity = csGetAllCitiesWithDetails.executeQuery();
            while (rsCity.next()) {
                City city = mapRow(rsCity);
                mapDetailsToCity(city, rsCity);
                cities.add(city);
            }
        }
        return cities;
    }

    @Override
    public Optional<City> getByIdWithDetails(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetByIdCityWithDetails = conn.prepareCall(SQLQueries.GETBYID_CITY_WITH_DETAILS)) {
            csGetByIdCityWithDetails.setInt("IDCity", id);
            ResultSet rsCity = csGetByIdCityWithDetails.executeQuery();
            if (rsCity.next()) {
                City city = mapRow(rsCity);
                mapDetailsToCity(city, rsCity);
                return Optional.of(city);
            }
        }
        return Optional.empty();
    }

    private void mapDetailsToCity(City city, ResultSet rsCity) throws SQLException {
        city.setCountry(new Country(rsCity.getInt("IDCountry"), rsCity.getString("CountryName")));
    }
}
