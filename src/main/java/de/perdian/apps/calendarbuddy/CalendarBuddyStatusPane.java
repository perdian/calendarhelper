package de.perdian.apps.calendarbuddy;

import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarModel;
import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutor;
import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutorStatusPane;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

class CalendarBuddyStatusPane extends GridPane {

    CalendarBuddyStatusPane(GoogleCalendarModel googleCalendarModel, JobExecutor jobExecutor) {

        ComponentFactory componentFactory = new ComponentFactory();

        JobExecutorStatusPane jobExecutorStatusPane = new JobExecutorStatusPane(jobExecutor);
        GridPane.setHgrow(jobExecutorStatusPane, Priority.ALWAYS);
        GridPane.setValignment(jobExecutorStatusPane, VPos.CENTER);

        Node nextFieldPane = componentFactory.createKeyboardShortcutPane("Tab", "Next field");
        Node deleteShortcutPane = componentFactory.createKeyboardShortcutPane("⌘ ⌫", "Delete item");
        deleteShortcutPane.disableProperty().bind(googleCalendarModel.selectedCalendarProperty().isNull());
        GridPane keyboardShortcutPane = new GridPane(10, 0);
        keyboardShortcutPane.add(nextFieldPane, 0, 0, 1, 1);
        keyboardShortcutPane.add(deleteShortcutPane, 1, 0, 1, 1);
        GridPane.setValignment(keyboardShortcutPane, VPos.CENTER);

        this.add(jobExecutorStatusPane, 0, 0, 1, 1);
        this.add(keyboardShortcutPane, 1, 0, 1, 1);
        this.setHgap(10);

    }

}
