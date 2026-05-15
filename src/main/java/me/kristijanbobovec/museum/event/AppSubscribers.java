package me.kristijanbobovec.museum.event;

import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AppSubscribers {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(AppSubscribers.class);

    AppSubscribers() {
        subscribeUserLoggedInEvent();
    }

    private void subscribeUserLoggedInEvent() {
        // USER SESSION
        EventBus.INSTANCE.subscribe(UserLoggedInEvent.class, UserSession.INSTANCE::setSession);

        // Login
        EventBus.INSTANCE.subscribe(UserLoggedInEvent.class, (e) -> log.info(Messages.Auth.LOGIN_SUCCEED_LOG.formatted(e.user().getUsername())));
    }
}
