package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.model.impl.AirtravelFlightItem;
import de.perdian.apps.calendarhelper.modules.items.model.impl.AirtravelJourneyItem;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import de.perdian.apps.calendarhelper.support.fx.converters.ToUppercaseStringConverter;
import de.perdian.apps.calendarhelper.support.fx.eventhandlers.keys.FocusTraversalKeyHandler;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class AirtravelFlightPane extends GridPane {

    AirtravelFlightPane(AirtravelFlightItem airtravelFlightItem, AirtravelJourneyItem airtravelJourneyItem) {

        Label flightLabel = new Label("Flight");
        TextFormatter<String> airlineCodeFormatter = new TextFormatter<>(new ToUppercaseStringConverter());
        airlineCodeFormatter.valueProperty().bindBidirectional(airtravelFlightItem.airlineCodeProperty());
        TextField airlineField = new TextField();
        airlineField.setPromptText("XX");
        airlineField.setTextFormatter(airlineCodeFormatter);
        airlineField.setPrefWidth(40);
        TextField numberField = new TextField();
        numberField.setPromptText("0000");
        numberField.setPrefWidth(55);
        numberField.textProperty().bindBidirectional(airtravelFlightItem.flightNumberProperty());
        flightLabel.setLabelFor(airlineField);

        Label departureLabel = new Label("Departure");
        TextField departureAirportCodeField = new TextField();
        departureAirportCodeField.textProperty().bindBidirectional(airtravelFlightItem.departureAirportCodeProperty());
        departureAirportCodeField.setPromptText("ABC");
        departureAirportCodeField.setPrefWidth(50);
        departureLabel.setLabelFor(departureAirportCodeField);
        DateField departureDateField = new DateField(airtravelFlightItem.startDateProperty());
        TimeField departureTimeField = new TimeField(airtravelFlightItem.startTimeProperty());
        TextField departureOffsetField = new TextField();
        departureOffsetField.textProperty().bind(airtravelFlightItem.startZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        departureOffsetField.setDisable(true);
        departureOffsetField.setPrefWidth(60);
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));

        Label arrivalLabel = new Label("Arrival");
        TextField arrivalAirportCodeField = new TextField();
        arrivalAirportCodeField.textProperty().bindBidirectional(airtravelFlightItem.arrivalAirportCodeProperty());
        arrivalAirportCodeField.setPromptText("ABC");
        arrivalAirportCodeField.setPrefWidth(50);
        arrivalLabel.setLabelFor(arrivalAirportCodeField);
        DateField arrivalDateField = new DateField(airtravelFlightItem.endDateProperty());
        TimeField arrivalTimeField = new TimeField(airtravelFlightItem.endTimeProperty());
        TextField arrivalOffsetField = new TextField();
        arrivalOffsetField.textProperty().bind(airtravelFlightItem.endZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        arrivalOffsetField.setDisable(true);
        arrivalOffsetField.setPrefWidth(60);
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));

        Label seatsLabel = new Label("Seats");
        TextField seatsField = new TextField();
        seatsField.textProperty().bindBidirectional(airtravelFlightItem.seatsProperty());
        seatsField.setPromptText("00A");
        seatsField.setPrefWidth(50);
        seatsLabel.setLabelFor(seatsField);
        GridPane.setMargin(seatsLabel, new Insets(0, 0, 0, 10));

        Label commentsLabel = new Label("Comments");
        TextArea commentsArea = new TextArea();
        commentsArea.textProperty().bindBidirectional(airtravelFlightItem.commentsProperty());
        commentsArea.setPrefSize(0, 0);
        commentsArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        commentsLabel.setLabelFor(commentsArea);
        GridPane.setHgrow(commentsArea, Priority.ALWAYS);
        GridPane.setMargin(commentsLabel, new Insets(0, 0, 0, 10));

        Label airplaneLabel = new Label("Airplane");
        TextField airplaneField = new TextField();
        airplaneField.setPrefWidth(0);
        airplaneField.setMaxWidth(Double.MAX_VALUE);
        airplaneField.textProperty().bindBidirectional(airtravelFlightItem.airplaneTypeProperty());
        airplaneLabel.setLabelFor(airplaneField);

        Label departureAirportNameLabel = new Label("Airport");
        TextField departureAirportNameField = new TextField();
        departureAirportNameField.textProperty().bind(airtravelFlightItem.departureAirportNameProperty());
        departureAirportNameField.setDisable(true);
        departureAirportNameField.setMaxWidth(Double.MAX_VALUE);
        departureAirportNameLabel.setLabelFor(departureAirportNameField);
        GridPane.setMargin(departureAirportNameLabel, new Insets(0, 0, 0, 10));

        TextFormatter<String> bookingCodeFormatter = new TextFormatter<>(new ToUppercaseStringConverter());
        bookingCodeFormatter.valueProperty().bindBidirectional(airtravelFlightItem.bookingCodeProperty());
        Label bookingCodeLabel = new Label("Code");
        TextField bookingCodeField = new TextField();
        bookingCodeField.setTextFormatter(bookingCodeFormatter);
        bookingCodeField.setPromptText("ABC123");
        bookingCodeField.setPrefWidth(70);
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        Label arrivalAirportNameLabel = new Label("Airport");
        TextField arrivalAirportNameField = new TextField();
        arrivalAirportNameField.textProperty().bind(airtravelFlightItem.arrivalAirportNameProperty());
        arrivalAirportNameField.setDisable(true);
        arrivalAirportNameField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setMargin(arrivalAirportNameLabel, new Insets(0, 0, 0, 10));

        Button removeButton = new Button("", new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.disableProperty().bind(Bindings.size(airtravelJourneyItem.getChildren()).lessThanOrEqualTo(1));
        removeButton.setOnAction(event -> airtravelJourneyItem.getChildren().remove(airtravelFlightItem));
        removeButton.setFocusTraversable(false);
        HBox buttonBox = new HBox(2);
        buttonBox.getChildren().add(removeButton);
        GridPane.setMargin(buttonBox, new Insets(0, 0, 0, 10));

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
        this.add(commentsLabel, 15, 0, 1, 1);
        this.add(commentsArea, 16, 0, 1, 2);
        this.add(buttonBox, 17, 0, 1, 1);
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

        commentsArea.addEventFilter(KeyEvent.KEY_PRESSED, new FocusTraversalKeyHandler(seatsField, airplaneField));

    }

}
