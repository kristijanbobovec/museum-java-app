package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuseumCollectionRepositoryImpl implements MuseumCollectionRepository{

    @Override
    public Optional<MuseumCollection> getById(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetCollectionById = conn.prepareCall(SQLQueries.GETBYID_MUSEUMCOLLECTION)) {
            csGetCollectionById.setInt("IDMuseumCollection", id);

            ResultSet rsMuseumCollection = csGetCollectionById.executeQuery();
            if (rsMuseumCollection.next()) {
                return Optional.of(mapRow(rsMuseumCollection));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MuseumCollection> getByMuseumId(int museumId) throws SQLException {
        List<MuseumCollection> museumCollections = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetMuseumCollectionByMuseumId = conn.prepareCall(SQLQueries.GETBYMUSEUMID_MUSEUMCOLLECTION);
        ) {
            csGetMuseumCollectionByMuseumId.setInt("MuseumID", museumId);
            ResultSet rsMuseumCollection = csGetMuseumCollectionByMuseumId.executeQuery();

            while (rsMuseumCollection.next()) {
                museumCollections.add(mapRow(rsMuseumCollection));
            }
        }
        return museumCollections;
    }

    @Override
    public Optional<MuseumCollection> insert(MuseumCollection museumCollection) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean update(int id, MuseumCollection museumCollection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    private MuseumCollection mapRow(ResultSet rsMuseumCollection) throws SQLException {
        return new MuseumCollection(rsMuseumCollection.getInt("IDMuseumCollection"), rsMuseumCollection.getString("Name"));
    }

}
