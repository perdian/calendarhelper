package de.perdian.apps.calendarhelper.modules.items.model.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractDateTimeItem;
import de.perdian.apps.calendarhelper.support.airtravel.Airline;
import de.perdian.apps.calendarhelper.support.airtravel.AirlineRepository;
import de.perdian.apps.calendarhelper.support.airtravel.Airport;
import de.perdian.apps.calendarhelper.support.airtravel.AirportRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AirtravelFlightItem extends AbstractDateTimeItem {

    private final StringProperty airlineCode = new SimpleStringProperty();
    private final StringProperty flightNumber = new SimpleStringProperty();
    private final StringProperty departureAirportCode = new SimpleStringProperty();
    private final StringProperty departureAirportName = new SimpleStringProperty();
    private final StringProperty arrivalAirportCode = new SimpleStringProperty();
    private final StringProperty arrivalAirportName = new SimpleStringProperty();
    private final StringProperty airplaneType = new SimpleStringProperty();
    private final StringProperty seats = new SimpleStringProperty();
    private final StringProperty bookingCode = new SimpleStringProperty();
    private final StringProperty comments = new SimpleStringProperty();

    public AirtravelFlightItem() {
        this.departureAirportCodeProperty().addListener((o, oldValue, newValue) -> this.afterAirportCodeUpdated(newValue, this.startZoneIdProperty(), this.departureAirportNameProperty()));
        this.arrivalAirportCodeProperty().addListener((o, oldValue, newValue) -> this.afterAirportCodeUpdated(newValue, this.endZoneIdProperty(), this.arrivalAirportNameProperty()));
        this.startZoneIdProperty().setValue(null);
        this.endZoneIdProperty().setValue(null);
    }

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.createEventSummary());
        event.setLocation(this.createEventLocation());
        event.setDescription(this.createEventDescription());
        return event;
    }

    @Override
    protected String createEventId() {
        StringBuilder eventId = new StringBuilder();
        eventId.append(this.startDateProperty().getValue());
        eventId.append(this.startTimeProperty().getValue());
        eventId.append(this.startZoneIdProperty().getValue());
        eventId.append("-");
        eventId.append(this.endDateProperty().getValue());
        eventId.append(this.endTimeProperty().getValue());
        eventId.append(this.endZoneIdProperty().getValue());
        return DigestUtils.md5Hex(eventId.toString());
    }

    private String createEventSummary() {
        StringBuilder eventSummary = new StringBuilder();
        eventSummary.append(this.airlineCodeProperty().getValue()).append(" ").append(this.flightNumberProperty().getValue()).append(" ✈️ ");
        eventSummary.append(this.departureAirportCodeProperty().getValue().toUpperCase()).append(" » ").append(this.arrivalAirportCodeProperty().getValue().toUpperCase());
        if (StringUtils.isNotEmpty(this.seatsProperty().getValue())) {
            eventSummary.append(" @ ").append(this.seatsProperty().getValue());
        }
        return eventSummary.toString();
    }

    private String createEventDescription() {

        StringBuilder eventDescription = new StringBuilder();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy HH:mm ZZZZ").withLocale(Locale.GERMANY);
        ZonedDateTime departureDateTime = this.startDateProperty().getValue().atTime(this.startTimeProperty().getValue()).atZone(this.startZoneIdProperty().getValue());
        ZonedDateTime arrivalDateTime = this.endDateProperty().getValue().atTime(this.endTimeProperty().getValue()).atZone(this.endZoneIdProperty().getValue());

        eventDescription.append("<strong>Flug</strong>\n");
        eventDescription.append(this.airlineCodeProperty().getValue()).append(" ").append(this.flightNumberProperty().getValue());
        Airline airline = AirlineRepository.getInstance().loadAirlineByCode(this.airlineCodeProperty().getValue());
        if (airline != null) {
            eventDescription.append(" (").append(airline.getName()).append(")");
        }
        if (StringUtils.isNotEmpty(this.airplaneTypeProperty().getValue())) {
            eventDescription.append(" // ").append(this.airplaneTypeProperty().getValue());
        }

        eventDescription.append("\n\n<strong>Abflug</strong>\n").append(dateTimeFormatter.format(departureDateTime)).append(" (").append(departureDateTime.getZone()).append(")");
        eventDescription.append("\n").append(this.departureAirportCodeProperty().getValue()).append(" - ").append(this.departureAirportNameProperty().getValue());
        eventDescription.append("\n\n<strong>Ankunft</strong>\n").append(dateTimeFormatter.format(arrivalDateTime)).append(" (").append(arrivalDateTime.getZone()).append(")");
        eventDescription.append("\n").append(this.arrivalAirportCodeProperty().getValue()).append(" - ").append(this.arrivalAirportNameProperty().getValue());
        if (StringUtils.isNotEmpty(this.seatsProperty().getValue())) {
            eventDescription.append("\n\n<strong>Sitze</strong>\n");
            eventDescription.append(this.seatsProperty().getValue());
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

    private String createEventLocation() {
        StringBuilder eventLocation = new StringBuilder();
        if (StringUtils.isNotEmpty(this.departureAirportNameProperty().getValue())) {
            eventLocation.append(this.departureAirportNameProperty().getValue());
            Airport airport = AirportRepository.getInstance().loadAirportByCode(this.departureAirportCodeProperty().getValue());
            if (airport != null && StringUtils.isNotEmpty(airport.getCity())) {
                eventLocation.append(", ").append(airport.getCity());
            }
            if (airport != null && StringUtils.isNotEmpty(airport.getCountryCode())) {
                eventLocation.append(", ").append(airport.getCountryCode());
            }
        } else {
            if (StringUtils.isNotEmpty(this.seatsProperty().getValue())) {
                eventLocation.append("Sitze: ").append(this.seatsProperty().getValue());
            }
            if (StringUtils.isNotEmpty(this.airplaneTypeProperty().getValue())) {
                eventLocation.append(eventLocation.isEmpty() ? "" : " // ").append(this.airplaneTypeProperty().getValue());
            }
        }
        return eventLocation.toString();
    }

    private void afterAirportCodeUpdated(String newAirportCode, ObjectProperty<ZoneId> zoneIdProperty, StringProperty airportNameProperty) {
        Airport airport = AirportRepository.getInstance().loadAirportByCode(newAirportCode);
        zoneIdProperty.setValue(airport == null ? null : airport.getTimezoneId());
        airportNameProperty.setValue(airport == null ? null : airport.getName());
    }
    public StringProperty airlineCodeProperty() {
        return this.airlineCode;
    }

    public StringProperty flightNumberProperty() {
        return this.flightNumber;
    }

    public StringProperty departureAirportCodeProperty() {
        return this.departureAirportCode;
    }

    public StringProperty departureAirportNameProperty() {
        return this.departureAirportName;
    }

    public StringProperty arrivalAirportCodeProperty() {
        return this.arrivalAirportCode;
    }

    public StringProperty arrivalAirportNameProperty() {
        return this.arrivalAirportName;
    }

    public StringProperty airplaneTypeProperty() {
        return this.airplaneType;
    }

    public StringProperty seatsProperty() {
        return this.seats;
    }

    public StringProperty bookingCodeProperty() {
        return this.bookingCode;
    }

    public StringProperty commentsProperty() {
        return this.comments;
    }

}
