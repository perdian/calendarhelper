package de.perdian.apps.calendarhelper.modules.execution.handlers;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.execution.ExecutionProgress;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendar;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendarService;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUser;
import de.perdian.apps.calendarhelper.modules.itemdefaults.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.support.fx.CalendarHelperDialogs;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ExecutionActionEventHandler implements EventHandler<ActionEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExecutionActionEventHandler.class);

    private ReadOnlyObjectProperty<GoogleUser> activeUser = null;
    private ReadOnlyObjectProperty<GoogleCalendar> activeCalendar = null;
    private ObservableList<Item> activeItems = null;
    private ItemDefaults itemDefaults = null;
    private ExecutionProgress progress = null;
    private ApplicationContext applicationContext = null;

    public ExecutionActionEventHandler(ReadOnlyObjectProperty<GoogleUser> activeUser, ReadOnlyObjectProperty<GoogleCalendar> activeCalendar, ObservableList<Item> activeItems, ItemDefaults itemDefaults, ExecutionProgress progress, ApplicationContext applicationContext) {
        this.setActiveUser(activeUser);
        this.setActiveCalendar(activeCalendar);
        this.setActiveItems(activeItems);
        this.setItemDefaults(itemDefaults);
        this.setProgress(progress);
        this.setApplicationContext(applicationContext);
    }

    @Override
    public void handle(ActionEvent event) {
        Platform.runLater(() -> this.getProgress().busyProperty().setValue(Boolean.TRUE));
        Thread.ofVirtual().start(() -> {
            try {
                this.createCalendarEvents();
            } catch (Exception e) {
                log.warn("Cannot create calendar events", e);
                CalendarHelperDialogs.showErrorDialog("Create calendar events", "Cannot create calendar events", e);
            } finally {
                Platform.runLater(() -> this.getProgress().busyProperty().setValue(Boolean.FALSE));
            }
        });
    }

    private void createCalendarEvents() {

        List<Event> calendarEvents = this.getActiveItems().stream()
                .flatMap(editorItem -> editorItem.createEvents().stream())
                .peek(event -> this.getItemDefaults().applyTo(event))
                .toList();
        log.info("Creating {} calendar events", calendarEvents.size());

        GoogleCalendarService googleCalendarService = this.getApplicationContext().getBean(GoogleCalendarService.class);
        for (int i = 0; i < calendarEvents.size(); i++) {
            Event calendarEvent = calendarEvents.get(i);
            this.getProgress().progressProperty().setValue(((double) i) / calendarEvents.size());
            googleCalendarService.insertEvent(calendarEvent, this.getActiveCalendar().getValue(), this.getActiveUser().getValue());
        }
        this.getProgress().progressProperty().setValue(1);

    }

    private ReadOnlyObjectProperty<GoogleUser> getActiveUser() {
        return this.activeUser;
    }
    private void setActiveUser(ReadOnlyObjectProperty<GoogleUser> activeUser) {
        this.activeUser = activeUser;
    }

    private ReadOnlyObjectProperty<GoogleCalendar> getActiveCalendar() {
        return this.activeCalendar;
    }
    private void setActiveCalendar(ReadOnlyObjectProperty<GoogleCalendar> activeCalendar) {
        this.activeCalendar = activeCalendar;
    }

    private ObservableList<Item> getActiveItems() {
        return this.activeItems;
    }
    private void setActiveItems(ObservableList<Item> activeItems) {
        this.activeItems = activeItems;
    }

    private ItemDefaults getItemDefaults() {
        return this.itemDefaults;
    }
    private void setItemDefaults(ItemDefaults itemDefaults) {
        this.itemDefaults = itemDefaults;
    }

    private ExecutionProgress getProgress() {
        return this.progress;
    }
    private void setProgress(ExecutionProgress progress) {
        this.progress = progress;
    }

    private ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
