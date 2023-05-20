package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import de.perdian.apps.calendarhelper.fx.modules.editor.impl.types.Availability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDateItem implements EditorItem {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty attendees = new SimpleStringProperty();
    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);

    protected Event createEvent() {
        if (this.startDateProperty().getValue() == null) {
            throw new IllegalArgumentException("No start date set!");
        } else {
            Event event = new Event();
            event.setStart(new EventDateTime().setDate(new DateTime(this.startDateProperty().getValue().toString())));
            event.setEnd(new EventDateTime().setDate(new DateTime(this.endDateProperty().getValue() == null ? this.startDateProperty().getValue().toString() : this.endDateProperty().getValue().toString())));
            event.setSummary(this.summaryProperty().getValue());
            event.setLocation(this.locationProperty().getValue());
            event.setDescription(this.descriptionProperty().getValue());
            event.setAttendees(this.createAttendees());
            event.setTransparency(this.availabilityProperty().getValue().getApiValue());
            return event;
        }
    }

    protected List<EventAttendee> createAttendees() {
        return Arrays.stream(StringUtils.defaultIfEmpty(this.attendeesProperty().getValue(), "").split(",;"))
                .map(String::strip)
                .filter(StringUtils::isNotEmpty)
                .map(attendee -> new EventAttendee().setEmail(attendee))
                .toList();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
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
