package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Country;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CountryRepositoryImpl implements CountryRepository {

    @Override
    public Country mapRow(ResultSet rs) throws SQLException {
        return new Country(rs.getInt("IDCountry"), rs.getString("Name"));
    }

    @Override
    public void mapForInsert(CallableStatement cs, Country item) throws SQLException {
        cs.setString(1, item.getName());
        cs.registerOutParameter(2, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, Country originalItem, Country toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getName() == null ? originalItem.getName() : toUpdate.getName());
    }
}
