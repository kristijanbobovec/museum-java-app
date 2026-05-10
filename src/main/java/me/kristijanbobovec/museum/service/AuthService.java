package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.ValidationException;
import me.kristijanbobovec.museum.exception.WrongCredentialsException;
import me.kristijanbobovec.museum.model.RoleType;
import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.util.Messages;
import me.kristijanbobovec.museum.util.Patterns;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void login(String username, String password) throws SQLException {
        User user = userService.getByUsername(username)
                .orElseThrow(() -> new WrongCredentialsException());

        if (!isValidPass(password, user.getPassword())) throw new WrongCredentialsException();

        System.out.println(user.getUsername() + " is succefully logined");
    }

    public void register(String username, String email, String password, String firstName, String lastName) throws SQLException, ValidationException {
        List<String> errors = new ArrayList<>();
        if (username.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                firstName.isEmpty() ||
                lastName.isEmpty()) errors.add(Messages.Auth.EMPTY_FIELDS);

        if (!email.isEmpty() && !email.matches(Patterns.EMAIL_PATTERN)) errors.add(Messages.Auth.EMAIL_FORMAT);
        if (!password.isEmpty() && password.length() < 8) errors.add(Messages.Auth.PASSWORD_NOT_VALID);
        if (!username.isEmpty() && userService.usernameExists(username)) errors.add(Messages.Auth.USERNAME_TAKEN);
        if (!email.isEmpty() && userService.emailExists(email)) errors.add(Messages.Auth.EMAIL_TAKEN);
        if (!errors.isEmpty()) throw new ValidationException(errors);

        User userToInsert = new User(username, email, firstName, lastName, hashPassworrd(password), RoleType.USER.getValue());
        //userService.insert(userToInsert);
    }

    private boolean isValidPass(String enteredPassword, String userPassword) {
        return BCrypt.checkpw(enteredPassword, userPassword);
    }

    private String hashPassworrd(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
