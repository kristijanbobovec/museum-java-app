package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.model.dto.user.UserToInsertDTO;
import me.kristijanbobovec.museum.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

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


    public void insert(UserToInsertDTO userDto) throws SQLException {
        User user = userDto.mapToUser();

        user.setPassword(hashPassword(user.getPassword()));
        userRepository.insert(user);
    }

    public boolean isValidPass(String enteredPassword, String userPassword) {
        return BCrypt.checkpw(enteredPassword, userPassword);
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
