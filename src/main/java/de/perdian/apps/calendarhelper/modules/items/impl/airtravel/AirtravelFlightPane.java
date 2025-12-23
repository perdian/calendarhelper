package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.support.airtravel.AircraftType;
import de.perdian.apps.calendarhelper.support.airtravel.AircraftTypeRepository;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import de.perdian.apps.calendarhelper.support.fx.converters.ToUppercaseStringConverter;
import de.perdian.apps.calendarhelper.support.fx.eventhandlers.keys.FocusTraversalKeyHandler;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

class AirtravelFlightPane extends GridPane {

    AirtravelFlightPane(AirtravelFlightItem airtravelFlightItem, AirtravelJourneyItem airtravelJourneyItem, ComponentFactory componentFactory) {

        Label flightLabel = componentFactory.createLabel("Flight");
        TextField airlineField = componentFactory.createTextField(airtravelFlightItem.airlineCodeProperty(), new ToUppercaseStringConverter());
        airlineField.setPromptText("XX");
        airlineField.setPrefWidth(40);
        TextField numberField = componentFactory.createTextField(airtravelFlightItem.flightNumberProperty());
        numberField.setPromptText("0000");
        numberField.setPrefWidth(55);
        flightLabel.setLabelFor(airlineField);

        Label departureLabel = componentFactory.createLabel("Departure");
        TextField departureAirportCodeField = componentFactory.createTextField(airtravelFlightItem.departureAirportCodeProperty(), new ToUppercaseStringConverter());
        departureAirportCodeField.setPromptText("ABC");
        departureAirportCodeField.setPrefWidth(50);
        departureLabel.setLabelFor(departureAirportCodeField);
        DateField departureDateField = componentFactory.createDateField(airtravelFlightItem.getCalendarValues().startDateProperty());
        TimeField departureTimeField = componentFactory.createTimeField(airtravelFlightItem.getCalendarValues().startTimeProperty());
        TextField departureOffsetField = componentFactory.createReadOnlyTextField(airtravelFlightItem.getCalendarValues().startZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        departureOffsetField.setDisable(true);
        departureOffsetField.setPrefWidth(60);
        GridPane.setMargin(departureLabel, new Insets(0, 0, 0, 10));

        Label arrivalLabel = componentFactory.createLabel("Arrival");
        TextField arrivalAirportCodeField = componentFactory.createTextField(airtravelFlightItem.arrivalAirportCodeProperty(), new ToUppercaseStringConverter());
        arrivalAirportCodeField.setPromptText("ABC");
        arrivalAirportCodeField.setPrefWidth(50);
        arrivalLabel.setLabelFor(arrivalAirportCodeField);
        DateField arrivalDateField = componentFactory.createDateField(airtravelFlightItem.getCalendarValues().endDateProperty());
        TimeField arrivalTimeField = componentFactory.createTimeField(airtravelFlightItem.getCalendarValues().endTimeProperty());
        TextField arrivalOffsetField = componentFactory.createReadOnlyTextField(airtravelFlightItem.getCalendarValues().endZoneOffsetProperty().map(offset -> offset == null ? "" : offset.toString()));
        arrivalOffsetField.setDisable(true);
        arrivalOffsetField.setPrefWidth(60);
        GridPane.setMargin(arrivalLabel, new Insets(0, 0, 0, 10));

        Label seatsLabel = componentFactory.createLabel("Seats");
        TextField seatsField = componentFactory.createTextField(airtravelFlightItem.seatsProperty(), new ToUppercaseStringConverter());
        seatsField.setPromptText("00A");
        seatsField.setPrefWidth(50);
        seatsLabel.setLabelFor(seatsField);
        GridPane.setMargin(seatsLabel, new Insets(0, 0, 0, 10));

        Label commentsLabel = componentFactory.createLabel("Comments");
        TextArea commentsArea = componentFactory.createTextArea(airtravelFlightItem.commentsProperty());
        commentsArea.setPrefSize(0, 0);
        commentsArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        commentsLabel.setLabelFor(commentsArea);
        GridPane.setHgrow(commentsArea, Priority.ALWAYS);
        GridPane.setMargin(commentsLabel, new Insets(0, 0, 0, 10));

        Label airplaneLabel = componentFactory.createLabel("Airplane");
        TextField airplaneField = componentFactory.createTextField(airtravelFlightItem.airplaneTypeProperty(), new AircraftTypeCodeToAircraftTypeNameConverter());
        airplaneField.setPrefWidth(0);
        airplaneField.setMaxWidth(Double.MAX_VALUE);
        airplaneLabel.setLabelFor(airplaneField);

        Label departureAirportNameLabel = componentFactory.createLabel("Airport");
        TextField departureAirportNameField = componentFactory.createReadOnlyTextField(airtravelFlightItem.departureAirportNameProperty());
        departureAirportNameField.setDisable(true);
        departureAirportNameField.setMaxWidth(Double.MAX_VALUE);
        departureAirportNameLabel.setLabelFor(departureAirportNameField);
        GridPane.setMargin(departureAirportNameLabel, new Insets(0, 0, 0, 10));

        Label bookingCodeLabel = componentFactory.createLabel("Code");
        TextField bookingCodeField = componentFactory.createTextField(airtravelFlightItem.bookingCodeProperty(), new ToUppercaseStringConverter());
        bookingCodeField.setPromptText("ABC123");
        bookingCodeField.setPrefWidth(70);
        bookingCodeLabel.setLabelFor(bookingCodeField);
        GridPane.setMargin(bookingCodeLabel, new Insets(0, 0, 0, 10));

        Label arrivalAirportNameLabel = componentFactory.createLabel("Airport");
        TextField arrivalAirportNameField = componentFactory.createReadOnlyTextField(airtravelFlightItem.arrivalAirportNameProperty());
        arrivalAirportNameField.setDisable(true);
        arrivalAirportNameField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setMargin(arrivalAirportNameLabel, new Insets(0, 0, 0, 10));

        Button removeButton = componentFactory.createButton(new FontIcon(MaterialDesignT.TRASH_CAN));
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

        commentsArea.addEventFilter(KeyEvent.KEY_PRESSED, new FocusTraversalKeyHandler(null, airplaneField));

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
