package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CuratorCollectionRepositoryImpl implements CuratorCollectionRepository {

    @Override
    public boolean assign(int curatorId, int collectionId) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(SQLQueries.INSERT_CURATOR_COLLECTION)) {
            cs.setInt(1, curatorId);
            cs.setInt(2, collectionId);
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean remove(int curatorId, int collectionId) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(SQLQueries.DELETE_CURATOR_COLLECTION)) {
            cs.setInt(1, curatorId);
            cs.setInt(2, collectionId);
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public void removeAllByCuratorId(int curatorId) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(SQLQueries.DELETE_CURATOR_COLLECTION_BY_CURATOR)) {
            cs.setInt(1, curatorId);
            cs.executeUpdate();
        }
    }
}
