package de.perdian.apps.calendarhelper.services.google.users;

import com.google.auth.oauth2.UserCredentials;

import java.io.Serializable;

public class GoogleUser implements Serializable {

    static final long serialVersionUID = 1L;

    private UserCredentials credentials = null;
    private String emailAddress = null;

    GoogleUser(UserCredentials userCredentials) {
        this.setCredentials(userCredentials);
    }

    public String toString() {
        return this.getEmailAddress();
    }

    public UserCredentials getCredentials() {
        return credentials;
    }
    private void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
