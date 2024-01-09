package de.perdian.apps.calendarhelper.modules.items;

import com.google.api.services.calendar.model.Event;

import java.util.List;

public interface Item {

    List<Event> createEvents();

}
