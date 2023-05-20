package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.services.calendar.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ConferenceItem extends AbstractDateItem {

    @Override
    public List<Event> createEvents() {

        List<Event> allEvents = new ArrayList<>();
        allEvents.add(this.createEvent());

//        // OutOfOffice events cannot be created via the Google Calendar API at this time :-(
//        // https://issuetracker.google.com/issues/112063903
//        Event outOfOfficeEvent = this.createEvent();
//        outOfOfficeEvent.setEventType("outOfOffice");
//        allEvents.add(outOfOfficeEvent);

        return allEvents;

    }

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary("[Konferenz] " + event.getSummary());
        return event;
    }

}
