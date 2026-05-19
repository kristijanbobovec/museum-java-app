package me.kristijanbobovec.museum.event;

import me.kristijanbobovec.museum.model.dto.user.UserSessionDTO;

public record UserLoggedOutEvent(UserSessionDTO user) { }
