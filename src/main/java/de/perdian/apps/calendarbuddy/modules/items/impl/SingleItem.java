package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.support.types.CalendarAvailability;
import javafx.beans.property.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class SingleItem extends Item {

    private final ObjectProperty<CalendarAvailability> calendarAvailability;
    private final BooleanProperty fullDay;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalTime> startTime;
    private final ObjectProperty<ZoneId> startZoneId;
    private final ObjectProperty<ZoneOffset> startZoneOffset;
    private final ObjectProperty<LocalDate> endDate;
    private final ObjectProperty<LocalTime> endTime;
    private final ObjectProperty<ZoneId> endZoneId;
    private final ObjectProperty<ZoneOffset> endZoneOffset;

    protected SingleItem() {

        this.calendarAvailability = new SimpleObjectProperty<>(CalendarAvailability.BLOCKED);
        this.fullDay = new SimpleBooleanProperty(false);
        this.startDate = new SimpleObjectProperty<>();
        this.startTime = new SimpleObjectProperty<>();
        this.startZoneId = new SimpleObjectProperty<>(ZoneId.systemDefault());
        this.startZoneOffset = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.endTime = new SimpleObjectProperty<>();
        this.endZoneId = new SimpleObjectProperty<>(ZoneId.systemDefault());
        this.endZoneOffset = new SimpleObjectProperty<>();

        this.startDate.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.startZoneOffset, newValue, this.startTime.getValue(), this.startZoneId.getValue()));
        this.startTime.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.startZoneOffset, this.startDate.getValue(), newValue, this.startZoneId.getValue()));
        this.startZoneId.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.startZoneOffset, this.startDate.getValue(), this.startTime.getValue(), newValue));
        this.endDate.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.endZoneOffset, newValue, this.endTime.getValue(), this.endZoneId.getValue()));
        this.endTime.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.endZoneOffset, this.endDate.getValue(), newValue, this.endZoneId.getValue()));
        this.endZoneId.addListener((_, _, newValue) -> this.recomputeZoneOffset(this.endZoneOffset, this.endDate.getValue(), this.endTime.getValue(), newValue));
        this.fullDay.addListener((_, _, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                this.startTime.setValue(null);
                this.endTime.setValue(null);
            }
        });

    }

    private void recomputeZoneOffset(ObjectProperty<ZoneOffset> offsetProperty, LocalDate date, LocalTime time, ZoneId zoneId) {
        if (date == null || time == null || zoneId == null) {
            offsetProperty.setValue(null);
        } else {
            offsetProperty.setValue(date.atTime(time).atZone(zoneId).getOffset());
        }
    }

    @Override
    public final List<Event> createEvents(DefaultsModel defaultsModel) {
        return Collections.singletonList(this.createEvent(defaultsModel));
    }

    protected Event createEvent(DefaultsModel defaultsModel) {
        Event event = new Event();
        event.setId(DigestUtils.md5Hex("calendarBuddy-" + UUID.randomUUID()));
        event.setTransparency(this.calendarAvailability.getValue().getGoogleApiValue());
        event.setAttendees(this.createEventAttendees(defaultsModel));
        event.setStart(this.createEventDateTime(this.startDate, null, this.startTime, this.startZoneId, defaultsModel.timezoneProperty()));
        event.setEnd(this.createEventDateTime(this.endDate, this.startDate.getValue(), this.endTime, this.endZoneId, defaultsModel.timezoneProperty()));
        return event;
    }

    protected List<EventAttendee> createEventAttendees(DefaultsModel defaultsModel) {
        if (StringUtils.isEmpty(defaultsModel.attendeesProperty().getValue())) {
            return null;
        } else {
            return Arrays.stream(defaultsModel.attendeesProperty().getValue().split(",;"))
                .map(String::strip)
                .filter(StringUtils::isNotEmpty)
                .map(attendee -> new EventAttendee().setEmail(attendee))
                .toList();
        }
    }

    private EventDateTime createEventDateTime(ObjectProperty<LocalDate> dateProperty, LocalDate defaultDate, ObjectProperty<LocalTime> timeProperty, ObjectProperty<ZoneId> zoneIdProperty, ObjectProperty<ZoneId> fallbackZoneIdProperty) {
        LocalDate eventDate = dateProperty.orElse(defaultDate).getValue();
        if (eventDate == null) {
            return null;
        } else if (this.fullDay.getValue()) {
            return new EventDateTime().setDate(new DateTime(eventDate.toString()));
        } else if (timeProperty.getValue() == null) {
            return null;
        } else {
            ZoneId fallbackZoneId = fallbackZoneIdProperty.orElse(ZoneId.systemDefault()).getValue();
            ZoneId resultZoneId = zoneIdProperty.orElse(fallbackZoneId).getValue();
            ZonedDateTime resultDateTime = eventDate.atTime(timeProperty.getValue()).atZone(resultZoneId);
            return new EventDateTime().setDateTime(new DateTime(resultDateTime.toInstant().toEpochMilli())).setTimeZone(resultZoneId.getId());
        }
    }

    public CalendarAvailability getCalendarAvailability() {
        return calendarAvailability.get();
    }
    public ObjectProperty<CalendarAvailability> calendarAvailabilityProperty() {
        return this.calendarAvailability;
    }
    public void setCalendarAvailability(CalendarAvailability calendarAvailability) {
        this.calendarAvailability.set(calendarAvailability);
    }

    public boolean isFullDay() {
        return this.fullDay.get();
    }
    public BooleanProperty fullDayProperty() {
        return this.fullDay;
    }
    public void setFullDay(boolean fullDay) {
        this.fullDay.set(fullDay);
    }

    public LocalDate getStartDate() {
        return this.startDate.get();
    }
    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalTime getStartTime() {
        return this.startTime.get();
    }
    public ObjectProperty<LocalTime> startTimeProperty() {
        return this.startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }

    public ZoneId getStartZoneId() {
        return this.startZoneId.get();
    }
    public ObjectProperty<ZoneId> startZoneIdProperty() {
        return this.startZoneId;
    }
    public void setStartZoneId(ZoneId startZoneId) {
        this.startZoneId.set(startZoneId);
    }

    public ZoneOffset getStartZoneOffset() {
        return this.startZoneOffset.get();
    }
    public ReadOnlyObjectProperty<ZoneOffset> startZoneOffsetProperty() {
        return this.startZoneOffset;
    }

    public ZonedDateTime toStartZonedDateTime() {
        return this.startDateProperty().getValue() == null || this.startTimeProperty().getValue() == null ? null : this.startDateProperty().getValue().atTime(this.startTimeProperty().getValue()).atZone(this.startZoneIdProperty().getValue());
    }

    public LocalDate getEndDate() {
        return this.endDate.get();
    }
    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public LocalTime getEndTime() {
        return this.endTime.get();
    }
    public ObjectProperty<LocalTime> endTimeProperty() {
        return this.endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime.set(endTime);
    }

    public ZoneId getEndZoneId() {
        return this.endZoneId.get();
    }
    public ObjectProperty<ZoneId> endZoneIdProperty() {
        return this.endZoneId;
    }
    public void setEndZoneId(ZoneId endZoneId) {
        this.endZoneId.set(endZoneId);
    }

    public ZonedDateTime toEndZonedDateTime() {
        return this.endDateProperty().getValue() == null || this.endTimeProperty().getValue() == null ? null : this.endDateProperty().getValue().atTime(this.endTimeProperty().getValue()).atZone(this.endZoneIdProperty().getValue());
    }

    public ZoneOffset getEndZoneOffset() {
        return this.endZoneOffset.get();
    }
    public ObjectProperty<ZoneOffset> endZoneOffsetProperty() {
        return this.endZoneOffset;
    }
    public void setEndZoneOffset(ZoneOffset endZoneOffset) {
        this.endZoneOffset.set(endZoneOffset);
    }

}
