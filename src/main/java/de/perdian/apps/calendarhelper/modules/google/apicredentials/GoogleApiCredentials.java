package de.perdian.apps.calendarhelper.modules.google.apicredentials;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class GoogleApiCredentials {

    private String clientId = null;
    private String clientSecret = null;
    private List<String> scopes = List.of(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/calendar"
    );

    public GoogleApiCredentials(String clientId, String clientSecret) {
        this.setClientId(clientId);
        this.setClientSecret(clientSecret);
    }

    public static void bindBidirectional(ObjectProperty<GoogleApiCredentials> credentials, StringProperty clientId, StringProperty clientSecret) {
        ChangeListener<String> credentialsValueListener = (_, _, _) -> {
            if (StringUtils.isEmpty(clientId.getValue()) || StringUtils.isEmpty(clientSecret.getValue())) {
                credentials.setValue(null);
            } else {
                String newClientId = clientId.getValue();
                String newClientSecret = clientSecret.getValue();
                if (credentials.getValue() == null || !Objects.equals(newClientId, credentials.getValue().getClientId()) || !Objects.equals(newClientSecret, credentials.getValue().getClientSecret())) {
                    credentials.setValue(new GoogleApiCredentials(newClientId, newClientSecret));
                }
            }
        };
        credentialsValueListener.changed(null, null, null);
        credentials.addListener((_, _, newCredentials) -> {
            clientId.setValue(newCredentials == null ? null : newCredentials.getClientId());
            clientSecret.setValue(newCredentials == null ? null : newCredentials.getClientSecret());
        });
    }

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
