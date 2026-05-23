package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ImagePathRepositoryImpl implements ImagePathRepository {

    @Override
    public int countReferences(String path) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(SQLQueries.IMAGE_USAGE_COUNT))
        {
            cs.setString("ImagePath", path);
            cs.registerOutParameter("ReferencesCount", Types.INTEGER);
            cs.execute();

            return cs.getInt("ReferencesCount");
        }
    }
}
