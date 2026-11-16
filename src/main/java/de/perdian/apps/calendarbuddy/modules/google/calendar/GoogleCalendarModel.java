package de.perdian.apps.calendarbuddy.modules.google.calendar;

import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUser;
import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUserModel;
import de.perdian.apps.calendarbuddy.support.storage.StorageService;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleCalendarModel {

    private final ObservableList<GoogleCalendar> availableCalendars;
    private final ObjectProperty<GoogleCalendar> selectedCalendar;
    private final StringProperty selectedCalendarId;
    private final GoogleCalendarService googleCalendarService;

    @Autowired
    GoogleCalendarModel(GoogleUserModel googleUserModel, GoogleCalendarService googleCalendarService, StorageService storageService) {
        this.availableCalendars = FXCollections.observableArrayList();
        this.selectedCalendar = new SimpleObjectProperty<>();
        this.selectedCalendarId = storageService.getPersistentStringProperty("GoogleCalendarModel.selectedCalendarId");
        this.googleCalendarService = googleCalendarService;
        googleUserModel.currentUserProperty().addListener((_, _, newValue) -> this.onNewUserSelected(newValue));
    }

    private void onNewUserSelected(GoogleUser newUser) {
        this.getAvailableCalendars().clear();
        this.setSelectedCalendar(null);
        if (newUser != null) {
            Thread.ofVirtual().start(() -> {
                List<GoogleCalendar> loadedCalendars = this.getGoogleCalendarService().loadCalendars(newUser);
                GoogleCalendar selectedCalendar = loadedCalendars.stream()
                    .filter(calendar -> calendar.getId().equals(this.getSelectedCalendarId()))
                    .findFirst()
                    .orElse(loadedCalendars.isEmpty() ? null : loadedCalendars.getFirst());
                Platform.runLater(() -> {
                    this.getAvailableCalendars().setAll(loadedCalendars);
                    this.setSelectedCalendar(selectedCalendar);
                });
            });
        }
    }

    public ObservableList<GoogleCalendar> getAvailableCalendars() {
        return this.availableCalendars;
    }

    public GoogleCalendar getSelectedCalendar() {
        return this.selectedCalendar.get();
    }
    public ObjectProperty<GoogleCalendar> selectedCalendarProperty() {
        return this.selectedCalendar;
    }
    public void setSelectedCalendar(GoogleCalendar selectedCalendar) {
        this.selectedCalendar.set(selectedCalendar);
    }

    private String getSelectedCalendarId() {
        return selectedCalendarId.get();
    }
    private StringProperty selectedCalendarIdProperty() {
        return selectedCalendarId;
    }
    private void setSelectedCalendarId(String selectedCalendarId) {
        this.selectedCalendarId.set(selectedCalendarId);
    }

    private GoogleCalendarService getGoogleCalendarService() {
        return this.googleCalendarService;
    }

}
