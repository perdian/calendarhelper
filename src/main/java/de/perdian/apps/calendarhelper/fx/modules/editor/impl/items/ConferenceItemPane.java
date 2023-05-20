package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.support.components.DateField;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ConferenceItemPane extends GridPane {

    public ConferenceItemPane(ConferenceItem conferenceItem) {

        Label startDateLabel = new Label("Start date");
        DateField startDateField = new DateField(conferenceItem.startDateProperty());
        startDateLabel.setLabelFor(startDateField);

        Label endDateLabel = new Label("End date");
        DateField endDateField = new DateField(conferenceItem.endDateProperty());
        endDateLabel.setLabelFor(endDateField);

        Label nameLabel = new Label("Name");
        nameLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField nameField = new TextField();
        nameField.textProperty().bindBidirectional(conferenceItem.summaryProperty());
        nameLabel.setLabelFor(nameField);

        Label locationLabel = new Label("Location");
        locationLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField locationField = new TextField();
        locationField.textProperty().bindBidirectional(conferenceItem.locationProperty());
        locationLabel.setLabelFor(locationField);

        Label descriptionLabel = new Label("Description");
        descriptionLabel.setPadding(new Insets(0, 0, 0, 20));
        TextArea descriptionArea = new TextArea();
        descriptionArea.textProperty().bindBidirectional(conferenceItem.descriptionProperty());
        descriptionArea.setPrefHeight(0);
        descriptionLabel.setLabelFor(descriptionArea);
        GridPane.setHgrow(descriptionArea, Priority.ALWAYS);

        this.add(startDateLabel, 0, 0, 1, 1);
        this.add(startDateField, 1, 0, 1, 1);
        this.add(endDateLabel, 0, 1, 1, 1);
        this.add(endDateField, 1, 1, 1, 1);
        this.add(nameLabel, 2, 0, 1, 1);
        this.add(nameField, 3, 0, 1, 1);
        this.add(locationLabel, 2, 1, 1, 1);
        this.add(locationField, 3, 1, 1, 1);
        this.add(descriptionLabel, 4, 0, 1, 1);
        this.add(descriptionArea, 5, 0, 1, 2);
        this.setHgap(5);
        this.setVgap(5);

    }

}
