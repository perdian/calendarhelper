package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import de.perdian.apps.calendarhelper.fx.modules.editor.impl.types.Availability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class GenericItem extends AbstractItem {

    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty attendees = new SimpleStringProperty();
    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.summaryProperty().getValue());
        event.setLocation(this.locationProperty().getValue());
        event.setDescription(this.descriptionProperty().getValue());
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

    public StringProperty summaryProperty() {
        return this.summary;
    }

    public StringProperty locationProperty() {
        return this.location;
    }

    public StringProperty descriptionProperty() {
        return this.description;
    }

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    public ObjectProperty<Availability> availabilityProperty() {
        return this.availability;
    }

}
