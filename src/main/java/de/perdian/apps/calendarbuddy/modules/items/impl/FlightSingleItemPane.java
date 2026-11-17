package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.support.airtravel.AircraftType;
import de.perdian.apps.calendarbuddy.support.airtravel.AircraftTypeRepository;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import de.perdian.apps.calendarbuddy.support.components.DateField;
import de.perdian.apps.calendarbuddy.support.components.TimeField;
import de.perdian.apps.calendarbuddy.support.converters.ToUppercaseStringConverter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

class FlightSingleItemPane extends GridPane {

    FlightSingleItemPane(FlightSingleItem flightSingleItem, FlightJourneyItem flightJourneyItem) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label flightLabel = componentFactory.createLabel("Flight");
        flightLabel.setPrefWidth(80);
        TextField airlineField = componentFactory.createTextField(flightSingleItem.airlineCodeProperty(), new ToUppercaseStringConverter());
        airlineField.setPromptText("XX");
        airlineField.setPrefWidth(50);
        TextField numberField = componentFactory.createTextField(flightSingleItem.flightNumberProperty());
        numberField.setPromptText("0000");
        numberField.setPrefWidth(65);
        flightLabel.setLabelFor(airlineField);

        Label departureLabel = componentFactory.createLabel("Departure");
        departureLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField departureAirportCodeField = componentFactory.createTextField(flightSingleItem.departureAirportCodeProperty(), new ToUppercaseStringConverter());
        departureAirportCodeField.setPromptText("ABC");
        departureAirportCodeField.setPrefWidth(50);
        departureLabel.setLabelFor(departureAirportCodeField);
        DateField departureDateField = componentFactory.createDateField(flightSingleItem.startDateProperty());
        TimeField departureTimeField = componentFactory.createTimeField(flightSingleItem.startTimeProperty());
        TextField departureOffsetField = componentFactory.createReadOnlyTextField(flightSingleItem.startZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        departureOffsetField.setDisable(true);
        departureOffsetField.setPrefWidth(60);
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));

        Label arrivalLabel = componentFactory.createLabel("Arrival");
        arrivalLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField arrivalAirportCodeField = componentFactory.createTextField(flightSingleItem.arrivalAirportCodeProperty(), new ToUppercaseStringConverter());
        arrivalAirportCodeField.setPromptText("ABC");
        arrivalAirportCodeField.setPrefWidth(50);
        arrivalLabel.setLabelFor(arrivalAirportCodeField);
        DateField arrivalDateField = componentFactory.createDateField(flightSingleItem.endDateProperty());
        TimeField arrivalTimeField = componentFactory.createTimeField(flightSingleItem.endTimeProperty());
        TextField arrivalOffsetField = componentFactory.createReadOnlyTextField(flightSingleItem.endZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        arrivalOffsetField.setDisable(true);
        arrivalOffsetField.setPrefWidth(60);
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));

        Label seatsLabel = componentFactory.createLabel("Seats");
        seatsLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField seatsField = componentFactory.createTextField(flightSingleItem.seatsProperty(), new ToUppercaseStringConverter());
        seatsField.setPromptText("00A");
        seatsField.setPrefWidth(50);
        seatsLabel.setLabelFor(seatsField);
        GridPane.setMargin(seatsLabel, new Insets(0, 0, 0, 10));

        Label airplaneLabel = componentFactory.createLabel("Airplane");
        airplaneLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField airplaneField = componentFactory.createTextField(flightSingleItem.airplaneTypeProperty(), new AircraftTypeCodeToAircraftTypeNameConverter());
        airplaneField.setPrefWidth(0);
        airplaneField.setMaxWidth(Double.MAX_VALUE);
        airplaneLabel.setLabelFor(airplaneField);

        Label departureAirportNameLabel = componentFactory.createLabel("Airport");
        departureAirportNameLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField departureAirportNameField = componentFactory.createReadOnlyTextField(flightSingleItem.departureAirportNameProperty());
        departureAirportNameField.setDisable(true);
        departureAirportNameField.setMaxWidth(Double.MAX_VALUE);
        departureAirportNameLabel.setLabelFor(departureAirportNameField);
        GridPane.setMargin(departureAirportNameLabel, new Insets(0, 0, 0, 10));

        Label bookingCodeLabel = componentFactory.createLabel("Code");
        bookingCodeLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField bookingCodeField = componentFactory.createTextField(flightSingleItem.bookingCodeProperty(), new ToUppercaseStringConverter());
        bookingCodeField.setPromptText("ABC123");
        bookingCodeField.setPrefWidth(70);
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        Label arrivalAirportNameLabel = componentFactory.createLabel("Airport");
        arrivalAirportNameLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField arrivalAirportNameField = componentFactory.createReadOnlyTextField(flightSingleItem.arrivalAirportNameProperty());
        arrivalAirportNameField.setDisable(true);
        arrivalAirportNameField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setMargin(arrivalAirportNameLabel, new Insets(0, 0, 0, 10));

        this.add(flightLabel, 0, 0, 1, 1);
        this.add(airlineField, 1, 0, 1, 1);
        this.add(numberField, 2, 0, 1, 1);
        this.add(departureLabel, 3, 0, 1, 1);
        this.add(departureAirportCodeField, 4, 0, 1, 1);
        this.add(departureDateField, 5, 0, 1, 1);
        this.add(departureTimeField, 6, 0, 1, 1);
        this.add(departureOffsetField, 7, 0, 1, 1);
        this.add(arrivalLabel, 8, 0, 1, 1);
        this.add(arrivalAirportCodeField, 9, 0, 1, 1);
        this.add(arrivalDateField, 10, 0, 1, 1);
        this.add(arrivalTimeField, 11, 0, 1, 1);
        this.add(arrivalOffsetField, 12, 0, 1, 1);
        this.add(seatsLabel, 13, 0, 1, 1);
        this.add(seatsField, 14, 0, 1, 1);
        this.add(airplaneLabel, 0, 1, 1, 1);
        this.add(airplaneField, 1, 1, 2, 1);
        this.add(departureAirportNameLabel, 3, 1, 1, 1);
        this.add(departureAirportNameField, 4, 1, 4, 1);
        this.add(arrivalAirportNameLabel, 8, 1, 1, 1);
        this.add(arrivalAirportNameField, 9, 1, 4, 1);
        this.add(bookingCodeLabel, 13, 1, 1, 1);
        this.add(bookingCodeField, 14, 1, 1, 1);

        this.setHgap(5);
        this.setVgap(2);

        this.focusedProperty().addListener((_, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                airlineField.requestFocus();
            }
        });

    }

    private static class AircraftTypeCodeToAircraftTypeNameConverter extends StringConverter<String> {

        @Override
        public String toString(String string) {
            return string;
        }

        @Override
        public String fromString(String aircraftTypeString) {
            if (StringUtils.isEmpty(aircraftTypeString)) {
                return null;
            } else {
                AircraftType aircraftType = AircraftTypeRepository.getInstance().loadAircraftTypeByCode(aircraftTypeString);
                return aircraftType == null ? aircraftTypeString : aircraftType.getName();
            }
        }

    }

}
