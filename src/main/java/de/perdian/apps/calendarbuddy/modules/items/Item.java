package de.perdian.apps.calendarbuddy.modules.items;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;

import java.util.List;

public abstract class Item {

    public abstract List<Event> createEvents(DefaultsModel defaultsModel);

}
