package de.perdian.apps.calendarhelper.fx.modules.execution;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.fx.CalendarHelperContext;
import de.perdian.apps.calendarhelper.fx.support.CalendarHelperDialogs;
import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

class ExecutionEventHandler implements EventHandler<ActionEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExecutionEventHandler.class);

    private CalendarHelperContext calendarHelperContext = null;
    private ApplicationContext applicationContext = null;

    ExecutionEventHandler(CalendarHelperContext calendarHelperContext, ApplicationContext applicationContext) {
        this.setCalendarHelperContext(calendarHelperContext);
        this.setApplicationContext(applicationContext);
    }

    @Override
    public void handle(ActionEvent event) {
        Platform.runLater(() -> this.getCalendarHelperContext().executionActiveProperty().setValue(Boolean.TRUE));
        Thread.ofVirtual().start(() -> {
            try {
                this.createCalendarEvents();
            } catch (Exception e) {
                log.warn("Cannot create calendar entries", e);
                CalendarHelperDialogs.showErrorDialog("Create calendar entries", "Cannot create calendar entries", e);
            } finally {
                Platform.runLater(() -> this.getCalendarHelperContext().executionActiveProperty().setValue(Boolean.FALSE));
            }
        });
    }

    private void createCalendarEvents() throws Exception {

        List<Event> calendarEvents = this.getCalendarHelperContext().editorItems().stream()
                .flatMap(editorItem -> editorItem.createEvents().stream())
                .toList();
        log.info("Creating {} calendar entries", calendarEvents.size());

        GoogleCalendarService googleCalendarService = this.getApplicationContext().getBean(GoogleCalendarService.class);
        for (int i = 0; i < calendarEvents.size(); i++) {
            Event calendarEvent = calendarEvents.get(i);
            this.getCalendarHelperContext().executionProgressProperty().setValue(((double) i) / calendarEvents.size());
            googleCalendarService.insertEvent(calendarEvent, this.getCalendarHelperContext().activeGoogleCalendarProperty().getValue(), this.getCalendarHelperContext().activeGoogleUserProperty().getValue());
        }

    }

    private CalendarHelperContext getCalendarHelperContext() {
        return this.calendarHelperContext;
    }
    private void setCalendarHelperContext(CalendarHelperContext calendarHelperContext) {
        this.calendarHelperContext = calendarHelperContext;
    }

    private ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}