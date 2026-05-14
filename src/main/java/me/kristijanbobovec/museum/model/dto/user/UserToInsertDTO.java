package me.kristijanbobovec.museum.model.dto.user;

import me.kristijanbobovec.museum.model.User;

public record UserToInsertDTO(String username, String email, String firstName, String lastName, String password, int roleId) {
    public User mapToUser() {
        return new User(username, email, firstName, lastName, password, roleId);
    }
}
