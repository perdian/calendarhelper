package de.perdian.apps.calendarhelper.modules.execution.handlers;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.CalendarHelperSelection;
import de.perdian.apps.calendarhelper.modules.execution.ExecutionProgress;
import de.perdian.apps.calendarhelper.modules.google.calendar.GoogleCalendarService;
import de.perdian.apps.calendarhelper.support.fx.CalendarHelperDialogs;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ExecutionActionEventHandler implements EventHandler<ActionEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExecutionActionEventHandler.class);

    private CalendarHelperSelection selection = null;
    private ExecutionProgress progress = null;
    private ApplicationContext applicationContext = null;

    public ExecutionActionEventHandler(CalendarHelperSelection selection, ExecutionProgress progress, ApplicationContext applicationContext) {
        this.setSelection(selection);
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

        List<Event> calendarEvents = this.getSelection().getActiveItems().stream()
                .flatMap(editorItem -> editorItem.createEvents().stream())
                .peek(event -> this.getSelection().getItemDefaults().applyTo(event))
                .toList();
        log.info("Creating {} calendar events", calendarEvents.size());

        GoogleCalendarService googleCalendarService = this.getApplicationContext().getBean(GoogleCalendarService.class);
        for (int i = 0; i < calendarEvents.size(); i++) {
            Event calendarEvent = calendarEvents.get(i);
            this.getProgress().progressProperty().setValue(((double) i) / calendarEvents.size());
            googleCalendarService.insertEvent(calendarEvent, this.getSelection().activeCalendarProperty().getValue(), this.getSelection().activeUserProperty().getValue());
        }
        this.getProgress().progressProperty().setValue(1);

    }

    private CalendarHelperSelection getSelection() {
        return this.selection;
    }
    private void setSelection(CalendarHelperSelection selection) {
        this.selection = selection;
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
