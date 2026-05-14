package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csGetAllUsers = conn.prepareCall(SQLQueries.GETALL_USERS);
                ) {

            ResultSet rsUsers = csGetAllUsers.executeQuery();
            while (rsUsers.next()) {
                users.add(mapRow(rsUsers));
            }
        }

        return users;
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {
        try (
                Connection conn = DatabaseManager.getConnection();
                CallableStatement csGetUserById = conn.prepareCall(SQLQueries.GETBYID_USER);
        ) {
            csGetUserById.setInt(1, id);

            ResultSet rsUser = csGetUserById.executeQuery();
            if (rsUser.next()) {
                return Optional.of(mapRow(rsUser));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> insert(User user) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
        CallableStatement csInsertUser = conn.prepareCall(SQLQueries.INSERT_USER);
        ) {
            csInsertUser.setString(1, user.getUsername());
            csInsertUser.setString(2, user.getEmail());
            csInsertUser.setString(3, user.getFirstName());
            csInsertUser.setString(4, user.getLastName());
            csInsertUser.setString(5, user.getPassword());
            csInsertUser.setInt(6, user.getRoleId());
            csInsertUser.registerOutParameter(7, Types.INTEGER);

            csInsertUser.execute();

            int idUser = csInsertUser.getInt(7);
            return getById(idUser);
        }
    }

    @Override
    public boolean update(int id, User user) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csUpdateUser = conn.prepareCall(SQLQueries.UPDATE_USER);
        ) {
            Optional<User> nonUpdatedUser = getById(id);
            if (nonUpdatedUser.isEmpty()) return false;

            csUpdateUser.setInt(1, id);
            csUpdateUser.setString(2, user.getUsername() == null ? nonUpdatedUser.get().getUsername() : user.getUsername());
            csUpdateUser.setString(3, user.getEmail() == null ? nonUpdatedUser.get().getEmail() : user.getEmail());
            csUpdateUser.setString(4, user.getFirstName() == null ? nonUpdatedUser.get().getFirstName() : user.getFirstName());
            csUpdateUser.setString(5, user.getLastName() == null ? nonUpdatedUser.get().getLastName() : user.getLastName());

            return csUpdateUser.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement csDeleteUser = conn.prepareCall(SQLQueries.DELETE_USER)) {
            csDeleteUser.setInt(1, id);

            return csDeleteUser.executeUpdate() > 0;
        }
    }

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

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7)
        );
    }
}
