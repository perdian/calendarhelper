package de.perdian.apps.calendarhelper.modules.items.model.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractDateTimeItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

public class TrainRideItem extends AbstractDateTimeItem {

    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty departureStation = new SimpleStringProperty();
    private final StringProperty arrivalStation = new SimpleStringProperty();
    private final StringProperty reservedWagon = new SimpleStringProperty();
    private final StringProperty reservedSeats = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.createEventSummary());
        event.setLocation(this.createEventLocation());
        event.setDescription(this.commentProperty().getValue());
        return event;
    }

    private String createEventSummary() {
        StringBuilder eventSummary = new StringBuilder();
        if (StringUtils.isNotEmpty(this.typeProperty().getValue()) || StringUtils.isNotEmpty(this.numberProperty().getValue())) {
            if (StringUtils.isNotEmpty(this.typeProperty().getValue())) {
                eventSummary.append(this.typeProperty().getValue());
                if (StringUtils.isNotEmpty(this.numberProperty().getValue())) {
                    eventSummary.append(" ");
                }
            }
            if (StringUtils.isNotEmpty(this.numberProperty().getValue())) {
                eventSummary.append(this.numberProperty().getValue());
            }
            eventSummary.append(" 🚊 ");
        }
        eventSummary.append(this.departureStationProperty().getValue());
        eventSummary.append(" » ");
        eventSummary.append(this.arrivalStationProperty().getValue());
        return eventSummary.toString();
    }

    private String createEventLocation() {
        if (StringUtils.isEmpty(this.reservedWagonProperty().getValue()) && StringUtils.isEmpty(this.reservedSeatsProperty().getValue())) {
            return null;
        } else {
            StringBuilder eventLocation = new StringBuilder();
            if (StringUtils.isNotEmpty(this.reservedWagonProperty().getValue())) {
                eventLocation.append("Wagen ").append(this.reservedWagonProperty().getValue());
            }
            if (StringUtils.isNotEmpty(this.reservedSeatsProperty().getValue())) {
                eventLocation.append(StringUtils.isEmpty(eventLocation) ? "" : " // ");
                eventLocation.append("Sitz(e) ").append(this.reservedSeatsProperty().getValue());
            }
            return eventLocation.toString();
        }
    }

    public StringProperty typeProperty() {
        return this.type;
    }

    public StringProperty numberProperty() {
        return this.number;
    }

    public StringProperty departureStationProperty() {
        return this.departureStation;
    }

    public StringProperty arrivalStationProperty() {
        return this.arrivalStation;
    }

    public StringProperty reservedWagonProperty() {
        return this.reservedWagon;
    }

    public StringProperty reservedSeatsProperty() {
        return this.reservedSeats;
    }

    public StringProperty commentProperty() {
        return this.comment;
    }

}
