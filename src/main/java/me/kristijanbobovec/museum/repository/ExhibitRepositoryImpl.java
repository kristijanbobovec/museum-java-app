package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitRepositoryImpl implements ExhibitRepository {

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

    public Exhibit mapRow(ResultSet rs) throws SQLException {
        Museum museum = new Museum(
                rs.getInt("IDMuseum"),
                rs.getString("MuseumName"),
                rs.getString("MuseumAdress"),
                rs.getInt("EstablishmentYear"),
                rs.getString("MuseumDescription"),
                rs.getString("MuseumImagePath"),
                rs.getInt("CityID")
        );

        int collectionId = rs.getInt("MuseumCollectionID");
        MuseumCollection collection = rs.wasNull() ? null : new MuseumCollection(collectionId, rs.getString("CollectionName"));

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

    @Override
    public void mapForInsert(CallableStatement cs, Exhibit item) throws SQLException {
        cs.setString(1, item.getName());
        cs.setString(2, item.getDescription());
        cs.setInt(3, item.getCreationYear());
        cs.setString(4, item.getImagePath());

        if (item.getMuseumCollectionId() != null) {
            cs.setInt(5, item.getMuseumCollectionId());
        } else {
            cs.setNull(5, Types.INTEGER);
        }

        cs.setInt(6, item.getMuseumId());
        cs.registerOutParameter(7, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, Exhibit originalItem, Exhibit toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getName() == null ? originalItem.getName() : toUpdate.getName());
        cs.setString(3, toUpdate.getDescription() == null ? originalItem.getDescription() : toUpdate.getDescription());
        cs.setInt(4, toUpdate.getCreationYear() == 0 ? originalItem.getCreationYear() : toUpdate.getCreationYear());
        cs.setString(5, toUpdate.getImagePath() == null ? originalItem.getImagePath() : toUpdate.getImagePath());
        cs.setInt(6, toUpdate.getMuseumCollectionId() != null
                ? toUpdate.getMuseumCollectionId()
                : originalItem.getMuseumCollectionId());
        cs.setInt(7, toUpdate.getMuseumId() != 0
                ? toUpdate.getMuseumId()
                : originalItem.getMuseumId());
    }
}
