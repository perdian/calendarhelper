package de.perdian.apps.calendarhelper.services.google.users;

public interface GoogleUserProvider {

    /**
     * Gets the currently available user
     *
     * @return the currently available user if a user is existing at this point of time, {@code null}
     * if no user has been made available yet.
     */
    GoogleUser lookupUser() throws GoogleUserException;

    /**
     * Requests a valid {@link GoogleUser}. If no user can be found in a session (or other stored location)
     * then a dialog will be shown to the user where he needs to select a new user. If any existing user can
     * be found an explicit validation call will be made against Google so that the client can be sure the
     * user is still valid and can be used.
     *
     * @return the currently available (and validated) user or {@code null} if the user could not be found,
     * the user cancelled the selection or didn't approve the user grants at Google.
     */
    GoogleUser lookupValidatedUser() throws GoogleUserException;

}
