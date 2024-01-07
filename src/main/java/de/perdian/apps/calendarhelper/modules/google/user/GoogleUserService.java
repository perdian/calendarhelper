package de.perdian.apps.calendarhelper.modules.google.user;

import de.perdian.apps.calendarhelper.modules.google.GoogleApiException;
import de.perdian.apps.calendarhelper.modules.google.apicredentials.GoogleApiCredentials;

public interface GoogleUserService {

    GoogleUser lookupUser(GoogleApiCredentials apiCredentials) throws GoogleApiException;

    GoogleUser loginNewUser(GoogleApiCredentials apiCredentials) throws GoogleApiException;

    void logoutUser(GoogleUser user);

}
