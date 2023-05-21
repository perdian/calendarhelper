package de.perdian.apps.calendarhelper;

import de.perdian.apps.calendarhelper.modules.items.model.Item;
import de.perdian.apps.calendarhelper.support.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.support.google.calendar.GoogleCalendarService;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUser;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUserService;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class CalendarHelperContext {

    private static final Logger log = LoggerFactory.getLogger(CalendarHelperContext.class);

    private final ObjectProperty<GoogleUser> activeGoogleUser = new SimpleObjectProperty<>();
    private final ObservableList<GoogleCalendar> googleCalendars = FXCollections.observableArrayList();
    private final ObjectProperty<GoogleCalendar> activeGoogleCalendar = new SimpleObjectProperty<>();
    private final ObservableList<Item> editorItems = FXCollections.observableArrayList();
    private final DoubleProperty executionProgress = new SimpleDoubleProperty(0);
    private final BooleanProperty executionActive = new SimpleBooleanProperty(false);

    public CalendarHelperContext(ApplicationContext applicationContext) {
        this.activeGoogleUserProperty().addListener((o, oldValue, newValue) -> {
            log.info("Updating globally used Google user: {}", newValue);
            Platform.runLater(() -> this.googleCalendars().clear());
            if (newValue == null) {
                GoogleUserService googleUserProvider = applicationContext.getBean(GoogleUserService.class);
                googleUserProvider.logoutUser();
            } else {
                Thread.ofVirtual().start(() -> {
                    GoogleCalendarService calendarClient = applicationContext.getBean(GoogleCalendarService.class);
                    List<GoogleCalendar> calendarList = calendarClient.loadCalendars(newValue);
                    Platform.runLater(() -> this.googleCalendars().setAll(calendarList));
                });
            }
        });
        this.googleCalendars().addListener((ListChangeListener.Change<? extends GoogleCalendar> change) -> {
            GoogleCalendar activeCalendar = this.activeGoogleCalendarProperty().getValue();
            if (activeCalendar != null && !change.getList().contains(activeCalendar)) {
                this.activeGoogleCalendarProperty().setValue(null);
            } else if (activeCalendar == null && !change.getList().isEmpty()) {
                this.activeGoogleCalendarProperty().setValue(change.getList().stream().filter(entry -> entry.isPrimary()).findFirst().orElse(null));
            }
        });
    }

    public ObjectProperty<GoogleUser> activeGoogleUserProperty() {
        return activeGoogleUser;
    }

    public ObservableList<GoogleCalendar> googleCalendars() {
        return googleCalendars;
    }

    public ObjectProperty<GoogleCalendar> activeGoogleCalendarProperty() {
        return activeGoogleCalendar;
    }

    public ObservableList<Item> editorItems() {
        return editorItems;
    }

    public DoubleProperty executionProgressProperty() {
        return executionProgress;
    }

    public BooleanProperty executionActiveProperty() {
        return executionActive;
    }

}
