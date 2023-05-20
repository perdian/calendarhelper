package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.services.calendar.model.Event;

import java.util.List;

public class ConferenceItem extends AbstractDateItem {

    @Override
    public List<Event> createEvents() {

        Event mainEvent = this.createEvent();
        mainEvent.setSummary("[Konferenz] " + mainEvent.getSummary());

        return List.of(mainEvent);

    }

}
