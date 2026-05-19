package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MuseumCollectionRepositoryImpl implements MuseumCollectionRepository{

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

    public MuseumCollection mapRow(ResultSet rsMuseumCollection) throws SQLException {
        return new MuseumCollection(rsMuseumCollection.getInt("IDMuseumCollection"), rsMuseumCollection.getString("Name"));
    }

    @Override
    public void mapForInsert(CallableStatement cs, MuseumCollection item) throws SQLException {
        cs.setString(1, item.getName());
        cs.registerOutParameter(2, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, MuseumCollection originalItem, MuseumCollection toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getName() == null ? originalItem.getName() : toUpdate.getName());
    }

}
