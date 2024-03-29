package de.perdian.apps.calendarhelper.modules.google.user;

import de.perdian.apps.calendarhelper.CalendarHelperSelection;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.modules.google.user.handlers.LoginNewUserActionEventHandler;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GoogleUserPane extends GridPane {

    private static final Logger log = LoggerFactory.getLogger(GoogleUserPane.class);

    public GoogleUserPane(CalendarHelperSelection selection, GoogleUserService userService) {

        Label userNameTitleLabel = new Label("User");
        Label userNameLabel = new Label(selection.activeUserProperty().getValue() == null ? "<< No user logged in yet >>" : selection.activeUserProperty().getValue().toString());
        userNameLabel.setMaxWidth(Double.MAX_VALUE);
        selection.activeUserProperty().addListener((_, _, newUser) -> Platform.runLater(() -> userNameLabel.setText(newUser == null ? "<< No user logged in yet >>" : newUser.toString())));
        GridPane.setHgrow(userNameLabel, Priority.ALWAYS);

        Label calendarTitleLabel = new Label("Calendar");
        ComboBox<GoogleCalendar> calendarBox = new ComboBox<>(selection.getAvailableCalendars());
        calendarBox.setMaxWidth(Double.MAX_VALUE);
        calendarBox.disableProperty().bind(Bindings.isEmpty(selection.getAvailableCalendars()));
        selection.activeCalendarProperty().addListener((_, _, newCalendar) -> {
            if (newCalendar == null) {
                Platform.runLater(() -> calendarBox.getSelectionModel().clearSelection());
            } else if (!Objects.equals(newCalendar, calendarBox.getSelectionModel().getSelectedItem())) {
                Platform.runLater(() -> calendarBox.getSelectionModel().select(newCalendar));
            }
        });
        calendarBox.getSelectionModel().selectedItemProperty().addListener((_, _, selectedCalendar) -> {
            if (!Objects.equals(selectedCalendar, selection.activeCalendarProperty().getValue())) {
                selection.activeCalendarProperty().setValue(selectedCalendar);
            }
        });
        GridPane.setHgrow(calendarBox, Priority.ALWAYS);

        Button loginNewUserButton = new Button("", new FontIcon(MaterialDesignA.ACCOUNT));
        loginNewUserButton.setTooltip(new Tooltip("Login new user"));
        loginNewUserButton.setOnAction(new LoginNewUserActionEventHandler(selection.activeUserProperty(), selection.apiCredentialsProperty(), userService));
        loginNewUserButton.disableProperty().bind(selection.apiCredentialsProperty().isNull());
        Button logoutUserButton = new Button("", new FontIcon(MaterialDesignL.LOGOUT));
        logoutUserButton.setTooltip(new Tooltip("Logout user"));
        logoutUserButton.setOnAction(event -> selection.activeUserProperty().setValue(null));
        logoutUserButton.disableProperty().bind(selection.activeUserProperty().isNull());

        HBox buttonBar = new HBox(loginNewUserButton, logoutUserButton);
        buttonBar.setSpacing(5);

        this.add(userNameTitleLabel, 0, 0, 1, 1);
        this.add(userNameLabel, 1, 0, 1, 1);
        this.add(buttonBar, 2, 0, 1, 1);
        this.add(calendarTitleLabel, 0, 1, 1, 1);
        this.add(calendarBox, 1, 1, 2, 1);
        this.setHgap(10);
        this.setVgap(5);

    }

}
