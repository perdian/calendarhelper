package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import de.perdian.apps.calendarhelper.modules.items.support.types.Availability;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.ZoneId;

public class GenericPane extends GridPane {

    public GenericPane(GenericItem item) {

        ObservableList<GenericType> typeValues = FXCollections.observableArrayList(GenericType.values());
        typeValues.add(0, null);
        Label typeLabel = new Label("Type");
        ComboBox<GenericType> typeBox = new ComboBox<>(typeValues);
        typeBox.valueProperty().bindBidirectional(item.typeProperty());
        typeBox.setMaxWidth(Double.MAX_VALUE);

        Label startLabel = new Label("Start");
        DateField startDateField = new DateField(item.startDateProperty());
        startLabel.setLabelFor(startDateField);
        TimeField startTimeField = new TimeField(item.startTimeProperty());
        startTimeField.disableProperty().bind(item.fullDayProperty());
        ComboBox<ZoneId> startZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        startZoneBox.setPrefWidth(150);
        startZoneBox.valueProperty().bindBidirectional(item.startZoneIdProperty());
        startZoneBox.disableProperty().bind(item.fullDayProperty());

        CheckBox fullDayBox = new CheckBox("Full day");
        fullDayBox.selectedProperty().bindBidirectional(item.fullDayProperty());
        GridPane.setMargin(fullDayBox, new Insets(0, 0, 0, 10));

        Label endLabel = new Label("End");
        DateField endDateField = new DateField(item.endDateProperty());
        endLabel.setLabelFor(endDateField);
        TimeField endTimeField = new TimeField(item.endTimeProperty());
        endTimeField.disableProperty().bind(item.fullDayProperty());
        ComboBox<ZoneId> endZoneBox = new ComboBox<>(FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        endZoneBox.setPrefWidth(150);
        endZoneBox.valueProperty().bindBidirectional(item.endZoneIdProperty());
        endZoneBox.disableProperty().bind(item.fullDayProperty());

        Label summaryLabel = new Label("Summary");
        TextField summaryField = new TextField();
        summaryField.textProperty().bindBidirectional(item.summaryProperty());
        summaryLabel.setLabelFor(summaryField);
        GridPane.setMargin(summaryLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(summaryField, Priority.ALWAYS);

        Label locationLabel = new Label("Location");
        TextField locationField = new TextField();
        locationField.textProperty().bindBidirectional(item.locationProperty());
        locationLabel.setLabelFor(locationField);
        GridPane.setMargin(locationLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(locationField, Priority.ALWAYS);

        Label availabilityLabel = new Label("Availability");
        ComboBox<Availability> availabilityBox = new ComboBox<>(FXCollections.observableArrayList(Availability.values()));
        availabilityBox.valueProperty().bindBidirectional(item.availabilityProperty());
        availabilityBox.setMaxWidth(Double.MAX_VALUE);
        availabilityLabel.setLabelFor(availabilityBox);

        Label descriptionLabel = new Label("Description");
        TextArea descriptionArea = new TextArea();
        descriptionArea.textProperty().bindBidirectional(item.descriptionProperty());
        descriptionArea.setPrefHeight(60);
        descriptionLabel.setLabelFor(descriptionArea);
        GridPane.setMargin(descriptionLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(descriptionArea, Priority.ALWAYS);

        this.add(typeLabel, 0, 0, 1, 1);
        this.add(typeBox, 1, 0, 4, 1);
        this.add(startLabel, 0, 1, 1, 1);
        this.add(startDateField, 1, 1, 1, 1);
        this.add(startTimeField, 2, 1, 1, 1);
        this.add(startZoneBox, 3, 1, 1, 1);
        this.add(fullDayBox, 4, 1, 1, 1);
        this.add(endLabel, 0, 2, 1, 1);
        this.add(endDateField, 1, 2, 1, 1);
        this.add(endTimeField, 2, 2, 1, 1);
        this.add(endZoneBox, 3, 2, 1, 1);
        this.add(availabilityLabel, 0, 3, 1, 1);
        this.add(availabilityBox, 1, 3, 4, 1);

        this.add(summaryLabel, 5, 0, 1, 1);
        this.add(summaryField, 6, 0, 1, 1);
        this.add(locationLabel, 5, 1, 1, 1);
        this.add(locationField, 6, 1, 1, 1);
        this.add(descriptionLabel, 5, 2, 1, 1);
        this.add(descriptionArea, 6, 2, 1, 3);

        this.setHgap(5);
        this.setVgap(5);

    }

}
