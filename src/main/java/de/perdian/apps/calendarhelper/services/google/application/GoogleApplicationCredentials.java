package de.perdian.apps.calendarhelper.services.google.application;

import org.springframework.stereotype.Component;

@Component
public class GoogleApplicationCredentials {

    private String clientId = System.getenv("GOOGLE_APP_CLIENT_ID");
    private String clientSecret = System.getenv("GOOGLE_APP_CLIENT_SECRET");

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
