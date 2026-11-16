package de.perdian.apps.calendarbuddy.modules.google.user;

import de.perdian.apps.calendarbuddy.modules.google.GoogleException;

public interface GoogleUserService {

    GoogleUser lookupUser(String apiClientId, String apiClientSecret) throws GoogleException;
    GoogleUser ensureUser(String apiClientId, String apiClientSecret) throws GoogleException;

    GoogleUser loginNewUser(String apiClientId, String apiClientSecret) throws GoogleException;

    void logoutUser(GoogleUser user);

}
