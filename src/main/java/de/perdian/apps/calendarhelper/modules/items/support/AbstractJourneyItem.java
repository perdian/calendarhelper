package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventOutOfOfficeProperties;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public abstract class AbstractJourneyItem<C extends AbstractSingleItem> extends AbstractParentItem<C> {

    @Override
    public List<Event> createEvents() {
        List<Event> allEvents = super.createEvents();
        Event journeyEvent = this.createJourneyEvent();
        if (journeyEvent != null) {
            allEvents.add(journeyEvent);
        }
        return allEvents;
    }

    protected Event createJourneyEvent() {
        if (this.getChildren().size() <= 1) {
            return null;
        } else {

            C firstItem = this.getChildren().getFirst();
            Event firstItemEvent = firstItem.createEvent();
            C lastItem = this.getChildren().getLast();
            Event lastItemEvent = lastItem.createEvent();

            StringBuilder overallEventId = new StringBuilder("calendarHelper-overall");
            overallEventId.append("-").append(firstItem.createEventId());
            overallEventId.append("-").append(lastItem.createEventId());

            Event overallEvent = new Event();
            overallEvent.setId(DigestUtils.md5Hex(overallEventId.toString()));
            overallEvent.setStart(firstItemEvent.getStart());
            overallEvent.setEnd(lastItemEvent.getEnd());
            overallEvent.setEventType("outOfOffice");
            overallEvent.setOutOfOfficeProperties(new EventOutOfOfficeProperties());
            overallEvent.setTransparency("opaque");
            overallEvent.setSummary(this.createJourneyEventSummary());
            return overallEvent;

        }
    }

    protected abstract String createJourneyEventSummary();

}
