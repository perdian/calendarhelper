package de.perdian.apps.calendarhelper.services.google.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class GoogleRefreshTokenStore {

    private static final Logger log = LoggerFactory.getLogger(GoogleRefreshTokenStore.class);

    void updateRefreshToken(GoogleRefreshToken token) {
        log.trace("Storing new Google refresh token");
    }

    GoogleRefreshToken loadRefreshToken() {
        return null;
    }

}
