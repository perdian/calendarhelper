package de.perdian.apps.calendarhelper.services.google.users;

public class GoogleUserException extends RuntimeException {

    public GoogleUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleUserException(String message) {
        super(message);
    }

}
