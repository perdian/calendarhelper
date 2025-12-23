package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import de.perdian.apps.calendarhelper.support.fx.eventhandlers.keys.FocusTraversalKeyHandler;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.time.ZoneId;

class TrainRidePane extends GridPane {

    TrainRidePane(TrainRideItem trainRideItem, TrainJourneyItem trainJourneyItem, ComponentFactory componentFactory) {

        Label trainLabel = componentFactory.createLabel("Train");
        trainLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField typeField = componentFactory.createTextField(trainRideItem.typeProperty());
        typeField.setPromptText("XX");
        typeField.setPrefWidth(50);
        TextField numberField = componentFactory.createTextField(trainRideItem.numberProperty());
        numberField.setPromptText("0000");
        numberField.setPrefWidth(60);
        trainLabel.setLabelFor(typeField);

        Label departureLabel = componentFactory.createLabel("Departure");
        departureLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));
        TextField departureStationField = componentFactory.createTextField(trainRideItem.departureStationProperty());
        departureStationField.setPromptText("Station");
        departureStationField.setPrefWidth(150);
        DateField departureDateField = componentFactory.createDateField(trainRideItem.getCalendarValues().startDateProperty());
        TimeField departureTimeField = componentFactory.createTimeField(trainRideItem.getCalendarValues().startTimeProperty());
        ComboBox<ZoneId> departureZoneBox = componentFactory.createComboBox(trainRideItem.getCalendarValues().startZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        departureZoneBox.setPrefWidth(125);
        departureZoneBox.setFocusTraversable(false);
        TextField departureTrackField = componentFactory.createTextField(trainRideItem.departureTrackProperty());
        departureTrackField.setPromptText("Track");
        departureTrackField.setPrefWidth(50);

        Label arrivalLabel = componentFactory.createLabel("Arrival");
        arrivalLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));
        TextField arrivalStationField = componentFactory.createTextField(trainRideItem.arrivalStationProperty());
        arrivalStationField.setPromptText("Station");
        arrivalStationField.setPrefWidth(150);
        DateField arrivalDateField = componentFactory.createDateField(trainRideItem.getCalendarValues().endDateProperty());
        TimeField arrivalTimeField = componentFactory.createTimeField(trainRideItem.getCalendarValues().endTimeProperty());
        ComboBox<ZoneId> arrivalZoneBox = componentFactory.createComboBox(trainRideItem.getCalendarValues().endZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        arrivalZoneBox.setPrefWidth(125);
        arrivalZoneBox.setFocusTraversable(false);
        TextField arrivalTrackField = componentFactory.createTextField(trainRideItem.arrivalTrackProperty());
        arrivalTrackField.setPromptText("Track");
        arrivalTrackField.setPrefWidth(50);

        TextField reservationWagonField = componentFactory.createTextField(trainRideItem.reservedWagonProperty());
        reservationWagonField.setPromptText("Wagon");
        reservationWagonField.setPrefWidth(55);
        TextField reservationSeatsField = componentFactory.createTextField(trainRideItem.reservedSeatsProperty());
        reservationSeatsField.setPromptText("Seat(s)");
        reservationSeatsField.setPrefWidth(55);
        Label reservationLabel = componentFactory.createLabel("Reservation");
        reservationLabel.setPadding(new Insets(0, 5, 0, 0));
        reservationLabel.setLabelFor(reservationWagonField);

        TextField bookingCodeField = componentFactory.createTextField(trainRideItem.bookingCodeProperty());
        bookingCodeField.setPrefWidth(100);
        Label bookingCodeLabel = componentFactory.createLabel("Code");
        bookingCodeLabel.setPadding(new Insets(0, 5, 0, 0));
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        TextArea commentsArea = componentFactory.createTextArea(trainRideItem.commentsProperty());
        commentsArea.setPrefWidth(0);
        commentsArea.setPrefHeight(0);
        GridPane.setHgrow(commentsArea, Priority.ALWAYS);
        GridPane.setVgrow(commentsArea, Priority.ALWAYS);
        Label commentsLabel = componentFactory.createLabel("Comments");
        commentsLabel.setPadding(new Insets(0, 5, 0, 0));
        commentsLabel.setLabelFor(commentsArea);
        GridPane.setMargin(commentsLabel, new Insets(0, 0, 0, 10));

        Button removeButton = componentFactory.createButton(new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.disableProperty().bind(Bindings.size(trainJourneyItem.getChildren()).lessThanOrEqualTo(1));
        removeButton.setOnAction(_ -> trainJourneyItem.getChildren().remove(trainRideItem));
        removeButton.setFocusTraversable(false);
        HBox buttonBox = new HBox(2);
        buttonBox.getChildren().add(removeButton);
        GridPane.setMargin(buttonBox, new Insets(0, 0, 0, 10));

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
        this.add(commentsLabel, 11, 0, 1, 1);
        this.add(commentsArea, 12, 0, 1, 2);

        this.add(reservationLabel, 0, 1, 1, 1);
        this.add(reservationWagonField, 1, 1, 1, 1);
        this.add(reservationSeatsField, 2, 1, 1, 1);

        this.add(arrivalLabel, 3, 1, 1, 1);
        this.add(arrivalDateField, 4, 1, 1, 1);
        this.add(arrivalTimeField, 5, 1, 1, 1);
        this.add(arrivalZoneBox, 6, 1, 1, 1);
        this.add(arrivalStationField, 7, 1, 1, 1);
        this.add(arrivalTrackField, 8, 1, 1, 1);

        this.add(buttonBox, 13, 0, 1, 1);

        this.setHgap(5);
        this.setVgap(2);

        commentsArea.addEventFilter(KeyEvent.KEY_PRESSED, new FocusTraversalKeyHandler(null, reservationWagonField));

        this.focusedProperty().addListener((_, oldValue, newValue) -> {
           if (!oldValue && newValue) {
               typeField.requestFocus();
           }
        });

    }

}
