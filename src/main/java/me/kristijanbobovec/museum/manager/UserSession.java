package me.kristijanbobovec.museum.manager;

import me.kristijanbobovec.museum.event.UserLoggedInEvent;
import me.kristijanbobovec.museum.event.UserLoggedOutEvent;
import me.kristijanbobovec.museum.model.dto.user.UserSessionDTO;

public enum UserSession {
    INSTANCE;

    private UserSessionDTO currentUser;

    public void setSession(UserLoggedInEvent e) {
        currentUser = UserSessionDTO.mapFromUser(e.user());
    }

    public void clearSession(UserLoggedOutEvent e) {
        currentUser = null;
    }

    public UserSessionDTO getSession() {
        return currentUser;
    }
}
