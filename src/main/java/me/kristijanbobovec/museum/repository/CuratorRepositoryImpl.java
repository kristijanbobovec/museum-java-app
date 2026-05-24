package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.*;

public class CuratorRepositoryImpl implements CuratorRepository {

    @Override
    public Optional<Curator> getById(int id) throws SQLException {
        return getByIdWithDetails(id);
    }

    @Override
    public List<Curator> getAll() throws SQLException {
        return getAllWithDetails();
    }

    @Override
    public Curator mapRow(ResultSet rs) throws SQLException {
        return new Curator(
                rs.getInt("IDCurator"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getInt("MuseumID")
        );
    }

    @Override
    public void mapForInsert(CallableStatement cs, Curator item) throws SQLException {
        cs.setString(1, item.getFirstName());
        cs.setString(2, item.getLastName());
        cs.setInt(3, item.getMuseumId());
        cs.registerOutParameter(4, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, Curator originalItem, Curator toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getFirstName() == null ? originalItem.getFirstName() : toUpdate.getFirstName());
        cs.setString(3, toUpdate.getLastName() == null ? originalItem.getLastName() : toUpdate.getLastName());
        cs.setInt(4, toUpdate.getMuseumId() == 0 ? originalItem.getMuseumId() : toUpdate.getMuseumId());
    }

    private List<Curator> getAllWithDetails() throws SQLException {
        List<Curator> curators = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetAll = conn.prepareCall(SQLQueries.GETALL_CURATORS_WITH_DETAILS);
             CallableStatement csGetCollections = conn.prepareCall(SQLQueries.GETALL_CURATOR_COLLECTIONS)) {

            ResultSet rsCurator = csGetAll.executeQuery();
            while (rsCurator.next()) {
                Curator curator = mapRow(rsCurator);
                mapMuseumToCurator(curator, rsCurator);
                curators.add(curator);
            }

            Map<Integer, Curator> curatorMap = new LinkedHashMap<>();
            for (Curator c : curators) curatorMap.put(c.getIdCurator(), c);
            curators.forEach(c -> c.setMuseumCollections(new ArrayList<>()));

            ResultSet rsCollections = csGetCollections.executeQuery();
            while (rsCollections.next()) {
                int curatorId = rsCollections.getInt("CuratorID");
                Curator curator = curatorMap.get(curatorId);
                if (curator != null) {
                    curator.getMuseumCollections().add(mapCollection(rsCollections));
                }
            }
        }
        return curators;
    }

    private Optional<Curator> getByIdWithDetails(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetById = conn.prepareCall(SQLQueries.GETBYID_CURATOR_WITH_DETAILS);
             CallableStatement csGetCollections = conn.prepareCall(SQLQueries.GETBYID_CURATOR_COLLECTIONS)) {

            csGetById.setInt("IDCurator", id);
            ResultSet rsCurator = csGetById.executeQuery();
            if (!rsCurator.next()) return Optional.empty();

            Curator curator = mapRow(rsCurator);
            mapMuseumToCurator(curator, rsCurator);

            List<MuseumCollection> collections = new ArrayList<>();
            csGetCollections.setInt(1, id);
            ResultSet rsCollections = csGetCollections.executeQuery();
            while (rsCollections.next()) {
                collections.add(mapCollection(rsCollections));
            }
            curator.setMuseumCollections(collections);

            return Optional.of(curator);
        }
    }

    private void mapMuseumToCurator(Curator curator, ResultSet rs) throws SQLException {
        curator.setMuseum(new Museum(
                rs.getInt("MuseumID"),
                rs.getString("MuseumName"),
                rs.getString("MuseumAdress"),
                rs.getInt("EstablishmentYear"),
                rs.getString("MuseumDescription"),
                rs.getString("MuseumImagePath"),
                rs.getInt("CityID")
        ));
    }

    private MuseumCollection mapCollection(ResultSet rs) throws SQLException {
        return new MuseumCollection(
                rs.getInt("IDMuseumCollection"),
                rs.getString("CollectionName")
        );
    }
}
