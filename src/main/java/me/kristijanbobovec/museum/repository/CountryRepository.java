package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.factory.StatementFactory;
import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.Country;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CountryRepository {
    public Country mapRow(ResultSet rs) throws SQLException;

    default List<Country> getAll() throws SQLException {
        List<Country> listOfElements = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "getAll"))) {

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                listOfElements.add(mapRow(rs));
            }

        }

        return listOfElements;
    }
}
