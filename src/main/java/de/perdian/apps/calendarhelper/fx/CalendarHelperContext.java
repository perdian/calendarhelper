package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarClient;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CalendarHelperContext {

    private static final Logger log = LoggerFactory.getLogger(CalendarHelperContext.class);

    private final ObjectProperty<GoogleUser> googleUser = new SimpleObjectProperty<>();
    private final ObservableList<GoogleCalendar> googleCalendars = FXCollections.observableArrayList();
    private final ObjectProperty<GoogleCalendar> activeGoogleCalendar = new SimpleObjectProperty<>();

    public CalendarHelperContext(ApplicationContext applicationContext) {
        this.googleUserProperty().addListener((o, oldValue, newValue) -> {
            log.info("Updating globally used Google user: {}", newValue);
            this.googleCalendars().clear();
            if (newValue == null) {
                GoogleUserService googleUserProvider = applicationContext.getBean(GoogleUserService.class);
                googleUserProvider.logoutUser();
            } else {
                Thread.ofVirtual().start(() -> {
                    GoogleCalendarClient calendarClient = applicationContext.getBean(GoogleCalendarClient.class);
                    this.googleCalendars().setAll(calendarClient.loadCalendars(newValue));
                });
            }
        });
        this.googleCalendars().addListener((ListChangeListener.Change<? extends GoogleCalendar> change) -> {
            GoogleCalendar activeCalendar = this.activeGoogleCalendarProperty().getValue();
            if (activeCalendar != null && !change.getList().contains(activeCalendar)) {
                this.activeGoogleCalendarProperty().setValue(null);
            }
        });
    }

    public ObjectProperty<GoogleUser> googleUserProperty() {
        return googleUser;
    }

    public ObservableList<GoogleCalendar> googleCalendars() {
        return googleCalendars;
    }

    public ObjectProperty<GoogleCalendar> activeGoogleCalendarProperty() {
        return activeGoogleCalendar;
    }

}
