package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.model.impl.GenericItem;
import de.perdian.apps.calendarhelper.modules.items.model.types.Availability;
import de.perdian.apps.calendarhelper.support.fx.components.DateField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class GenericPane extends GridPane {

    private final StringProperty startDateLabel = new SimpleStringProperty("Start date");
    private final StringProperty endDateLabel = new SimpleStringProperty("End date");
    private final StringProperty summaryLabel = new SimpleStringProperty("Summary");
    private final StringProperty locationLabel = new SimpleStringProperty("Location");
    private final StringProperty availabilityLabel = new SimpleStringProperty("Availability");
    private final StringProperty descriptionLabel = new SimpleStringProperty("Description");
    private final StringProperty attendeesLabel = new SimpleStringProperty("Attendees");

    public GenericPane(GenericItem item) {

        Label startDateLabel = new Label();
        startDateLabel.textProperty().bind(this.startDateLabelProperty());
        DateField startDateField = new DateField(item.startDateProperty());
        startDateLabel.setLabelFor(startDateField);

        Label endDateLabel = new Label();
        endDateLabel.textProperty().bind(this.endDateLabelProperty());
        DateField endDateField = new DateField(item.endDateProperty());
        endDateLabel.setLabelFor(endDateField);

        Label summaryLabel = new Label();
        summaryLabel.textProperty().bind(this.summaryLabelProperty());
        summaryLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField summaryField = new TextField();
        summaryField.textProperty().bindBidirectional(item.summaryProperty());
        summaryLabel.setLabelFor(summaryField);

        Label locationLabel = new Label();
        locationLabel.textProperty().bind(this.locationLabelProperty());
        locationLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField locationField = new TextField();
        locationField.textProperty().bindBidirectional(item.locationProperty());
        locationLabel.setLabelFor(locationField);

        Label availabilityLabel = new Label();
        availabilityLabel.textProperty().bind(this.availabilityLabelProperty());
        ComboBox<Availability> availabilityBox = new ComboBox<>(FXCollections.observableArrayList(Availability.values()));
        availabilityBox.valueProperty().bindBidirectional(item.availabilityProperty());
        availabilityBox.setMaxWidth(Double.MAX_VALUE);
        availabilityLabel.setLabelFor(availabilityBox);

        Label descriptionLabel = new Label();
        descriptionLabel.textProperty().bind(this.descriptionLabelProperty());
        descriptionLabel.setPadding(new Insets(0, 0, 0, 20));
        TextArea descriptionArea = new TextArea();
        descriptionArea.textProperty().bindBidirectional(item.descriptionProperty());
        descriptionArea.setPrefHeight(0);
        descriptionLabel.setLabelFor(descriptionArea);
        GridPane.setHgrow(descriptionArea, Priority.ALWAYS);

        Label attendeesLabel = new Label();
        attendeesLabel.textProperty().bind(this.attendeesLabelProperty());
        attendeesLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField attendeesField = new TextField();
        attendeesField.textProperty().bindBidirectional(item.attendeesProperty());
        attendeesLabel.setLabelFor(attendeesField);
        GridPane.setHgrow(attendeesField, Priority.ALWAYS);

        this.add(startDateLabel, 0, 0, 1, 1);
        this.add(startDateField, 1, 0, 1, 1);
        this.add(endDateLabel, 0, 1, 1, 1);
        this.add(endDateField, 1, 1, 1, 1);
        this.add(availabilityLabel, 0, 2, 1, 1);
        this.add(availabilityBox, 1, 2, 1, 1);
        this.add(summaryLabel, 2, 0, 1, 1);
        this.add(summaryField, 3, 0, 1, 1);
        this.add(locationLabel, 2, 1, 1, 1);
        this.add(locationField, 3, 1, 1, 1);
        this.add(descriptionLabel, 4, 0, 1, 1);
        this.add(descriptionArea, 5, 0, 1, 2);
        this.add(attendeesLabel, 4, 2, 1, 1);
        this.add(attendeesField, 5, 2, 1, 1);
        this.setHgap(5);
        this.setVgap(5);

    }


    public StringProperty startDateLabelProperty() {
        return this.startDateLabel;
    }

    public StringProperty endDateLabelProperty() {
        return this.endDateLabel;
    }

    public StringProperty summaryLabelProperty() {
        return this.summaryLabel;
    }

    public StringProperty locationLabelProperty() {
        return this.locationLabel;
    }

    public StringProperty availabilityLabelProperty() {
        return this.availabilityLabel;
    }

    public StringProperty descriptionLabelProperty() {
        return this.descriptionLabel;
    }

    public StringProperty attendeesLabelProperty() {
        return this.attendeesLabel;
    }

}
