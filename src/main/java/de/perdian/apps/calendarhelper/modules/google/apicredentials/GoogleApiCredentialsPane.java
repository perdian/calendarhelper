package de.perdian.apps.calendarhelper.modules.google.apicredentials;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.apache.commons.lang3.StringUtils;

public class GoogleApiCredentialsPane extends GridPane {

    public GoogleApiCredentialsPane(ObjectProperty<GoogleApiCredentials> apiCredentials) {

        BooleanProperty fieldsToCredentials = new SimpleBooleanProperty(true);
        StringProperty clientId = new SimpleStringProperty();
        StringProperty clientSecret = new SimpleStringProperty();

        ChangeListener<Object> clientCredentialsChangeListener = (_, _, _) -> {
            if (fieldsToCredentials.getValue()) {
                String clientIdValue = clientId.getValue();
                String clientSecretValue = clientSecret.getValue();
                if (StringUtils.isEmpty(clientIdValue) || StringUtils.isEmpty(clientSecretValue)) {
                    apiCredentials.setValue(null);
                } else {
                    apiCredentials.setValue(new GoogleApiCredentials(clientIdValue, clientSecretValue));
                }
            }
        };
        ChangeListener<GoogleApiCredentials> apiCredentialsChangeListener = (_, _, newClientCredentials) -> {
            fieldsToCredentials.setValue(false);
            try {
                clientId.setValue(newClientCredentials == null ? "" : newClientCredentials.getClientId());
                clientSecret.setValue(newClientCredentials == null ? "" : newClientCredentials.getClientSecret());
            } finally {
                fieldsToCredentials.setValue(true);
            }
        };
        apiCredentialsChangeListener.changed(null, null, apiCredentials.getValue());
        apiCredentials.addListener(apiCredentialsChangeListener);

        Label clientIdLabel = new Label("Client ID");
        TextField clientIdField = new TextField();
        clientIdField.textProperty().bindBidirectional(clientId);
        clientIdField.textProperty().addListener(clientCredentialsChangeListener);
        GridPane.setHgrow(clientIdField, Priority.ALWAYS);

        Label clientSecretLabel = new Label("Client Secret");
        PasswordField clientSecretField = new PasswordField();
        clientSecretField.textProperty().bindBidirectional(clientSecret);
        clientSecretField.textProperty().addListener(clientCredentialsChangeListener);
        GridPane.setHgrow(clientSecretField, Priority.ALWAYS);

        this.setHgap(10);
        this.setVgap(5);
        this.add(clientIdLabel, 0, 0, 1, 1);
        this.add(clientIdField, 1, 0, 1, 1);
        this.add(clientSecretLabel, 0, 1, 1, 1);
        this.add(clientSecretField, 1, 1, 1, 1);

    }

}
