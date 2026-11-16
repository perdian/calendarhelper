package de.perdian.apps.calendarbuddy.modules.google.user;

import de.perdian.apps.calendarbuddy.CalendarBuddyLifecycle;
import de.perdian.apps.calendarbuddy.modules.google.api.GoogleApiModel;
import javafx.beans.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleUserModel implements CalendarBuddyLifecycle {

    private final ObjectProperty<GoogleUser> currentUser;
    private final BooleanProperty chooseNewUserAvailable;
    private final BooleanProperty busy;
    private final GoogleApiModel googleApiModel;
    private final GoogleUserService googleUserService;

    @Autowired
    GoogleUserModel(GoogleApiModel googleApiModel, GoogleUserService googleUserService) {
        this.currentUser = new SimpleObjectProperty<>();
        this.busy = new SimpleBooleanProperty(false);
        this.chooseNewUserAvailable = new SimpleBooleanProperty(false);
        this.chooseNewUserAvailable.bind(googleApiModel.availableProperty().and(this.busy.not()));
        this.googleApiModel = googleApiModel;
        this.googleUserService = googleUserService;
    }

    @Override
    public synchronized void onAfterApplicationOpened() {
        if (this.getGoogleApiModel().isAvailable()) {
            this.setBusy(true);
            Thread.ofVirtual().start(() -> {
                try {
                    this.setCurrentUser(this.getGoogleUserService().lookupUser(this.getGoogleApiModel().getClientId(), this.getGoogleApiModel().getClientSecret()));
                } finally {
                    this.setBusy(false);
                }
            });
        }
    }

    public synchronized void chooseNewUser() {
        this.setBusy(true);
        Thread.ofVirtual().start(() -> {
            try {
                this.setCurrentUser(this.getGoogleUserService().loginNewUser(this.getGoogleApiModel().getClientId(), this.getGoogleApiModel().getClientSecret()));
            } finally {
                this.setBusy(false);
            }
        });
    }

    public GoogleUser getCurrentUser() {
        return this.currentUser.get();
    }
    public ObjectProperty<GoogleUser> currentUserProperty() {
        return this.currentUser;
    }
    public void setCurrentUser(GoogleUser currentUser) {
        this.currentUser.set(currentUser);
    }

    public boolean isChooseNewUserAvailable() {
        return chooseNewUserAvailable.get();
    }
    public ReadOnlyBooleanProperty chooseNewUserAvailableProperty() {
        return chooseNewUserAvailable;
    }
    public void setChooseNewUserAvailable(boolean chooseNewUserAvailable) {
        this.chooseNewUserAvailable.set(chooseNewUserAvailable);
    }

    private boolean isBusy() {
        return this.busy.get();
    }
    private BooleanProperty busyProperty() {
        return this.busy;
    }
    private void setBusy(boolean busy) {
        this.busy.set(busy);
    }
    private GoogleApiModel getGoogleApiModel() {
        return this.googleApiModel;
    }
    private GoogleUserService getGoogleUserService() {
        return this.googleUserService;
    }

}
