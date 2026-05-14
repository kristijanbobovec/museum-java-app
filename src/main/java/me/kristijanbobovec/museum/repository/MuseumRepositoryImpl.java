package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuseumRepositoryImpl implements MuseumRepository {
    @Override
    public List<Museum> getAll() throws SQLException {
        List<Museum> museums = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetAllMuseums = conn.prepareCall(SQLQueries.GETALL_MUSEUMS)) {

            ResultSet rsMuseums = csGetAllMuseums.executeQuery();
            while (rsMuseums.next()) {
                museums.add(mapRow(rsMuseums));
            }
        }
        return museums;
    }

    @Override
    public Optional<Museum> getById(int id) throws SQLException {
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csGetMuseumById = conn.prepareCall(SQLQueries.GETBYID_MUSEUM);
        ) {
            csGetMuseumById.setInt(1, id);

            ResultSet rsMuseum = csGetMuseumById.executeQuery();
            if (rsMuseum.next()) {
                return Optional.of(mapRow(rsMuseum));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Museum> insert(Museum museum) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csInsertMuseum = conn.prepareCall(SQLQueries.INSERT_MUSEUM);
        ) {
            csInsertMuseum.setString(1, museum.getName());
            csInsertMuseum.setString(2, museum.getAdress());
            csInsertMuseum.setInt(3, museum.getEstablishmentYear());
            csInsertMuseum.setString(4, museum.getDescription());
            csInsertMuseum.setString(5, museum.getImagePath());
            csInsertMuseum.setInt(6, museum.getCityId());
            csInsertMuseum.registerOutParameter(7, Types.INTEGER);

            csInsertMuseum.execute();

            int idMuseum = csInsertMuseum.getInt(7);
            return getById(idMuseum);
        }
    }

    @Override
    public boolean update(int id, Museum museum) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csUpdateMuseum = conn.prepareCall(SQLQueries.UPDATE_MUSEUM);
        ) {
            Optional<Museum> nonUpdatedMuseum = getById(id);
            if (nonUpdatedMuseum.isEmpty()) return false;

            csUpdateMuseum.setInt(1, id);
            csUpdateMuseum.setString(2, museum.getName() == null ? nonUpdatedMuseum.get().getName() : museum.getName());
            csUpdateMuseum.setString(3, museum.getAdress() == null ? nonUpdatedMuseum.get().getAdress() : museum.getAdress());
            csUpdateMuseum.setInt(4, museum.getEstablishmentYear() == 0 ? nonUpdatedMuseum.get().getEstablishmentYear() : museum.getEstablishmentYear());
            csUpdateMuseum.setString(5, museum.getDescription() == null ? nonUpdatedMuseum.get().getDescription() : museum.getDescription());
            csUpdateMuseum.setString(6, museum.getImagePath() == null ? nonUpdatedMuseum.get().getImagePath() : museum.getImagePath());
            csUpdateMuseum.setInt(7, museum.getCityId() == 0 ? nonUpdatedMuseum.get().getCityId() : museum.getCityId());


            return csUpdateMuseum.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csDeleteMuseum = conn.prepareCall(SQLQueries.DELETE_MUSEUM)) {
            csDeleteMuseum.setInt(1, id);

            return csDeleteMuseum.executeUpdate() > 0;
        }
    }

    private Museum mapRow(ResultSet rs) throws SQLException {
        return new Museum(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7)
        );
    }
}
