package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import de.perdian.apps.calendarhelper.modules.items.support.types.CalendarAvailability;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
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

    public GenericPane(GenericItem item, ComponentFactory componentFactory) {

        ObservableList<GenericType> typeValues = FXCollections.observableArrayList(GenericType.values());
        typeValues.add(0, null);
        Label typeLabel = componentFactory.createLabel("Type");
        ComboBox<GenericType> typeBox = componentFactory.createComboBox(item.typeProperty(), typeValues);
        typeBox.setMaxWidth(Double.MAX_VALUE);

        Label startLabel = componentFactory.createLabel("Start");
        DateField startDateField = componentFactory.createDateField(item.getCalendarValues().startDateProperty());
        startLabel.setLabelFor(startDateField);
        TimeField startTimeField = componentFactory.createTimeField(item.getCalendarValues().startTimeProperty());
        startTimeField.disableProperty().bind(item.getCalendarValues().fullDayProperty());
        ComboBox<ZoneId> startZoneBox = componentFactory.createComboBox(item.getCalendarValues().startZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        startZoneBox.setPrefWidth(150);
        startZoneBox.disableProperty().bind(item.getCalendarValues().fullDayProperty());

        CheckBox fullDayBox = componentFactory.createCheckBox("Full day", item.getCalendarValues().fullDayProperty());
        GridPane.setMargin(fullDayBox, new Insets(0, 0, 0, 10));

        Label endLabel = componentFactory.createLabel("End");
        DateField endDateField = componentFactory.createDateField(item.getCalendarValues().endDateProperty());
        endLabel.setLabelFor(endDateField);
        TimeField endTimeField = componentFactory.createTimeField(item.getCalendarValues().endTimeProperty());
        endTimeField.disableProperty().bind(item.getCalendarValues().fullDayProperty());
        ComboBox<ZoneId> endZoneBox = componentFactory.createComboBox(item.getCalendarValues().endZoneIdProperty(), FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList()));
        endZoneBox.setPrefWidth(150);
        endZoneBox.disableProperty().bind(item.getCalendarValues().fullDayProperty());

        Label summaryLabel = componentFactory.createLabel("Summary");
        TextField summaryField = componentFactory.createTextField(item.summaryProperty());
        summaryLabel.setLabelFor(summaryField);
        GridPane.setMargin(summaryLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(summaryField, Priority.ALWAYS);

        Label locationLabel = componentFactory.createLabel("Location");
        TextField locationField = componentFactory.createTextField(item.locationProperty());
        locationLabel.setLabelFor(locationField);
        GridPane.setMargin(locationLabel, new Insets(0, 0, 0, 20));
        GridPane.setHgrow(locationField, Priority.ALWAYS);

        Label availabilityLabel = componentFactory.createLabel("Availability");
        ComboBox<CalendarAvailability> availabilityBox = componentFactory.createComboBox(item.getCalendarValues().calendarAvailabilityProperty(), FXCollections.observableArrayList(CalendarAvailability.values()));
        availabilityBox.setMaxWidth(Double.MAX_VALUE);
        availabilityLabel.setLabelFor(availabilityBox);

        Label descriptionLabel = componentFactory.createLabel("Description");
        TextArea descriptionArea = componentFactory.createTextArea(item.descriptionProperty());
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

        this.focusedProperty().addListener((_, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                typeBox.requestFocus();
            }
        });

    }

}
