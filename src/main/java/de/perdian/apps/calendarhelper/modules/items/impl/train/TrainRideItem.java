package de.perdian.apps.calendarhelper.modules.items.impl.train;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractSingleItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TrainRideItem extends AbstractSingleItem {

    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty departureStation = new SimpleStringProperty();
    private final StringProperty departureTrack = new SimpleStringProperty();
    private final StringProperty arrivalStation = new SimpleStringProperty();
    private final StringProperty arrivalTrack = new SimpleStringProperty();
    private final StringProperty reservedWagon = new SimpleStringProperty();
    private final StringProperty reservedSeats = new SimpleStringProperty();
    private final StringProperty bookingCode = new SimpleStringProperty();
    private final StringProperty comments = new SimpleStringProperty();

    public TrainRideItem(ItemDefaults itemDefaults) {
        super(itemDefaults);
        this.startDateProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null && this.endDateProperty().getValue() == null) {
                this.endDateProperty().setValue(newValue);
            }
        });
    }

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.createEventSummary());
        event.setLocation(this.createEventLocation());
        event.setDescription(this.createEventDescription());
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


    private String createEventDescription() {

        ZonedDateTime departureDateTime = this.toStartZonedDateTime();
        ZonedDateTime arrivalDateTime = this.toEndZonedDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withLocale(Locale.GERMANY);

        StringBuilder eventDescription = new StringBuilder();
        eventDescription.append("<strong>Zugfahrt</strong>\n");
        eventDescription.append(this.typeProperty().getValue());
        eventDescription.append(" ").append(this.numberProperty().getValue());

        eventDescription.append("\n\n<strong>Abfahrt</strong>\n");
        eventDescription.append(dateTimeFormatter.format(departureDateTime));
        if (!ZoneId.of("Europe/Berlin").equals(departureDateTime.getZone()) || !ZoneId.of("Europe/Berlin").equals(arrivalDateTime.getZone())) {
            eventDescription.append(" (").append(departureDateTime.getZone()).append(")");
        }
        eventDescription.append("\n").append(this.departureStationProperty().getValue());
        if (StringUtils.isNotEmpty(this.departureTrackProperty().getValue())) {
            eventDescription.append(" // Gleis ").append(this.departureTrackProperty().getValue());
        }

        eventDescription.append("\n\n<strong>Ankunft</strong>\n");
        eventDescription.append(dateTimeFormatter.format(arrivalDateTime));
        if (!ZoneId.of("Europe/Berlin").equals(departureDateTime.getZone()) || !ZoneId.of("Europe/Berlin").equals(arrivalDateTime.getZone())) {
            eventDescription.append(" (").append(arrivalDateTime.getZone()).append(")");
        }
        eventDescription.append("\n").append(this.arrivalStationProperty().getValue());
        if (StringUtils.isNotEmpty(this.arrivalTrackProperty().getValue())) {
            eventDescription.append(" // Gleis ").append(this.arrivalTrackProperty().getValue());
        }

        if (StringUtils.isNotEmpty(this.reservedWagonProperty().getValue()) && StringUtils.isNotEmpty(this.reservedSeatsProperty().getValue())) {
            eventDescription.append("\n\n<strong>Sitzplatz</strong>\n");
            eventDescription.append("Wagen ").append(this.reservedWagonProperty().getValue());
            eventDescription.append(" // Sitz ").append(this.reservedSeatsProperty().getValue());
        }

        Duration flightDuration = Duration.between(departureDateTime, arrivalDateTime);
        eventDescription.append("\n\n<strong>Dauer</strong>\n");
        if (flightDuration.toHours() > 0) {
            eventDescription.append(flightDuration.toHours()).append(" h ");
        }
        eventDescription.append(flightDuration.toMinutesPart()).append(" min");

        if (StringUtils.isNotEmpty(this.bookingCodeProperty().getValue())) {
            eventDescription.append("\n\n<strong>Buchungscode</strong>\n");
            eventDescription.append(this.bookingCodeProperty().getValue());
        }

        if (StringUtils.isNotEmpty(this.commentsProperty().getValue())) {
            eventDescription.append(eventDescription.isEmpty() ? "" : "\n\n<hr/>\n").append(this.commentsProperty().getValue());
        }
        return eventDescription.toString();

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

    public StringProperty departureTrackProperty() {
        return this.departureTrack;
    }

    public StringProperty arrivalStationProperty() {
        return this.arrivalStation;
    }

    public StringProperty arrivalTrackProperty() {
        return this.arrivalTrack;
    }

    public StringProperty reservedWagonProperty() {
        return this.reservedWagon;
    }

    public StringProperty reservedSeatsProperty() {
        return this.reservedSeats;
    }

    public StringProperty bookingCodeProperty() {
        return this.bookingCode;
    }

    public StringProperty commentsProperty() {
        return this.comments;
    }

}
