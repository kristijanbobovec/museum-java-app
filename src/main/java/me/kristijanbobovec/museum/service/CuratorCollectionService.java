package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.repository.CuratorCollectionRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;

public class CuratorCollectionService {
    private final CuratorCollectionRepository curatorCollectionRepository;

    public CuratorCollectionService(CuratorCollectionRepository curatorCollectionRepository) {
        this.curatorCollectionRepository = curatorCollectionRepository;
    }

    public void assign(int curatorId, int collectionId) throws SQLException {
        boolean success = curatorCollectionRepository.assign(curatorId, collectionId);
        if (!success) throw new DatabaseFailedToException(
                Messages.Database.DATABASE_FAILEDTO.formatted("assign", "curator-collection(" + curatorId + "," + collectionId + ")"));
    }

    public void remove(int curatorId, int collectionId) throws SQLException {
        boolean success = curatorCollectionRepository.remove(curatorId, collectionId);
        if (!success) throw new DatabaseFailedToException(
                Messages.Database.DATABASE_FAILEDTO.formatted("remove", "curator-collection(" + curatorId + "," + collectionId + ")"));
    }

    public void removeAllByCuratorId(int curatorId) throws SQLException {
        curatorCollectionRepository.removeAllByCuratorId(curatorId);
    }
}
