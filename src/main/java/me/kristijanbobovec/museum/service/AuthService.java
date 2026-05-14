package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.event.EventBus;
import me.kristijanbobovec.museum.event.UserLoggedInEvent;
import me.kristijanbobovec.museum.exception.ValidationException;
import me.kristijanbobovec.museum.exception.WrongCredentialsException;
import me.kristijanbobovec.museum.model.RoleType;
import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.model.dto.user.UserToInsertDTO;
import me.kristijanbobovec.museum.util.Messages;
import me.kristijanbobovec.museum.util.Patterns;

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
                .orElseThrow(WrongCredentialsException::new);

        if (!userService.isValidPass(password, user.getPassword())) throw new WrongCredentialsException();

        // User logged in notify all subscribers!
        EventBus.INSTANCE.publish(new UserLoggedInEvent(user));
    }

    public void register(String username, String email, String firstName, String lastName, String password) throws SQLException, ValidationException {
        List<String> errors = new ArrayList<>();
        if (username.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                firstName.isEmpty() ||
                lastName.isEmpty()) {
            errors.add(Messages.Auth.EMPTY_FIELDS);
            throw new ValidationException(errors);
        };

        if (!email.matches(Patterns.EMAIL_PATTERN)) errors.add(Messages.Auth.EMAIL_FORMAT);
        if (password.length() < 8) errors.add(Messages.Auth.PASSWORD_NOT_VALID);
        if (userService.usernameExists(username)) errors.add(Messages.Auth.USERNAME_TAKEN);
        if (userService.emailExists(email)) errors.add(Messages.Auth.EMAIL_TAKEN);

        if (!errors.isEmpty()) throw new ValidationException(errors);

        UserToInsertDTO userToInsert = new UserToInsertDTO(username, email, firstName, lastName, password, RoleType.USER.getValue());
        userService.insert(userToInsert);
    }
}
