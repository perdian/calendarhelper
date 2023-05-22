package de.perdian.apps.calendarhelper.modules.items.model.support;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AbstractDateTimeItem extends AbstractItem {

    private final BooleanProperty fullDay = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> startZoneId = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneOffset> startZoneOffset = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> endZoneId = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneOffset> endZoneOffset = new SimpleObjectProperty<>();

    public AbstractDateTimeItem() {
        this.fullDayProperty().addListener((o, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                this.startTimeProperty().setValue(null);
                this.endTimeProperty().setValue(null);
            }
        });
        this.startDateProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), newValue, this.startTimeProperty().getValue(), this.startZoneIdProperty().getValue()));
        this.startTimeProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), this.startDateProperty().getValue(), newValue, this.startZoneIdProperty().getValue()));
        this.startZoneIdProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.startZoneOffsetProperty(), this.startDateProperty().getValue(), this.startTimeProperty().getValue(), newValue));
        this.startZoneIdProperty().setValue(ZoneId.of("Europe/Berlin"));
        this.endDateProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), newValue, this.endTimeProperty().getValue(), this.endZoneIdProperty().getValue()));
        this.endTimeProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), this.endDateProperty().getValue(), newValue, this.endZoneIdProperty().getValue()));
        this.endZoneIdProperty().addListener((o, oldValue, newValue) -> this.recomputeZoneOffset(this.endZoneOffsetProperty(), this.endDateProperty().getValue(), this.endTimeProperty().getValue(), newValue));
        this.endZoneIdProperty().setValue(ZoneId.of("Europe/Berlin"));
    }

    @Override
    public List<Event> createEvents() {
        return Collections.singletonList(this.createEvent());
    }

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setId(this.createEventId());
        event.setStart(this.createEventDateTime(this.startDateProperty(), null, this.startTimeProperty(), this.startZoneIdProperty()));
        event.setEnd(this.createEventDateTime(this.endDateProperty(), this.startDateProperty().getValue(), this.endTimeProperty(), this.endZoneIdProperty()));
        return event;
    }

    protected String createEventId() {
        return UUID.randomUUID().toString();
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
            ZoneId resultZoneId = zoneIdProperty.orElse(ZoneId.of("Europe/Berlin")).getValue();
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
