package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends Repository<User>{
    Optional<User> getByUsername(String username) throws SQLException;
    boolean usernameExists(String username) throws SQLException;
    boolean emailExists(String email) throws SQLException;
}
