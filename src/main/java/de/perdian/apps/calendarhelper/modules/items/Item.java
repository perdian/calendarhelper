package de.perdian.apps.calendarhelper.modules.items;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.support.types.CalendarValues;

import java.util.List;

public abstract class Item {

    private CalendarValues calendarValues = null;

    protected Item(ItemDefaults defaults) {
        this.setCalendarValues(new CalendarValues(defaults));
    }

    public abstract List<Event> createEvents();

    public CalendarValues getCalendarValues() {
        return this.calendarValues;
    }
    public void setCalendarValues(CalendarValues calendarValues) {
        this.calendarValues = calendarValues;
    }

}
