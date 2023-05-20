package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class AbstractDateTimeItem extends AbstractDateItem {

    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> startZoneId = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> endZoneId = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        if (this.startTimeProperty().getValue() == null) {
            throw new IllegalArgumentException("No start time set!");
        } else if (this.startZoneIdProperty().getValue() == null) {
            throw new IllegalArgumentException("No start timezone set!");
        } else if (this.endTimeProperty().getValue() == null) {
            throw new IllegalArgumentException("No end time set!");
        } else if (this.endZoneIdProperty().getValue() == null) {
            throw new IllegalArgumentException("No end timezone set!");
        } else {
            ZonedDateTime startTime = this.startDateProperty().getValue().atTime(this.startTimeProperty().getValue()).atZone(this.startZoneIdProperty().getValue());
            ZonedDateTime endTime = this.endDateProperty().getValue().atTime(this.endTimeProperty().getValue()).atZone(this.endZoneIdProperty().getValue());
            event.setStart(new EventDateTime().setDateTime(new DateTime(startTime.toInstant().toEpochMilli())).setTimeZone(this.startZoneIdProperty().getValue().getId()));
            event.setEnd(new EventDateTime().setDateTime(new DateTime(endTime.toInstant().toEpochMilli())).setTimeZone(this.endZoneIdProperty().getValue().getId()));
            return event;
        }
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return this.startTime;
    }

    public ObjectProperty<ZoneId> startZoneIdProperty() {
        return this.startZoneId;
    }

    public ObjectProperty<ZoneId> endZoneIdProperty() {
        return this.endZoneId;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return this.endTime;
    }

}
