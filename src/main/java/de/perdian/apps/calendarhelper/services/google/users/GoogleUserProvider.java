package de.perdian.apps.calendarhelper.services.google.users;

public interface GoogleUserProvider {

    /**
     * Requests a valid {@link GoogleUser}. If no user can be found in a session (or other stored location)
     * then a dialog will be shown to the user where he needs to select a new user. If any existing user can
     * be found an explicit validation call will be made against Google so that the client can be sure the
     * user is still valid and can be used.
     *
     * @return the currently available (and validated) user.
     */
    GoogleUser lookupUser() throws GoogleUserException;

    /**
     * Force a login of a new user session, independently of whether or not user credentials can be found
     * from a previous login.
     *
     * @return the currently available (and validated) user.
     */
    GoogleUser forceLoginUser() throws GoogleUserException;

}
