package me.kristijanbobovec.museum.event;

import me.kristijanbobovec.museum.manager.UserSession;

public enum AppSubscribers {
    INSTANCE;

    AppSubscribers() {
        subscribeUserLoggedInEvent();
    }

    private void subscribeUserLoggedInEvent() {
        // USER SESSION
        EventBus.INSTANCE.subscribe(UserLoggedInEvent.class, UserSession.INSTANCE::setSession);
    }
}
