package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitXMLExport;
import me.kristijanbobovec.museum.model.dto.museum.MuseumWrapper;
import me.kristijanbobovec.museum.model.dto.museum.MuseumXMLExportDTO;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionXMLExport;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public interface MuseumRepository extends Repository<Museum> {
    static MuseumWrapper getAllMuseumsForXMLExport() throws SQLException {
        Map<Integer, MuseumXMLExportDTO> museumMap = new LinkedHashMap<>();

            try (Connection conn = DatabaseManager.getConnection();
                 CallableStatement cs = conn.prepareCall(SQLQueries.GETALL_MUSEUMS_FOR_XML_EXPORT)) {

                ResultSet rs = cs.executeQuery();
                while (rs.next()) {
                    int museumId = rs.getInt("IDMuseum");

                    if (!museumMap.containsKey(museumId)) {
                        museumMap.put(museumId, new MuseumXMLExportDTO(
                                museumId,
                                rs.getString("Name"),
                                rs.getString("Adress"),
                                rs.getInt("EstablishmentYear"),
                                rs.getString("Description"),
                                rs.getString("ImagePath"),
                                rs.getInt("CityID"),
                                new ArrayList<>()
                        ));
                    }

                    MuseumXMLExportDTO museum = museumMap.get(museumId);

                    int exhibitId = rs.getInt("IDExhibit");
                    if (!rs.wasNull()) {
                        MuseumCollectionXMLExport collection = null;
                        int collectionId = rs.getInt("IDMuseumCollection");
                        if (!rs.wasNull()) {
                            collection = new MuseumCollectionXMLExport(collectionId, rs.getString("CollectionName"));
                        }

                        museum.getExhibits().add(new ExhibitXMLExport(
                                exhibitId,
                                rs.getString("ExhibitName"),
                                rs.getString("ExhibitDescription"),
                                rs.getInt("CreationYear"),
                                rs.getString("ExhibitImagePath"),
                                collection
                        ));
                    }
                }
            }
            return new MuseumWrapper(new ArrayList<>(museumMap.values()));
        }
}
