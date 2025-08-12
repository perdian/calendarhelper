package de.perdian.apps.calendarhelper.modules.items.impl.train;

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

    TrainRidePane(TrainRideItem trainRideItem, TrainJourneyItem trainJourneyItem) {

        TextField typeField = new TextField();
        typeField.setPromptText("XX");
        typeField.textProperty().bindBidirectional(trainRideItem.typeProperty());
        typeField.setPrefWidth(50);
        TextField numberField = new TextField();
        numberField.setPromptText("0000");
        numberField.setPrefWidth(60);
        numberField.textProperty().bindBidirectional(trainRideItem.numberProperty());
        Label trainLabel = new Label("Train");
        trainLabel.setPadding(new Insets(0, 5, 0, 0));
        trainLabel.setLabelFor(typeField);

        Label departureLabel = new Label("Departure");
        departureLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));
        TextField departureStationField = new TextField();
        departureStationField.setPromptText("Station");
        departureStationField.textProperty().bindBidirectional(trainRideItem.departureStationProperty());
        departureStationField.setPrefWidth(150);
        DateField departureDateField = new DateField(trainRideItem.getCalendarValues().startDateProperty());
        TimeField departureTimeField = new TimeField(trainRideItem.getCalendarValues().startTimeProperty());
        ComboBox<ZoneId> departureZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        departureZoneBox.setPrefWidth(125);
        departureZoneBox.valueProperty().bindBidirectional(trainRideItem.getCalendarValues().startZoneIdProperty());
        departureZoneBox.setFocusTraversable(false);
        TextField departureTrackField = new TextField();
        departureTrackField.setPromptText("Track");
        departureTrackField.textProperty().bindBidirectional(trainRideItem.departureTrackProperty());
        departureTrackField.setPrefWidth(50);

        Label arrivalLabel = new Label("Arrival");
        arrivalLabel.setPadding(new Insets(0, 5, 0, 0));
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));
        TextField arrivalStationField = new TextField();
        arrivalStationField.setPromptText("Station");
        arrivalStationField.textProperty().bindBidirectional(trainRideItem.arrivalStationProperty());
        arrivalStationField.setPrefWidth(150);
        DateField arrivalDateField = new DateField(trainRideItem.getCalendarValues().endDateProperty());
        TimeField arrivalTimeField = new TimeField(trainRideItem.getCalendarValues().endTimeProperty());
        ComboBox<ZoneId> arrivalZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        arrivalZoneBox.setPrefWidth(125);
        arrivalZoneBox.valueProperty().bindBidirectional(trainRideItem.getCalendarValues().endZoneIdProperty());
        arrivalZoneBox.setFocusTraversable(false);
        TextField arrivalTrackField = new TextField();
        arrivalTrackField.setPromptText("Track");
        arrivalTrackField.textProperty().bindBidirectional(trainRideItem.arrivalTrackProperty());
        arrivalTrackField.setPrefWidth(50);

        TextField reservationWagonField = new TextField();
        reservationWagonField.setPromptText("Wagon");
        reservationWagonField.textProperty().bindBidirectional(trainRideItem.reservedWagonProperty());
        reservationWagonField.setPrefWidth(55);
        TextField reservationSeatsField = new TextField();
        reservationSeatsField.setPromptText("Seat(s)");
        reservationSeatsField.textProperty().bindBidirectional(trainRideItem.reservedSeatsProperty());
        reservationSeatsField.setPrefWidth(55);
        Label reservationLabel = new Label("Reservation");
        reservationLabel.setPadding(new Insets(0, 5, 0, 0));
        reservationLabel.setLabelFor(reservationWagonField);

        TextField bookingCodeField = new TextField();
        bookingCodeField.textProperty().bindBidirectional(trainRideItem.bookingCodeProperty());
        bookingCodeField.setPrefWidth(100);
        Label bookingCodeLabel = new Label("Code");
        bookingCodeLabel.setPadding(new Insets(0, 5, 0, 0));
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        TextArea commentsArea = new TextArea();
        commentsArea.setPrefWidth(0);
        commentsArea.setPrefHeight(0);
        commentsArea.textProperty().bindBidirectional(trainRideItem.commentsProperty());
        GridPane.setHgrow(commentsArea, Priority.ALWAYS);
        GridPane.setVgrow(commentsArea, Priority.ALWAYS);
        Label commentsLabel = new Label("Comments");
        commentsLabel.setPadding(new Insets(0, 5, 0, 0));
        commentsLabel.setLabelFor(commentsArea);
        GridPane.setMargin(commentsLabel, new Insets(0, 0, 0, 10));

        Button removeButton = new Button("", new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.disableProperty().bind(Bindings.size(trainJourneyItem.getChildren()).lessThanOrEqualTo(1));
        removeButton.setOnAction(event -> trainJourneyItem.getChildren().remove(trainRideItem));
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

    }

}
