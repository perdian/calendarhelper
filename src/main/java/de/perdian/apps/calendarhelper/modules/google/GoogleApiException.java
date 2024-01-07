package de.perdian.apps.calendarhelper.modules.google;

public class GoogleApiException extends RuntimeException {

    public GoogleApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleApiException(String message) {
        super(message);
    }

}
