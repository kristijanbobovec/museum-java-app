package me.kristijanbobovec.museum.event;

import me.kristijanbobovec.museum.model.User;

public record UserLoggedInEvent(User user){}
