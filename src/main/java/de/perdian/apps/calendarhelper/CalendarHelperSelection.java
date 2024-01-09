package de.perdian.apps.calendarhelper;

import de.perdian.apps.calendarhelper.modules.google.apicredentials.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendarService;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUser;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserService;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.support.storage.StorageService;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class CalendarHelperSelection {

    private ApplicationContext applicationContext = null;
    private final ObjectProperty<GoogleApiCredentials> apiCredentials = new SimpleObjectProperty<>();
    private final ObjectProperty<GoogleUser> activeUser = new SimpleObjectProperty<>();
    private final ObjectProperty<GoogleCalendar> activeCalendar = new SimpleObjectProperty<>();
    private final ObservableList<GoogleCalendar> availableCalendars = FXCollections.observableArrayList();
    private final ObservableList<Item> activeItems = FXCollections.observableArrayList();
    private final BooleanProperty busy = new SimpleBooleanProperty(false);
    private final ItemDefaults itemDefaults = new ItemDefaults();

    public CalendarHelperSelection(ApplicationContext applicationContext) {
        StorageService storageService = applicationContext.getBean(StorageService.class);
        this.apiCredentialsProperty().bindBidirectional(storageService.getPersistentObjectProperty("CalendarHelperSelection.apiCredentials"));
        this.apiCredentialsProperty().addListener((_, oldCredentials, newCredentials) -> this.onCredentialsChanged(oldCredentials, newCredentials));
        this.getItemDefaults().attendeesProperty().bindBidirectional(storageService.getPersistentStringProperty("CalendarHelperSelection.itemDefaults.attendees"));
        this.activeUserProperty().addListener((_, oldUser, newUser) -> this.onActiveUserChanged(oldUser, newUser));
        this.getAvailableCalendars().addListener((ListChangeListener.Change<? extends GoogleCalendar> change) -> this.onAvailableCalendarsChanged(change.getList()));
        this.setApplicationContext(applicationContext);
    }

    private void onCredentialsChanged(GoogleApiCredentials oldCredentials, GoogleApiCredentials newCredentials) {
        this.activeUserProperty().setValue(null);
    }

    private void onActiveUserChanged(GoogleUser oldUser, GoogleUser newUser) {
        this.getAvailableCalendars().clear();
        if (oldUser != null) {
            this.getApplicationContext().getBean(GoogleUserService.class).logoutUser(oldUser);
        }
        if (newUser != null) {
            List<GoogleCalendar> availableCalendars = this.getApplicationContext().getBean(GoogleCalendarService.class).loadCalendars(newUser);
            Platform.runLater(() -> this.getAvailableCalendars().setAll(availableCalendars));
        }
    }

    private void onAvailableCalendarsChanged(List<? extends GoogleCalendar> availableCalendars) {
        if (availableCalendars == null || availableCalendars.isEmpty()) {
            this.activeCalendarProperty().setValue(null);
        } else if (!availableCalendars.contains(this.activeCalendarProperty().getValue())) {
            this.activeCalendarProperty().setValue(availableCalendars.getFirst());
        }
    }

    private ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ObjectProperty<GoogleApiCredentials> apiCredentialsProperty() {
        return this.apiCredentials;
    }

    public ObjectProperty<GoogleUser> activeUserProperty() {
        return this.activeUser;
    }

    public ObjectProperty<GoogleCalendar> activeCalendarProperty() {
        return this.activeCalendar;
    }

    public ObservableList<GoogleCalendar> getAvailableCalendars() {
        return this.availableCalendars;
    }

    public ObservableList<Item> getActiveItems() {
        return this.activeItems;
    }

    public BooleanProperty busyProperty() {
        return this.busy;
    }

    public ItemDefaults getItemDefaults() {
        return this.itemDefaults;
    }

}
