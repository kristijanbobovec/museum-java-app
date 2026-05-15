package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExhibitRepositoryImpl implements ExhibitRepository {

    @Override
    public List<Exhibit> getAll() throws SQLException {
        List<Exhibit> exhibits = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetAllExhibits = conn.prepareCall(SQLQueries.GETALL_EXHIBITS)) {
            ResultSet rsExhibits = csGetAllExhibits.executeQuery();
            while (rsExhibits.next()) {
                exhibits.add(mapRow(rsExhibits));
            }
        }
        return exhibits;
    }

    @Override
    public Optional<Exhibit> getById(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetExhibitById = conn.prepareCall(SQLQueries.GETBYID_EXHIBIT)) {
            csGetExhibitById.setInt(1, id);
            ResultSet rsExhibit = csGetExhibitById.executeQuery();
            if (rsExhibit.next()) {
                return Optional.of(mapRow(rsExhibit));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Exhibit> getByMuseumId(int museumId) throws SQLException {
        List<Exhibit> exhibits = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetExhibitsByMuseumId = conn.prepareCall(SQLQueries.GETBYMUSEUMID_EXHIBIT)) {
            csGetExhibitsByMuseumId.setInt(1, museumId);
            ResultSet rsExhibits = csGetExhibitsByMuseumId.executeQuery();
            while (rsExhibits.next()) {
                exhibits.add(mapRow(rsExhibits));
            }
        }
        return exhibits;
    }

    @Override
    public Optional<Exhibit> insert(Exhibit exhibit) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csInsertExhibit = conn.prepareCall(SQLQueries.INSERT_EXHIBIT)) {
            csInsertExhibit.setString(1, exhibit.getName());
            csInsertExhibit.setString(2, exhibit.getDescription());
            csInsertExhibit.setInt(3, exhibit.getCreationYear());
            csInsertExhibit.setString(4, exhibit.getImagePath());
            csInsertExhibit.setInt(5, exhibit.getMuseumCollection().getIdCollection());
            csInsertExhibit.setInt(6, exhibit.getMuseum().getIdMuseum());
            csInsertExhibit.registerOutParameter(7, Types.INTEGER);

            csInsertExhibit.execute();

            int idExhibit = csInsertExhibit.getInt(7);
            return getById(idExhibit);
        }
    }

    @Override
    public boolean update(int id, Exhibit exhibit) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csUpdateExhibit = conn.prepareCall(SQLQueries.UPDATE_EXHIBIT)) {
            Optional<Exhibit> existing = getById(id);
            if (existing.isEmpty()) return false;

            Exhibit current = existing.get();
            csUpdateExhibit.setInt(1, id);
            csUpdateExhibit.setString(2, exhibit.getName() == null ? current.getName() : exhibit.getName());
            csUpdateExhibit.setString(3, exhibit.getDescription() == null ? current.getDescription() : exhibit.getDescription());
            csUpdateExhibit.setInt(4, exhibit.getCreationYear() == 0 ? current.getCreationYear() : exhibit.getCreationYear());
            csUpdateExhibit.setString(5, exhibit.getImagePath() == null ? current.getImagePath() : exhibit.getImagePath());
            csUpdateExhibit.setInt(6, exhibit.getMuseumCollection() != null
                    ? exhibit.getMuseumCollection().getIdCollection()
                    : current.getMuseumCollection().getIdCollection());
            csUpdateExhibit.setInt(7, exhibit.getMuseum() != null
                    ? exhibit.getMuseum().getIdMuseum()
                    : current.getMuseum().getIdMuseum());

            return csUpdateExhibit.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csDeleteExhibit = conn.prepareCall(SQLQueries.DELETE_EXHIBIT)) {
            csDeleteExhibit.setInt(1, id);
            return csDeleteExhibit.executeUpdate() > 0;
        }
    }

    private Exhibit mapRow(ResultSet rs) throws SQLException {
        Museum museum = new Museum(
                rs.getInt("IDMuseum"),
                rs.getString("MuseumName"),
                rs.getString("MuseumAdress"),
                rs.getInt("EstablishmentYear"),
                rs.getString("MuseumDescription"),
                rs.getString("MuseumImagePath"),
                rs.getInt("CityID")
        );

        MuseumCollection collection = new MuseumCollection(
                rs.getInt("MuseumCollectionID"),
                rs.getString("CollectionName")
        );

        return new Exhibit(
                rs.getInt("IDExhibit"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getInt("CreationYear"),
                rs.getString("ImagePath"),
                collection,
                museum
        );
    }
}
