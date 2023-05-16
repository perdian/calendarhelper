package de.perdian.apps.calendarhelper.fx.modules.execution;

import de.perdian.apps.calendarhelper.fx.CalendarHelperContext;
import de.perdian.apps.calendarhelper.fx.support.CalendarHelperDialogs;
import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarEntryJob;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class ExecutionEventHandler implements EventHandler<ActionEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExecutionEventHandler.class);

    private CalendarHelperContext calendarHelperContext = null;

    ExecutionEventHandler(CalendarHelperContext calendarHelperContext) {
        this.setCalendarHelperContext(calendarHelperContext);
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

    private void createCalendarEvents() {

        List<GoogleCalendarEntryJob> calendarEntryJobs = this.getCalendarHelperContext().editorItems().stream()
                .flatMap(editorItem -> editorItem.createEntryJobs().stream())
                .toList();
        log.info("Creating {} calendar entries", calendarEntryJobs.size());

        for (int i = 0; i < calendarEntryJobs.size(); i++) {
            GoogleCalendarEntryJob calendarEntryJob = calendarEntryJobs.get(i);
            this.getCalendarHelperContext().executionProgressProperty().setValue(((double) i) / calendarEntryJobs.size());
            throw new UnsupportedOperationException("Calendar entry creation not implemented yet!");
        }

    }

    private CalendarHelperContext getCalendarHelperContext() {
        return this.calendarHelperContext;
    }
    private void setCalendarHelperContext(CalendarHelperContext calendarHelperContext) {
        this.calendarHelperContext = calendarHelperContext;
    }

}
