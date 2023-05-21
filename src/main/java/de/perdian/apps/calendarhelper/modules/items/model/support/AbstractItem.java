package de.perdian.apps.calendarhelper.modules.items.model.support;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import de.perdian.apps.calendarhelper.modules.items.model.Item;
import de.perdian.apps.calendarhelper.modules.items.model.types.Availability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractItem implements Item {

    private final StringProperty attendees = new SimpleStringProperty();
    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);

    protected Event createEvent() {
        Event event = new Event();
        event.setAttendees(this.createAttendees());
        event.setTransparency(this.availabilityProperty().getValue().getApiValue());
        return event;
    }

    protected List<EventAttendee> createAttendees() {
        return Arrays.stream(StringUtils.defaultIfEmpty(this.attendeesProperty().getValue(), "").split(",;"))
                .map(String::strip)
                .filter(StringUtils::isNotEmpty)
                .map(attendee -> new EventAttendee().setEmail(attendee))
                .toList();
    }

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    public ObjectProperty<Availability> availabilityProperty() {
        return this.availability;
    }

}
