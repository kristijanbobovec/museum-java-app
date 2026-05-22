package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuseumRepositoryImpl implements MuseumRepository {

    @Override
    public Optional<Museum> getById(int id) throws SQLException {
        return getByIdWithDetails(id);
    }

    @Override
    public List<Museum> getAll() throws SQLException {
        return getAllWithDetails();
    }

    public void mapForInsert(CallableStatement cs, Museum item) throws SQLException {
        cs.setString(1, item.getName());
        cs.setString(2, item.getAdress());
        cs.setInt(3, item.getEstablishmentYear());
        cs.setString(4, item.getDescription());
        cs.setString(5, item.getImagePath());
        cs.setInt(6, item.getCityId());
        cs.registerOutParameter(7, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, Museum originalItem, Museum toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getName() == null ? originalItem.getName() : toUpdate.getName());
        cs.setString(3, toUpdate.getAdress() == null ? originalItem.getAdress() : toUpdate.getAdress());
        cs.setInt(4, toUpdate.getEstablishmentYear() == 0 ? originalItem.getEstablishmentYear() : toUpdate.getEstablishmentYear());
        cs.setString(5, toUpdate.getDescription() == null ? originalItem.getDescription() : toUpdate.getDescription());
        cs.setString(6, toUpdate.getImagePath() == null ? originalItem.getImagePath() : toUpdate.getImagePath());
        cs.setInt(7, toUpdate.getCityId() == 0 ? originalItem.getCityId() : toUpdate.getCityId());
    }


    public Museum mapRow(ResultSet rs) throws SQLException {
        return new Museum(
                rs.getInt("IDMuseum"),
                rs.getString("Name"),
                rs.getString("Adress"),
                rs.getInt("EstablishmentYear"),
                rs.getString("Description"),
                rs.getString("ImagePath"),
                rs.getInt("CityID")
        );
    }

    private List<Museum> getAllWithDetails() throws SQLException {
        List<Museum> museums = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
        CallableStatement csGetAllMuseumsWithDetials = conn.prepareCall(SQLQueries.GETALL_MUSEUMS_WITH_DETAILS)) {
            ResultSet rsMuseum = csGetAllMuseumsWithDetials.executeQuery();

            while (rsMuseum.next()) {
                Museum museum = mapRow(rsMuseum);
                mapDetailsToMuseum(museum, rsMuseum);
                museums.add(museum);
            }
        }
        return museums;
    }

    private Optional<Museum> getByIdWithDetails(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetByIdMuseumsWithDetials = conn.prepareCall(SQLQueries.GETBYID_MUSEUM_WITH_DETAILS)) {

            csGetByIdMuseumsWithDetials.setInt("IDMuseum", id);

            ResultSet rsMuseum = csGetByIdMuseumsWithDetials.executeQuery();

            if (rsMuseum.next()) {
                Museum museum = mapRow(rsMuseum);
                mapDetailsToMuseum(museum, rsMuseum);
                return Optional.of(mapRow(rsMuseum));
            }

        }
        return Optional.empty();
    }

    private void mapDetailsToMuseum(Museum museum, ResultSet rsMuseum) throws SQLException {
        museum.setCountry(new Country(rsMuseum.getInt("IDCountry"),
                rsMuseum.getString("CountryName")));

        museum.setCity(new City(rsMuseum.getInt("IDCity"),
                rsMuseum.getString("CityName"), rsMuseum.getInt("IDCountry")));

    }
}
