package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.services.google.calendar.Calendar;
import de.perdian.apps.calendarhelper.services.google.calendar.CalendarClient;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CalendarHelperContext {

    private static final Logger log = LoggerFactory.getLogger(CalendarHelperContext.class);

    private final ObjectProperty<GoogleUser> googleUser = new SimpleObjectProperty<>();
    private final ObservableList<Calendar> googleCalendars = FXCollections.observableArrayList();

    public CalendarHelperContext(ApplicationContext applicationContext) {
        this.googleUserProperty().addListener((o, oldValue, newValue) -> {
            log.info("Updating globally used Google user: {}", newValue);
            this.googleCalendars().clear();
            if (newValue != null) {
                Thread.ofVirtual().start(() -> {
                    CalendarClient calendarClient = applicationContext.getBean(CalendarClient.class);
                    this.googleCalendars().setAll(calendarClient.loadCalendars(newValue));
                });
            }
        });
    }

    public ObjectProperty<GoogleUser> googleUserProperty() {
        return googleUser;
    }

    public ObservableList<Calendar> googleCalendars() {
        return googleCalendars;
    }

}
