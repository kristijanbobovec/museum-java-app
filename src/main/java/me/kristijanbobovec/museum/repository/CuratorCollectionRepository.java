package me.kristijanbobovec.museum.repository;

import java.sql.SQLException;

public interface CuratorCollectionRepository {
    boolean assign(int curatorId, int collectionId) throws SQLException;
    boolean remove(int curatorId, int collectionId) throws SQLException;
    void removeAllByCuratorId(int curatorId) throws SQLException;
}
