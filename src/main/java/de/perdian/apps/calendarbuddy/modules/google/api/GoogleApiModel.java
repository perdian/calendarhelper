package de.perdian.apps.calendarbuddy.modules.google.api;

import de.perdian.apps.calendarbuddy.support.storage.StorageService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class GoogleApiModel {

    private final StringProperty clientId;
    private final StringProperty clientSecret;
    private final BooleanProperty available;

    GoogleApiModel(StorageService storageService) {
        this.clientId = storageService.getPersistentStringProperty("GoogleApiModel.clientId");
        this.clientSecret = storageService.getPersistentStringProperty("GoogleApiModel.clientSecret");
        this.available = new SimpleBooleanProperty();
        this.available.bind(this.clientId.isNotEmpty().and(this.clientSecret.isNotEmpty()));
    }

    public String getClientId() {
        return this.clientId.get();
    }
    public StringProperty clientIdProperty() {
        return this.clientId;
    }
    public void setClientId(String clientId) {
        this.clientId.set(clientId);
    }

    public String getClientSecret() {
        return this.clientSecret.get();
    }
    public StringProperty clientSecretProperty() {
        return this.clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret.set(clientSecret);
    }

    public boolean isAvailable() {
        return available.get();
    }
    public ReadOnlyBooleanProperty availableProperty() {
        return available;
    }

}
