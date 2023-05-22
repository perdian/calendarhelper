package de.perdian.apps.calendarhelper.support.google;

import java.util.List;

public class GoogleApiCredentials {

    private String clientId = null;
    private String clientSecret = null;
    private List<String> scopes = List.of(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/calendar"
    );

    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getScopes() {
        return this.scopes;
    }
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

}
