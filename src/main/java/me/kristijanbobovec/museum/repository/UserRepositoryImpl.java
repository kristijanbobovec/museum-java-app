package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.manger.DatabaseManager;
import me.kristijanbobovec.museum.mapper.UserMapper;
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
                users.add(UserMapper.mapFromRepo(rsUsers));
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
                return Optional.of(UserMapper.mapFromRepo(rsUser));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> insert(User user) {
        return Optional.empty();
    }

    @Override
    public boolean update(int id, User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
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
                return Optional.of(UserMapper.mapFromRepo(rsUser));
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
}
