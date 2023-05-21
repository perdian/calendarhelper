package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainJourneyItem;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainRideItem;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class TrainRidePane extends GridPane {

    TrainRidePane(TrainRideItem trainRideItem, TrainJourneyItem trainJourneyItem) {

        Label numberLabel = new Label("Number");
        TextField typeField = new TextField();
        typeField.setPromptText("XX");
        typeField.textProperty().bindBidirectional(trainRideItem.typeProperty());
        typeField.setPrefWidth(40);
        TextField numberField = new TextField();
        numberField.setPromptText("0000");
        numberField.setPrefWidth(55);
        numberField.textProperty().bindBidirectional(trainRideItem.numberProperty());
        numberLabel.setLabelFor(typeField);

        Label departureStationLabel = new Label("Departure");
        TextField departureStationField = new TextField();
        departureStationField.textProperty().bindBidirectional(trainRideItem.departureStationProperty());
        departureStationField.setPrefWidth(125);
        departureStationLabel.setLabelFor(departureStationField);
        DateField departureDateField = new DateField(trainRideItem.startDateProperty());
        TimeField departureTimeField = new TimeField(trainRideItem.startTimeProperty());
        GridPane.setMargin(departureStationLabel, new Insets(0, 0, 0, 10));

        Label arrivalStationLabel = new Label("Arrival");
        TextField arrivalStationField = new TextField();
        arrivalStationField.textProperty().bindBidirectional(trainRideItem.arrivalStationProperty());
        arrivalStationField.setPrefWidth(125);
        arrivalStationLabel.setLabelFor(departureStationField);
        DateField arrivalDateField = new DateField(trainRideItem.endDateProperty());
        TimeField arrivalTimeField = new TimeField(trainRideItem.endTimeProperty());
        GridPane.setMargin(arrivalStationLabel, new Insets(0, 0, 0, 10));

        Label reservationLabel = new Label("Reservation");
        TextField reservationWagonField = new TextField();
        reservationWagonField.setPromptText("Wagon");
        reservationWagonField.textProperty().bindBidirectional(trainRideItem.reservedWagonProperty());
        reservationWagonField.setPrefWidth(55);
        TextField reservationSeatsField = new TextField();
        reservationSeatsField.setPromptText("Seat(s)");
        reservationSeatsField.textProperty().bindBidirectional(trainRideItem.reservedSeatsProperty());
        reservationSeatsField.setPrefWidth(55);
        reservationLabel.setLabelFor(reservationWagonField);
        GridPane.setMargin(reservationLabel, new Insets(0, 0, 0, 10));

        Label commentLabel = new Label("Comment");
        TextField commentField = new TextField();
        commentField.setPrefWidth(0);
        commentField.textProperty().bindBidirectional(trainRideItem.commentProperty());
        GridPane.setHgrow(commentField, Priority.ALWAYS);
        GridPane.setMargin(commentLabel, new Insets(0, 0, 0, 10));

        Button removeButton = new Button("", new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.disableProperty().bind(Bindings.size(trainJourneyItem.getChildren()).lessThanOrEqualTo(1));
        removeButton.setOnAction(event -> trainJourneyItem.getChildren().remove(trainRideItem));
        HBox buttonBox = new HBox(2);
        buttonBox.getChildren().add(removeButton);
        GridPane.setMargin(buttonBox, new Insets(0, 0, 0, 10));

        this.add(numberLabel, 0, 0, 1, 1);
        this.add(typeField, 1, 0, 1, 1);
        this.add(numberField, 2, 0, 1, 1);
        this.add(departureStationLabel, 3, 0, 1, 1);
        this.add(departureStationField, 4, 0, 1, 1);
        this.add(departureDateField, 5, 0, 1, 1);
        this.add(departureTimeField, 6, 0, 1, 1);
        this.add(arrivalStationLabel, 7, 0, 1, 1);
        this.add(arrivalStationField, 8, 0, 1, 1);
        this.add(arrivalDateField, 9, 0, 1, 1);
        this.add(arrivalTimeField, 10, 0, 1, 1);
        this.add(reservationLabel, 11, 0, 1, 1);
        this.add(reservationWagonField, 12, 0, 1, 1);
        this.add(reservationSeatsField, 13, 0, 1, 1);
        this.add(commentLabel, 14, 0, 1, 1);
        this.add(commentField, 15, 0, 1, 1);
        this.add(buttonBox, 16, 0, 1, 1);

        this.setHgap(5);

    }

}
