package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@JsonTypeName("trainSingleItem")
@JsonClassDescription("""
    One individual ride on a train, bus, tram or similar vehicle between two stations, \
    with its own line or train number. Only used as an element of the 'children' of a \
    trainJourneyItem, never as a top-level item.
    """)
public class TrainSingleItem extends SingleItem {

    private final StringProperty type;
    private final StringProperty number;
    private final StringProperty departureStation;
    private final StringProperty departureTrack;
    private final StringProperty arrivalStation;
    private final StringProperty arrivalTrack;
    private final StringProperty reservedWagon;
    private final StringProperty reservedSeats;
    private final StringProperty bookingCode;

    public TrainSingleItem() {
        this.type = new SimpleStringProperty();
        this.number = new SimpleStringProperty();
        this.departureStation = new SimpleStringProperty();
        this.departureTrack = new SimpleStringProperty();
        this.arrivalStation = new SimpleStringProperty();
        this.arrivalTrack = new SimpleStringProperty();
        this.reservedWagon = new SimpleStringProperty();
        this.reservedSeats = new SimpleStringProperty();
        this.bookingCode = new SimpleStringProperty();
        this.startZoneIdProperty().setValue(ZoneId.systemDefault());
        this.endZoneIdProperty().setValue(ZoneId.systemDefault());
        this.startDateProperty().addListener((_, _, newValue) -> {
            if (newValue != null && this.endDateProperty().getValue() == null) {
                this.endDateProperty().setValue(newValue);
            }
        });
    }

    @Override
    public Event createEvent(DefaultsModel defaultsModel) {
        Event event = super.createEvent(defaultsModel);
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
            eventSummary.append(" ").append(this.createEventSummaryIcon()).append(" ");
        }
        eventSummary.append(this.departureStationProperty().getValue());
        eventSummary.append(" » ");
        eventSummary.append(this.arrivalStationProperty().getValue());
        return eventSummary.toString();
    }

    private String createEventSummaryIcon() {
        if ("Bus".equalsIgnoreCase(this.typeProperty().get())) {
            return "🚍";
        } else if ("ICE".equalsIgnoreCase(this.typeProperty().get()) || "ECE".equalsIgnoreCase(this.typeProperty().get())) {
            return "🚆";
        } else if ("STR".equalsIgnoreCase(this.typeProperty().get())) {
            return "🚃";
        } else if ("U".equalsIgnoreCase(this.typeProperty().get())) {
            return "🚇";
        } else {
            return "🚊";
        }
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
        return eventDescription.toString();

    }

    public String getType() {
        return this.type.get();
    }
    public StringProperty typeProperty() {
        return this.type;
    }
    public void setType(String type) {
        this.type.set(type);
    }

    public String getNumber() {
        return this.number.get();
    }
    public StringProperty numberProperty() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getDepartureStation() {
        return this.departureStation.get();
    }
    public StringProperty departureStationProperty() {
        return this.departureStation;
    }
    public void setDepartureStation(String departureStation) {
        this.departureStation.set(departureStation);
    }

    public String getDepartureTrack() {
        return this.departureTrack.get();
    }
    public StringProperty departureTrackProperty() {
        return this.departureTrack;
    }
    public void setDepartureTrack(String departureTrack) {
        this.departureTrack.set(departureTrack);
    }

    public String getArrivalStation() {
        return this.arrivalStation.get();
    }
    public StringProperty arrivalStationProperty() {
        return this.arrivalStation;
    }
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation.set(arrivalStation);
    }

    public String getArrivalTrack() {
        return this.arrivalTrack.get();
    }
    public StringProperty arrivalTrackProperty() {
        return this.arrivalTrack;
    }
    public void setArrivalTrack(String arrivalTrack) {
        this.arrivalTrack.set(arrivalTrack);
    }

    public String getReservedWagon() {
        return this.reservedWagon.get();
    }
    public StringProperty reservedWagonProperty() {
        return this.reservedWagon;
    }
    public void setReservedWagon(String reservedWagon) {
        this.reservedWagon.set(reservedWagon);
    }

    public String getReservedSeats() {
        return this.reservedSeats.get();
    }
    public StringProperty reservedSeatsProperty() {
        return this.reservedSeats;
    }
    public void setReservedSeats(String reservedSeats) {
        this.reservedSeats.set(reservedSeats);
    }

    public String getBookingCode() {
        return this.bookingCode.get();
    }
    public StringProperty bookingCodeProperty() {
        return this.bookingCode;
    }
    public void setBookingCode(String bookingCode) {
        this.bookingCode.set(bookingCode);
    }

}
