package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuratorRepositoryImpl implements CuratorRepository {

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

    @Override
    public List<Curator> getAllWithDetails() throws SQLException {
        List<Curator> curators = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetAllCuratorsWithDetails = conn.prepareCall(SQLQueries.GETALL_CURATORS_WITH_DETAILS)) {
            ResultSet rsCurator = csGetAllCuratorsWithDetails.executeQuery();
            while (rsCurator.next()) {
                Curator curator = mapRow(rsCurator);
                mapDetailsToCurator(curator, rsCurator);
                curators.add(curator);
            }
        }
        return curators;
    }

    @Override
    public Optional<Curator> getByIdWithDetails(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csGetByIdCuratorWithDetails = conn.prepareCall(SQLQueries.GETBYID_CURATOR_WITH_DETAILS)) {
            csGetByIdCuratorWithDetails.setInt("IDCurator", id);
            ResultSet rsCurator = csGetByIdCuratorWithDetails.executeQuery();
            if (rsCurator.next()) {
                Curator curator = mapRow(rsCurator);
                mapDetailsToCurator(curator, rsCurator);
                return Optional.of(curator);
            }
        }
        return Optional.empty();
    }

    private void mapDetailsToCurator(Curator curator, ResultSet rsCurator) throws SQLException {
        curator.setMuseum(new Museum(
                rsCurator.getInt("MuseumID"),
                rsCurator.getString("MuseumName"),
                rsCurator.getString("MuseumAdress"),
                rsCurator.getInt("EstablishmentYear"),
                rsCurator.getString("MuseumDescription"),
                rsCurator.getString("MuseumImagePath"),
                rsCurator.getInt("CityID")
        ));
    }
}
