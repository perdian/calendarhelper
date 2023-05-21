package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public abstract class AbstractItem implements EditorItem {

    private final BooleanProperty fullDay = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> startZoneId = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> endZoneId = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));

    public AbstractItem() {
        this.fullDayProperty().addListener((o, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                this.endDateProperty().setValue(null);
                this.endTimeProperty().setValue(null);
            }
        });
    }

    @Override
    public List<Event> createEvents() {
        return Collections.singletonList(this.createEvent());
    }

    protected Event createEvent() {
        Event event = new Event();
        event.setStart(this.createEventDateTime(this.startDateProperty(), null, this.startTimeProperty(), this.startZoneIdProperty()));
        event.setEnd(this.createEventDateTime(this.endDateProperty(), this.startDateProperty().getValue(), this.endTimeProperty(), this.endZoneIdProperty()));
        return event;
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

    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }

    public ObjectProperty<ZoneId> endZoneIdProperty() {
        return this.endZoneId;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return this.endTime;
    }

}
