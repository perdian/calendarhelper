package de.perdian.apps.calendarhelper.fx.modules.editor;

import com.google.api.services.calendar.model.Event;

import java.util.List;

public interface EditorItem {

    /**
     * Creates a list of events that are stored in the Google Calendar
     */
    List<Event> createEvents();

}
