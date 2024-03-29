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

public class TrainRidePane extends GridPane {

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

        TextField departureStationField = new TextField();
        departureStationField.textProperty().bindBidirectional(trainRideItem.departureStationProperty());
        departureStationField.setPrefWidth(125);
        Label departureStationLabel = new Label("Departure");
        departureStationLabel.setPadding(new Insets(0, 5, 0, 0));
        departureStationLabel.setLabelFor(departureStationField);
        DateField departureDateField = new DateField(trainRideItem.startDateProperty());
        TimeField departureTimeField = new TimeField(trainRideItem.startTimeProperty());
        GridPane.setMargin(departureStationLabel, new Insets(0, 0, 0, 10));
        ComboBox<ZoneId> departureZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        departureZoneBox.setPrefWidth(125);
        departureZoneBox.valueProperty().bindBidirectional(trainRideItem.startZoneIdProperty());
        departureZoneBox.setFocusTraversable(false);
        TextField departureTrackField = new TextField();
        departureTrackField.setPromptText("Track");
        departureTrackField.textProperty().bindBidirectional(trainRideItem.departureTrackProperty());
        departureTrackField.setPrefWidth(50);

        TextField arrivalStationField = new TextField();
        arrivalStationField.textProperty().bindBidirectional(trainRideItem.arrivalStationProperty());
        arrivalStationField.setPrefWidth(125);
        Label arrivalStationLabel = new Label("Arrival");
        arrivalStationLabel.setPadding(new Insets(0, 5, 0, 0));
        arrivalStationLabel.setLabelFor(departureStationField);
        DateField arrivalDateField = new DateField(trainRideItem.endDateProperty());
        TimeField arrivalTimeField = new TimeField(trainRideItem.endTimeProperty());
        GridPane.setMargin(arrivalStationLabel, new Insets(0, 0, 0, 10));
        ComboBox<ZoneId> arrivalZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        arrivalZoneBox.setPrefWidth(125);
        arrivalZoneBox.valueProperty().bindBidirectional(trainRideItem.endZoneIdProperty());
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

        TextField bookingCodeField = new TextField();
        bookingCodeField.textProperty().bindBidirectional(trainRideItem.bookingCodeProperty());
        bookingCodeField.setPrefWidth(75);
        Label bookingCodeLabel = new Label("Code");
        bookingCodeLabel.setPadding(new Insets(0, 5, 0, 0));
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

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

        this.add(departureStationLabel, 3, 0, 1, 1);
        this.add(departureStationField, 4, 0, 1, 1);
        this.add(departureDateField, 5, 0, 1, 1);
        this.add(departureTimeField, 6, 0, 1, 1);
        this.add(departureZoneBox, 7, 0, 1, 1);
        this.add(departureTrackField, 8, 0, 1, 1);
        this.add(bookingCodeLabel, 9, 0, 1, 1);
        this.add(bookingCodeField, 10, 0, 1, 1);
        this.add(commentsLabel, 11, 0, 1, 1);
        this.add(commentsArea, 12, 0, 1, 2);

        this.add(reservationLabel, 0, 1, 1, 1);
        this.add(reservationWagonField, 1, 1, 1, 1);
        this.add(reservationSeatsField, 2, 1, 1, 1);

        this.add(arrivalStationLabel, 3, 1, 1, 1);
        this.add(arrivalStationField, 4, 1, 1, 1);
        this.add(arrivalDateField, 5, 1, 1, 1);
        this.add(arrivalTimeField, 6, 1, 1, 1);
        this.add(arrivalZoneBox, 7, 1, 1, 1);
        this.add(arrivalTrackField, 8, 1, 1, 1);

        this.add(buttonBox, 13, 0, 1, 1);

        this.setHgap(5);
        this.setVgap(2);

        commentsArea.addEventFilter(KeyEvent.KEY_PRESSED, new FocusTraversalKeyHandler(null, reservationWagonField));

    }

}
