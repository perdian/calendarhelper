package de.perdian.apps.calendarhelper.fx.modules.editor;

import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarEntryJob;

public interface EditorItem {

    /**
     * Creates a list of jobs that are used to transform this item into an actual Google calendar item
     */
    GoogleCalendarEntryJob createEntryJob();

}
