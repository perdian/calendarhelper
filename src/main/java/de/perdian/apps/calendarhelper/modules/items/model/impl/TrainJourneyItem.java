package de.perdian.apps.calendarhelper.modules.items.model.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractParentItem;

import java.util.List;

public class TrainJourneyItem extends AbstractParentItem<TrainRideItem> {

    @Override
    public List<Event> createEvents() {
        throw new UnsupportedOperationException();
    }

}
