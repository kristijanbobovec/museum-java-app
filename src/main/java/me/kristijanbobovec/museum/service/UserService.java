package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepoistory) {
        this.userRepository = userRepoistory;
    }

    public Optional<User> getByUsername(String username) throws SQLException {
        return userRepository.getByUsername(username);
    }

    public boolean usernameExists(String username) throws SQLException {
        return userRepository.usernameExists(username);
    }

    public boolean emailExists(String email) throws SQLException {
        return userRepository.emailExists(email);
    }
}
