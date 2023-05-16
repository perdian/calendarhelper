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
        nameField.textProperty().bindBidirectional(conferenceItem.nameProperty());
        nameLabel.setLabelFor(nameField);

        Label locationLabel = new Label("Location");
        locationLabel.setPadding(new Insets(0, 0, 0, 20));
        TextField locationField = new TextField();
        locationField.textProperty().bindBidirectional(conferenceItem.locationProperty());
        locationLabel.setLabelFor(locationField);

        Label commentLabel = new Label("Comment");
        commentLabel.setPadding(new Insets(0, 0, 0, 20));
        TextArea commentArea = new TextArea();
        commentArea.textProperty().bindBidirectional(conferenceItem.commentProperty());
        commentArea.setPrefHeight(0);
        commentLabel.setLabelFor(commentArea);
        GridPane.setHgrow(commentArea, Priority.ALWAYS);

        this.add(startDateLabel, 0, 0, 1, 1);
        this.add(startDateField, 1, 0, 1, 1);
        this.add(endDateLabel, 0, 1, 1, 1);
        this.add(endDateField, 1, 1, 1, 1);
        this.add(nameLabel, 2, 0, 1, 1);
        this.add(nameField, 3, 0, 1, 1);
        this.add(locationLabel, 2, 1, 1, 1);
        this.add(locationField, 3, 1, 1, 1);
        this.add(commentLabel, 4, 0, 1, 1);
        this.add(commentArea, 5, 0, 1, 2);
        this.setHgap(5);
        this.setVgap(5);

    }

}
