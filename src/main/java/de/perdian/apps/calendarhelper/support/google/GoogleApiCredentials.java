package de.perdian.apps.calendarhelper.support.google;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GoogleApiCredentials {

    private final StringProperty clientId = new SimpleStringProperty();
    private final StringProperty clientSecret = new SimpleStringProperty();
    private final BooleanProperty valid = new SimpleBooleanProperty();
    private final ObservableList<String> scopes = FXCollections.observableArrayList(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/calendar"
    );

    GoogleApiCredentials() {
        this.valid.bind(this.clientId.isNotEmpty().and(this.clientSecret.isNotEmpty()));
    }

    public StringProperty clientIdProperty() {
        return this.clientId;
    }

    public StringProperty clientSecretProperty() {
        return this.clientSecret;
    }

    public ReadOnlyBooleanProperty validProperty() {
        return this.valid;
    }

    public ObservableList<String> getScopes() {
        return this.scopes;
    }

}
