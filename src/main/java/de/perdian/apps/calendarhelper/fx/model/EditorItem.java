package de.perdian.apps.calendarhelper.fx.model;

import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarEntryJob;
import javafx.scene.layout.Pane;

public interface EditorItem {

    /**
     * Creates a JavaFX pane that is used to display the edit options for this item
     */
    Pane createPane();

    /**
     * Creates a list of jobs that are used to transform this item into an actual Google calendar item
     */
    GoogleCalendarEntryJob createEntryJob();

}
