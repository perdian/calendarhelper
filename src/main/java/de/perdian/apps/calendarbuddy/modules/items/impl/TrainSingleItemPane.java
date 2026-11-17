package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import de.perdian.apps.calendarbuddy.support.components.DateField;
import de.perdian.apps.calendarbuddy.support.components.TimeField;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.ZoneId;

class TrainSingleItemPane extends GridPane {

    TrainSingleItemPane(TrainSingleItem trainSingleItem, TrainJourneyItem trainJourneyItem) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label trainLabel = componentFactory.createLabel("Train");
        trainLabel.setPrefWidth(80);
        trainLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField typeField = componentFactory.createTextField(trainSingleItem.typeProperty());
        typeField.setPromptText("XX");
        typeField.setPrefWidth(50);
        TextField numberField = componentFactory.createTextField(trainSingleItem.numberProperty());
        numberField.setPromptText("0000");
        numberField.setPrefWidth(60);
        trainLabel.setLabelFor(typeField);

        Label departureLabel = componentFactory.createLabel("Departure");
        departureLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));
        TextField departureStationField = componentFactory.createTextField(trainSingleItem.departureStationProperty());
        departureStationField.setPromptText("Station");
        departureStationField.setPrefWidth(200);
        DateField departureDateField = componentFactory.createDateField(trainSingleItem.startDateProperty());
        TimeField departureTimeField = componentFactory.createTimeField(trainSingleItem.startTimeProperty());
        ComboBox<ZoneId> departureZoneBox = componentFactory.createComboBox(trainSingleItem.startZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        departureZoneBox.setPrefWidth(125);
        departureZoneBox.setFocusTraversable(false);
        TextField departureTrackField = componentFactory.createTextField(trainSingleItem.departureTrackProperty());
        departureTrackField.setPromptText("Track");
        departureTrackField.setPrefWidth(50);

        Label arrivalLabel = componentFactory.createLabel("Arrival");
        arrivalLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));
        TextField arrivalStationField = componentFactory.createTextField(trainSingleItem.arrivalStationProperty());
        arrivalStationField.setPromptText("Station");
        arrivalStationField.setPrefWidth(200);
        DateField arrivalDateField = componentFactory.createDateField(trainSingleItem.endDateProperty());
        TimeField arrivalTimeField = componentFactory.createTimeField(trainSingleItem.endTimeProperty());
        ComboBox<ZoneId> arrivalZoneBox = componentFactory.createComboBox(trainSingleItem.endZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        arrivalZoneBox.setPrefWidth(125);
        arrivalZoneBox.setFocusTraversable(false);
        TextField arrivalTrackField = componentFactory.createTextField(trainSingleItem.arrivalTrackProperty());
        arrivalTrackField.setPromptText("Track");
        arrivalTrackField.setPrefWidth(50);

        TextField reservationWagonField = componentFactory.createTextField(trainSingleItem.reservedWagonProperty());
        reservationWagonField.setPromptText("Wagon");
        reservationWagonField.setPrefWidth(55);
        TextField reservationSeatsField = componentFactory.createTextField(trainSingleItem.reservedSeatsProperty());
        reservationSeatsField.setPromptText("Seat(s)");
        reservationSeatsField.setPrefWidth(55);
        Label reservationLabel = componentFactory.createLabel("Reservation");
        reservationLabel.setPadding(new Insets(0, 5, 0, 0));
        reservationLabel.setLabelFor(reservationWagonField);

        TextField bookingCodeField = componentFactory.createTextField(trainSingleItem.bookingCodeProperty());
        bookingCodeField.setPrefWidth(100);
        Label bookingCodeLabel = componentFactory.createLabel("Code");
        bookingCodeLabel.setPadding(new Insets(0, 5, 0, 0));
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        this.add(trainLabel, 0, 0, 1, 1);
        this.add(typeField, 1, 0, 1, 1);
        this.add(numberField, 2, 0, 1, 1);

        this.add(departureLabel, 3, 0, 1, 1);
        this.add(departureDateField, 4, 0, 1, 1);
        this.add(departureTimeField, 5, 0, 1, 1);
        this.add(departureZoneBox, 6, 0, 1, 1);
        this.add(departureStationField, 7, 0, 1, 1);
        this.add(departureTrackField, 8, 0, 1, 1);
        this.add(bookingCodeLabel, 9, 0, 1, 1);
        this.add(bookingCodeField, 10, 0, 1, 1);

        this.add(reservationLabel, 0, 1, 1, 1);
        this.add(reservationWagonField, 1, 1, 1, 1);
        this.add(reservationSeatsField, 2, 1, 1, 1);

        this.add(arrivalLabel, 3, 1, 1, 1);
        this.add(arrivalDateField, 4, 1, 1, 1);
        this.add(arrivalTimeField, 5, 1, 1, 1);
        this.add(arrivalZoneBox, 6, 1, 1, 1);
        this.add(arrivalStationField, 7, 1, 1, 1);
        this.add(arrivalTrackField, 8, 1, 1, 1);

        this.setHgap(5);
        this.setVgap(2);

        this.focusedProperty().addListener((_, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                typeField.requestFocus();
            }
        });

    }

}
