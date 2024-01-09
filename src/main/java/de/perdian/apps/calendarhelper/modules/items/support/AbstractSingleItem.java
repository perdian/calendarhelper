package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.types.Availability;
import javafx.beans.property.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AbstractSingleItem implements Item {

    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty attendees = new SimpleStringProperty();
    private final BooleanProperty fullDay = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> startZoneId = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneOffset> startZoneOffset = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> endZoneId = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneOffset> endZoneOffset = new SimpleObjectProperty<>();

    public AbstractSingleItem(ItemDefaults itemDefaults) {
        this.attendeesProperty().bind(itemDefaults.attendeesProperty());
        this.fullDayProperty().addListener((o, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                this.startTimeProperty().setValue(null);
                this.endTimeProperty().setValue(null);
            }
        });
        this.startDateProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), newValue, this.startTimeProperty().getValue(), this.startZoneIdProperty().getValue()));
        this.startTimeProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), this.startDateProperty().getValue(), newValue, this.startZoneIdProperty().getValue()));
        this.startZoneIdProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), this.startDateProperty().getValue(), this.startTimeProperty().getValue(), newValue));
        this.startZoneIdProperty().setValue(itemDefaults.timezoneProperty().getValue());
        this.endDateProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), newValue, this.endTimeProperty().getValue(), this.endZoneIdProperty().getValue()));
        this.endTimeProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), this.endDateProperty().getValue(), newValue, this.endZoneIdProperty().getValue()));
        this.endZoneIdProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), this.endDateProperty().getValue(), this.endTimeProperty().getValue(), newValue));
        this.endZoneIdProperty().setValue(itemDefaults.timezoneProperty().getValue());
    }

    @Override
    public final List<Event> createEvents() {
        return Collections.singletonList(this.createEvent());
    }

    protected Event createEvent() {
        Event event = new Event();
        event.setId(DigestUtils.md5Hex(this.createEventId()));
        event.setTransparency(this.availabilityProperty().getValue().getApiValue());
        event.setAttendees(this.createEventAttendees());
        event.setStart(this.createEventDateTime(this.startDateProperty(), null, this.startTimeProperty(), this.startZoneIdProperty()));
        event.setEnd(this.createEventDateTime(this.endDateProperty(), this.startDateProperty().getValue(), this.endTimeProperty(), this.endZoneIdProperty()));
        return event;
    }

    protected String createEventId() {
        if (StringUtils.isEmpty(this.idProperty().getValue())) {
            this.idProperty().setValue("calendarHelper-" + UUID.randomUUID());
        }
        return this.idProperty().getValue();
    }

    protected List<EventAttendee> createEventAttendees() {
        if (StringUtils.isEmpty(this.attendeesProperty().getValue())) {
            return null;
        } else {
            return Arrays.stream(this.attendeesProperty().getValue().split(",;"))
                .map(String::strip)
                .filter(StringUtils::isNotEmpty)
                .map(attendee -> new EventAttendee().setEmail(attendee))
                .toList();
        }
    }

    private EventDateTime createEventDateTime(ObjectProperty<LocalDate> dateProperty, LocalDate defaultDate, ObjectProperty<LocalTime> timeProperty, ObjectProperty<ZoneId> zoneIdProperty) {
        LocalDate eventDate = dateProperty.orElse(defaultDate).getValue();
        if (eventDate == null) {
            return null;
        } else if (this.fullDayProperty().getValue()) {
            return new EventDateTime().setDate(new DateTime(eventDate.toString()));
        } else if (timeProperty.getValue() == null) {
            return null;
        } else {
            ZoneId resultZoneId = zoneIdProperty.orElse(ZoneId.systemDefault()).getValue();
            ZonedDateTime resultDateTime = eventDate.atTime(timeProperty.getValue()).atZone(resultZoneId);
            return new EventDateTime().setDateTime(new DateTime(resultDateTime.toInstant().toEpochMilli())).setTimeZone(resultZoneId.getId());
        }
    }

    private void recomputeZoneOffset(ObjectProperty<ZoneOffset> offsetProperty, LocalDate date, LocalTime time, ZoneId zoneId) {
        if (date == null || time == null || zoneId == null) {
            offsetProperty.setValue(null);
        } else {
            offsetProperty.setValue(date.atTime(time).atZone(zoneId).getOffset());
        }
    }

    public ObjectProperty<Availability> availabilityProperty() {
        return this.availability;
    }

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    private StringProperty idProperty() {
        return this.id;
    }

    public BooleanProperty fullDayProperty() {
        return this.fullDay;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return this.startTime;
    }

    public ObjectProperty<ZoneId> startZoneIdProperty() {
        return this.startZoneId;
    }

    public ObjectProperty<ZoneOffset> startZoneOffsetProperty() {
        return this.startZoneOffset;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }

    public ObjectProperty<ZoneId> endZoneIdProperty() {
        return this.endZoneId;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return this.endTime;
    }

    public ObjectProperty<ZoneOffset> endZoneOffsetProperty() {
        return this.endZoneOffset;
    }

}
