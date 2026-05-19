package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> getByUsername(String username) throws SQLException {
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csGetUserByUsername = conn.prepareCall(SQLQueries.GETBYUSERNAME_USER);
        ) {
            csGetUserByUsername.setString(1, username);

            ResultSet rsUser = csGetUserByUsername.executeQuery();
            if (rsUser.next()) {
                return Optional.of(mapRow(rsUser));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csUserWithUsernameExists = conn.prepareCall(SQLQueries.USERNAMEEXISTS_USER);
        ) {
            return exists(csUserWithUsernameExists, username);
        }
    }

    @Override
    public boolean emailExists(String email) throws SQLException {
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csUserWithEmailExists = conn.prepareCall(SQLQueries.EMAILEXISTS_USER);
        ) {
            return exists(csUserWithEmailExists, email);
        }
    }

    private boolean exists(CallableStatement cs, String value) throws SQLException {
        cs.setString(1, value);
        cs.registerOutParameter(2, Types.BIT);

        cs.execute();

        return cs.getBoolean(2);
    }

    public User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("IDUser"),
                rs.getString("Username"),
                rs.getString("Email"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Password"),
                rs.getInt("RoleID")
        );
    }

    @Override
    public void mapForInsert(CallableStatement cs, User item) throws SQLException {
        cs.setString(1, item.getUsername());
        cs.setString(2, item.getEmail());
        cs.setString(3, item.getFirstName());
        cs.setString(4, item.getLastName());
        cs.setString(5, item.getPassword());
        cs.setInt(6, item.getRoleId());
        cs.registerOutParameter(7, Types.INTEGER);
    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, User originalItem, User toUpdate) throws SQLException {
        cs.setInt(1, id);
        cs.setString(2, toUpdate.getUsername() == null ? originalItem.getUsername() : toUpdate.getUsername());
        cs.setString(3, toUpdate.getEmail() == null ? originalItem.getEmail() : toUpdate.getEmail());
        cs.setString(4, toUpdate.getFirstName() == null ? originalItem.getFirstName() : toUpdate.getFirstName());
        cs.setString(5, toUpdate.getLastName() == null ? originalItem.getLastName() : toUpdate.getLastName());
    }
}
