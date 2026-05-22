package me.kristijanbobovec.museum.model.dto.user;

import me.kristijanbobovec.museum.model.User;
import me.kristijanbobovec.museum.model.dto.Dto;

public record UserToInsertDTO(String username, String email, String firstName, String lastName, String password, int roleId) implements Dto {
    public User mapToUser() {
        return new User(username, email, firstName, lastName, password, roleId);
    }
}
