package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import de.perdian.apps.calendarbuddy.support.components.DateField;
import de.perdian.apps.calendarbuddy.support.components.TimeField;
import de.perdian.apps.calendarbuddy.support.types.CalendarAvailability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.ZoneId;

class GenericSingleItemPane extends GridPane {

    GenericSingleItemPane(GenericSingleItem genericSingleItem) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label typeLabel = componentFactory.createLabel("Type");
        typeLabel.setPrefWidth(80);
        ObservableList<GenericSingleItemType> typeValues = FXCollections.observableArrayList(GenericSingleItemType.values());
        typeValues.add(0, null);
        ComboBox<GenericSingleItemType> typeBox = componentFactory.createComboBox(genericSingleItem.typeProperty(), typeValues);
        typeBox.setMaxWidth(Double.MAX_VALUE);

        Label startLabel = componentFactory.createLabel("Start");
        startLabel.setPadding(new Insets(0, 5, 0, 0));
        DateField startDateField = componentFactory.createDateField(genericSingleItem.startDateProperty());
        startLabel.setLabelFor(startDateField);
        TimeField startTimeField = componentFactory.createTimeField(genericSingleItem.startTimeProperty());
        startTimeField.disableProperty().bind(genericSingleItem.fullDayProperty());
        ComboBox<ZoneId> startZoneBox = componentFactory.createComboBox(genericSingleItem.startZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        startZoneBox.setPrefWidth(150);
        startZoneBox.disableProperty().bind(genericSingleItem.fullDayProperty());

        CheckBox fullDayBox = componentFactory.createCheckBox("Full day", genericSingleItem.fullDayProperty());
        GridPane.setMargin(fullDayBox, new Insets(0, 0, 0, 10));

        Label endLabel = componentFactory.createLabel("End");
        endLabel.setPadding(new Insets(0, 5, 0, 0));
        DateField endDateField = componentFactory.createDateField(genericSingleItem.endDateProperty());
        endLabel.setLabelFor(endDateField);
        TimeField endTimeField = componentFactory.createTimeField(genericSingleItem.endTimeProperty());
        endTimeField.disableProperty().bind(genericSingleItem.fullDayProperty());
        ComboBox<ZoneId> endZoneBox = componentFactory.createComboBox(genericSingleItem.endZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        endZoneBox.setPrefWidth(150);
        endZoneBox.disableProperty().bind(genericSingleItem.fullDayProperty());

        Label summaryLabel = componentFactory.createLabel("Summary");
        summaryLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField summaryField = componentFactory.createTextField(genericSingleItem.summaryProperty());
        summaryLabel.setLabelFor(summaryField);
        GridPane.setMargin(summaryLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(summaryField, Priority.ALWAYS);

        Label locationLabel = componentFactory.createLabel("Location");
        locationLabel.setPadding(new Insets(0, 5, 0, 0));
        TextField locationField = componentFactory.createTextField(genericSingleItem.locationProperty());
        locationLabel.setLabelFor(locationField);
        GridPane.setMargin(locationLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(locationField, Priority.ALWAYS);

        Label availabilityLabel = componentFactory.createLabel("Availability");
        availabilityLabel.setPadding(new Insets(0, 5, 0, 0));
        ComboBox<CalendarAvailability> availabilityBox = componentFactory.createComboBox(genericSingleItem.calendarAvailabilityProperty(), FXCollections.observableArrayList(CalendarAvailability.values()));
        availabilityBox.setMaxWidth(Double.MAX_VALUE);
        availabilityLabel.setLabelFor(availabilityBox);

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

        this.setHgap(5);
        this.setVgap(5);

        this.focusedProperty().addListener((_, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                typeBox.requestFocus();
            }
        });
    }

}
