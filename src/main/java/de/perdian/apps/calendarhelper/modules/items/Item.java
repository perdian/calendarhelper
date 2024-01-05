package de.perdian.apps.calendarhelper.modules.items;

import com.google.api.services.calendar.model.Event;

import java.util.List;

public interface Item {

    /**
     * Creates a list of events that are stored in the Google Calendar
     */
    List<Event> createEvents();

}
