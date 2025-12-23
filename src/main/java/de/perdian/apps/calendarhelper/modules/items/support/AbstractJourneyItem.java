package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventOutOfOfficeProperties;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractJourneyItem<C extends Item> extends AbstractContainerItem<C> {

    protected AbstractJourneyItem(ItemDefaults defaults) {
        super(defaults);
    }

    @Override
    public List<Event> createEvents() {
        List<Event> allEvents = new ArrayList<>(super.createEvents());
        Event journeyEvent = this.createJourneyEvent();
        if (journeyEvent != null) {
            allEvents.add(journeyEvent);
        }
        return allEvents;
    }

    protected Event createJourneyEvent() {
        if (this.getChildren().size() > 1 && this.getDefaults().createJourneyEventProperty().get()) {

            C firstItem = this.getChildren().getFirst();
            Event firstItemEvent = firstItem.createEvents().getFirst();
            C lastItem = this.getChildren().getLast();
            Event lastItemEvent = lastItem.createEvents().getLast();

            Event overallEvent = new Event();
            overallEvent.setId(DigestUtils.md5Hex(UUID.randomUUID().toString()));
            overallEvent.setStart(firstItemEvent.getStart());
            overallEvent.setEnd(lastItemEvent.getEnd());
            overallEvent.setEventType("outOfOffice");
            overallEvent.setOutOfOfficeProperties(new EventOutOfOfficeProperties());
            overallEvent.setTransparency("opaque");
            overallEvent.setSummary(this.createJourneyEventSummary());
            return overallEvent;

        } else {
            return null;
        }
    }

    protected abstract String createJourneyEventSummary();

}
