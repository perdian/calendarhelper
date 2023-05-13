package de.perdian.apps.calendarhelper.services.google.application;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleApplicationCredentials {

    private String clientId = System.getenv("GOOGLE_APP_CLIENT_ID");
    private String clientSecret = System.getenv("GOOGLE_APP_CLIENT_SECRET");
    private List<String> scopes = List.of(
            "https://mail.google.com/",
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/calendar"
    );

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

    public List<String> getScopes() {
        return scopes;
    }
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

}
