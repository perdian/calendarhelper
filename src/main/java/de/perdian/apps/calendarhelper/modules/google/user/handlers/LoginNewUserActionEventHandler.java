package de.perdian.apps.calendarhelper.modules.google.user.handlers;

import de.perdian.apps.calendarhelper.modules.google.apicredentials.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUser;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserService;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoginNewUserActionEventHandler implements EventHandler<ActionEvent> {

    private ObjectProperty<GoogleUser> activeUser = null;
    private ReadOnlyObjectProperty<GoogleApiCredentials> apiCredentials = null;
    private GoogleUserService userService = null;

    public LoginNewUserActionEventHandler(ObjectProperty<GoogleUser> activeUser, ReadOnlyObjectProperty<GoogleApiCredentials> apiCredentials, GoogleUserService userService) {
        this.setActiveUser(activeUser);
        this.setApiCredentials(apiCredentials);
        this.setUserService(userService);
    }

    @Override
    public void handle(ActionEvent event) {
        Thread.ofVirtual().start(() -> {
            GoogleUser newUser = this.getUserService().loginNewUser(this.getApiCredentials().getValue());
            Platform.runLater(() -> this.getActiveUser().setValue(newUser));
        });

    }

    private ObjectProperty<GoogleUser> getActiveUser() {
        return this.activeUser;
    }
    private void setActiveUser(ObjectProperty<GoogleUser> activeUser) {
        this.activeUser = activeUser;
    }

    private ReadOnlyObjectProperty<GoogleApiCredentials> getApiCredentials() {
        return this.apiCredentials;
    }
    private void setApiCredentials(ReadOnlyObjectProperty<GoogleApiCredentials> apiCredentials) {
        this.apiCredentials = apiCredentials;
    }

    private GoogleUserService getUserService() {
        return this.userService;
    }
    private void setUserService(GoogleUserService userService) {
        this.userService = userService;
    }

}
