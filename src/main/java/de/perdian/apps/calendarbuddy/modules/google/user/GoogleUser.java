package de.perdian.apps.calendarbuddy.modules.google.user;

import com.google.auth.oauth2.UserCredentials;

public class GoogleUser {

    private UserCredentials credentials = null;
    private String name = null;
    private String emailAddress = null;

    public GoogleUser(UserCredentials userCredentials) {
        this.setCredentials(userCredentials);
    }

    @Override
    public String toString() {
        return this.getName() + " <" + this.getEmailAddress() + ">";
    }

    public UserCredentials getCredentials() {
        return this.credentials;
    }
    private void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
