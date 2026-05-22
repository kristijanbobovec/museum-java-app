package me.kristijanbobovec.museum.model.dto.user;

import me.kristijanbobovec.museum.model.RoleType;
import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.model.dto.Dto;

public record UserSessionDTO(int userId, String username, String firstName, String lastName, RoleType role) implements Dto {
    public boolean isAdmin() {
        return role == RoleType.ADMINISTRATOR;
    }

    public static UserSessionDTO mapFromUser(User user) {
        return new UserSessionDTO(user.getIdUser(), user.getUsername(), user.getFirstName(), user.getLastName(), RoleType.fromValue(user.getRoleId()));
    }
}
