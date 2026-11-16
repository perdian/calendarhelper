package de.perdian.apps.calendarbuddy.modules.google;

import de.perdian.apps.calendarbuddy.modules.google.api.GoogleApiModel;
import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarModel;
import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUserModel;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class GooglePane extends GridPane {

    public GooglePane(GoogleUserModel googleUserModel, GoogleCalendarModel googleCalendarModel, GoogleApiModel googleApiModel) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label userTitleLabel = componentFactory.createTitleLabel("Google User");
        Label userLabel = componentFactory.createLabel("...");
        googleUserModel.currentUserProperty().addListener((_, _, newValue) -> {
            Platform.runLater(() -> {
                userLabel.setText(newValue == null ? "..." : newValue.toString());
            });
        });
        userLabel.setAlignment(Pos.CENTER_LEFT);
        userLabel.setPrefWidth(225);
        userLabel.setMaxWidth(225);
        userLabel.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(userLabel, Priority.ALWAYS);
        Button changeUserButton = componentFactory.createButton("", new FontIcon(MaterialDesignA.ACCOUNT_PLUS));
        changeUserButton.disableProperty().bind(googleUserModel.chooseNewUserAvailableProperty().not());
        changeUserButton.setOnAction(_ -> googleUserModel.chooseNewUser());
        changeUserButton.setTooltip(new Tooltip("Choose new User"));
        Button logoutUserButton = componentFactory.createButton("", new FontIcon(MaterialDesignA.ACCOUNT_OFF));
        logoutUserButton.setOnAction(_ -> googleUserModel.setCurrentUser(null));
        logoutUserButton.setTooltip(new Tooltip("Logout User"));
        logoutUserButton.disableProperty().bind(googleUserModel.currentUserProperty().isNull());
        GridPane userBox = new GridPane(5, 0);
        userBox.add(userLabel, 0, 0);
        userBox.add(changeUserButton, 1, 0);
        userBox.add(logoutUserButton, 2, 0);
        GridPane.setHgrow(userBox, Priority.ALWAYS);

        Label calendarTitleLabel = componentFactory.createTitleLabel("Google Calendar");
        ComboBox<GoogleCalendar> calendarComboBox = componentFactory.createComboBox(googleCalendarModel.selectedCalendarProperty(), googleCalendarModel.getAvailableCalendars());
        calendarComboBox.disableProperty().bind(Bindings.isEmpty(googleCalendarModel.getAvailableCalendars()));
        calendarComboBox.setPrefWidth(225);
        calendarComboBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(calendarComboBox, Priority.ALWAYS);

        Label apiClientIdTitleLabel = componentFactory.createTitleLabel("Google API Client ID");
        GridPane.setMargin(apiClientIdTitleLabel, new Insets(8, 0, 0, 0));
        TextField apiClientIdTextField = componentFactory.createTextField(googleApiModel.clientIdProperty());
        GridPane.setHgrow(apiClientIdTextField, Priority.ALWAYS);
        Label apiClientSecretTitleLabel = componentFactory.createTitleLabel("Google API Client Secret");
        GridPane.setMargin(apiClientSecretTitleLabel, new Insets(8, 0, 0, 0));
        TextField apiClientSecretTextField = componentFactory.createPasswordField(googleApiModel.clientSecretProperty());
        GridPane.setHgrow(apiClientSecretTextField, Priority.ALWAYS);

        this.add(userTitleLabel, 0, 0, 1, 1);
        this.add(userBox, 0, 1, 1, 1);
        this.add(calendarTitleLabel, 1, 0, 1, 1);
        this.add(calendarComboBox, 1, 1, 1, 1);
        this.add(apiClientIdTitleLabel, 0, 2, 1, 1);
        this.add(apiClientIdTextField, 0, 3, 1, 1);
        this.add(apiClientSecretTitleLabel, 1, 2, 1, 1);
        this.add(apiClientSecretTextField, 1, 3, 1, 1);
        this.setHgap(10);
        this.setVgap(2);

    }

}
