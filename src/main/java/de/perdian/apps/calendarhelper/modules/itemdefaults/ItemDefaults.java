package de.perdian.apps.calendarhelper.modules.itemdefaults;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ItemDefaults {

    private final StringProperty attendees = new SimpleStringProperty();

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    public void applyTo(Event event) {
        if (event.getAttendees() == null) {
            event.setAttendees(this.createAttendees());
        }
    }

    protected List<EventAttendee> createAttendees() {
        return Arrays.stream(StringUtils.defaultIfEmpty(this.attendeesProperty().getValue(), "").split(",;"))
                .map(String::strip)
                .filter(StringUtils::isNotEmpty)
                .map(attendee -> new EventAttendee().setEmail(attendee))
                .toList();
    }

}
