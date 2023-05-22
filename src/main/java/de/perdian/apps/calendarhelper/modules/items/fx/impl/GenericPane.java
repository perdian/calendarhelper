package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.model.impl.GenericItem;
import de.perdian.apps.calendarhelper.modules.items.model.types.Availability;
import de.perdian.apps.calendarhelper.modules.items.model.types.Type;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import de.perdian.apps.calendarhelper.support.fx.components.TimeField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class GenericPane extends GridPane {

    public GenericPane(GenericItem item) {

        ObservableList<Type> typeValues = FXCollections.observableArrayList(Type.values());
        typeValues.add(0, null);
        Label typeLabel = new Label("Type");
        ComboBox<Type> typeBox = new ComboBox<>(typeValues);
        typeBox.valueProperty().bindBidirectional(item.typeProperty());
        typeBox.setMaxWidth(Double.MAX_VALUE);

        Label startLabel = new Label("Start");
        DateField startDateField = new DateField(item.startDateProperty());
        startLabel.setLabelFor(startDateField);
        TimeField startTimeField = new TimeField(item.startTimeProperty());
        startTimeField.disableProperty().bind(item.fullDayProperty());

        CheckBox fullDayBox = new CheckBox("Full day");
        fullDayBox.selectedProperty().bindBidirectional(item.fullDayProperty());
        GridPane.setMargin(fullDayBox, new Insets(0, 0, 0, 10));

        Label endLabel = new Label("End");
        DateField endDateField = new DateField(item.endDateProperty());
        endLabel.setLabelFor(endDateField);
        TimeField endTimeField = new TimeField(item.endTimeProperty());
        endTimeField.disableProperty().bind(item.fullDayProperty());

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
        this.add(typeBox, 1, 0, 3, 1);
        this.add(startLabel, 0, 1, 1, 1);
        this.add(startDateField, 1, 1, 1, 1);
        this.add(startTimeField, 2, 1, 1, 1);
        this.add(fullDayBox, 3, 1, 1, 1);
        this.add(endLabel, 0, 2, 1, 1);
        this.add(endDateField, 1, 2, 1, 1);
        this.add(endTimeField, 2, 2, 1, 1);
        this.add(availabilityLabel, 0, 3, 1, 1);
        this.add(availabilityBox, 1, 3, 3, 1);

        this.add(summaryLabel, 4, 0, 1, 1);
        this.add(summaryField, 5, 0, 1, 1);
        this.add(locationLabel, 4, 1, 1, 1);
        this.add(locationField, 5, 1, 1, 1);
        this.add(descriptionLabel, 4, 2, 1, 1);
        this.add(descriptionArea, 5, 2, 1, 3);

        this.setHgap(5);
        this.setVgap(5);

    }

}
