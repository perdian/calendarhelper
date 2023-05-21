package de.perdian.apps.calendarhelper.modules.items.model.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractParentItem;

import java.util.ArrayList;
import java.util.List;

public class TrainJourneyItem extends AbstractParentItem<TrainRideItem> {

    @Override
    public List<Event> createEvents() {
        List<Event> allEvents = new ArrayList<>();

        for (TrainRideItem trainRideItem : this.getChildren()) {
            Event trainRideEvent = trainRideItem.createEvent();
            allEvents.add(trainRideEvent);
        }

        return allEvents;
    }

}
