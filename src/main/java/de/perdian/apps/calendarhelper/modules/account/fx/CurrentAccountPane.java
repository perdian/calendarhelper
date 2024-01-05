package de.perdian.apps.calendarhelper.modules.account.fx;

import de.perdian.apps.calendarhelper.CalendarHelperContext;
import de.perdian.apps.calendarhelper.support.google.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.support.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUser;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUserService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CurrentAccountPane extends GridPane {

    private static final Logger log = LoggerFactory.getLogger(CurrentAccountPane.class);

    public CurrentAccountPane(CalendarHelperContext calendarHelperContext, GoogleApiCredentials googleApiCredentials, GoogleUserService googleUserService) {

        Label clientIdLabel = new Label("Client ID");
        TextField clientIdField = new TextField();
        clientIdField.textProperty().bindBidirectional(googleApiCredentials.clientIdProperty());
        GridPane.setHgrow(clientIdField, Priority.ALWAYS);

        Label clientSecretLabel = new Label("Client Secret");
        PasswordField clientSecretField = new PasswordField();
        clientSecretField.textProperty().bindBidirectional(googleApiCredentials.clientSecretProperty());
        GridPane.setHgrow(clientSecretField, Priority.ALWAYS);

        Label userNameTitleLabel = new Label("User");
        Label userNameLabel = new Label("<< No user logged in yet >>");
        userNameLabel.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(userNameLabel, Priority.ALWAYS);
        calendarHelperContext.activeGoogleUserProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue == null) {
                userNameLabel.setText("<< No user logged in yet >>");
            } else {
                userNameLabel.setText(newValue.getName() + " (" + newValue.getEmailAddress() + ")");
            }
        }));
        GridPane.setHgrow(userNameLabel, Priority.ALWAYS);

        Label calendarTitleLabel = new Label("Calendar");
        ComboBox<GoogleCalendar> calendarBox = new ComboBox<>(calendarHelperContext.googleCalendars());
        calendarBox.setMaxWidth(Double.MAX_VALUE);
        calendarBox.disableProperty().bind(Bindings.isEmpty(calendarHelperContext.googleCalendars()));
        calendarHelperContext.activeGoogleCalendarProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == null) {
                Platform.runLater(() -> calendarBox.getSelectionModel().clearSelection());
            } else if (!Objects.equals(newValue, calendarBox.getSelectionModel().getSelectedItem())) {
                Platform.runLater(() -> calendarBox.getSelectionModel().select(newValue));
            }
        });
        calendarBox.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (!Objects.equals(newValue, calendarHelperContext.activeGoogleCalendarProperty().getValue())) {
                calendarHelperContext.activeGoogleCalendarProperty().setValue(newValue);
            }
        });
        GridPane.setHgrow(calendarBox, Priority.ALWAYS);

        Button selectNewUserButton = new Button("", new FontIcon(MaterialDesignA.ACCOUNT));
        selectNewUserButton.setTooltip(new Tooltip("Select new user"));
        selectNewUserButton.setOnAction(event -> Thread.ofVirtual().start(() -> {
            try {
                GoogleUser newUser = googleUserService.forceLoginUser();
                if (newUser != null) {
                    calendarHelperContext.activeGoogleUserProperty().setValue(newUser);
                }
            } catch (Exception e) {
                log.warn("Cannot login new Google user", e);
            }
        }));
        selectNewUserButton.disableProperty().bind(googleApiCredentials.validProperty().not());
        Button logoutUserButton = new Button("", new FontIcon(MaterialDesignL.LOGOUT));
        logoutUserButton.setTooltip(new Tooltip("Logout user"));
        logoutUserButton.setOnAction(event -> calendarHelperContext.activeGoogleUserProperty().setValue(null));
        logoutUserButton.disableProperty().bind(calendarHelperContext.activeGoogleUserProperty().isNull());

        HBox buttonBar = new HBox(selectNewUserButton, logoutUserButton);
        buttonBar.setSpacing(5);

        this.add(clientIdLabel, 0, 0, 1, 1);
        this.add(clientIdField, 1, 0, 2, 1);
        this.add(clientSecretLabel, 0, 1, 1, 1);
        this.add(clientSecretField, 1, 1, 2, 1);
        this.add(userNameTitleLabel, 0, 2, 1, 1);
        this.add(userNameLabel, 1, 2, 1, 1);
        this.add(buttonBar, 2, 2, 1, 1);
        this.add(calendarTitleLabel, 0, 3, 1, 1);
        this.add(calendarBox, 1, 3, 2, 1);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setHgap(10);
        this.setVgap(5);

    }

}
