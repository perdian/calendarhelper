package de.perdian.apps.calendarhelper.fx.modules.user;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserService;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentUserPane extends GridPane {

    private static final Logger log = LoggerFactory.getLogger(CurrentUserPane.class);

    public CurrentUserPane(ObjectProperty<GoogleUser> currentUserProperty, GoogleUserService googleUserService) {

        Label userNameLabel = new Label("<< No user logged in yet >>");
        userNameLabel.setAlignment(Pos.CENTER);
        userNameLabel.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(userNameLabel, Priority.ALWAYS);
        currentUserProperty.addListener((o, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue == null) {
                    userNameLabel.setText("<< No user logged in yet >>");
                } else {
                    userNameLabel.setText(newValue.getEmailAddress());
                }
            });
        });

        Button selectNewUserButton = new Button("Select new user", new FontIcon(MaterialDesignA.ACCOUNT));
        selectNewUserButton.setOnAction(event -> Thread.ofVirtual().start(() -> {
            try {
                GoogleUser newUser = googleUserService.forceLoginUser();
                if (newUser != null) {
                    currentUserProperty.setValue(newUser);
                }
            } catch (Exception e) {
                log.warn("Cannot login new Google user", e);
            }
        }));
        ButtonBar.setButtonData(selectNewUserButton, ButtonBar.ButtonData.LEFT);
        Button logoutUserButton = new Button("Logout user", new FontIcon(MaterialDesignL.LOGOUT));
        logoutUserButton.setOnAction(event -> currentUserProperty.setValue(null));
        logoutUserButton.disableProperty().bind(currentUserProperty.isNull());
        ButtonBar.setButtonData(logoutUserButton, ButtonBar.ButtonData.RIGHT);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(selectNewUserButton, logoutUserButton);
        GridPane.setHgrow(buttonBar, Priority.ALWAYS);

        this.add(userNameLabel, 0, 0, 1, 1);
        this.add(buttonBar, 0, 1, 1, 1);
        this.setVgap(10);

    }

}
