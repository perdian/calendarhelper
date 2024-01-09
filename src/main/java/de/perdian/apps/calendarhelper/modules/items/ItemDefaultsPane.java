package de.perdian.apps.calendarhelper.modules.items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.ZoneId;

public class ItemDefaultsPane extends GridPane {

    public ItemDefaultsPane(ItemDefaults itemDefaults) {

        Label attendeesLabel = new Label("Attendees");
        TextField attendeesField = new TextField();
        attendeesField.textProperty().bindBidirectional(itemDefaults.attendeesProperty());
        attendeesLabel.setLabelFor(attendeesField);
        GridPane.setHgrow(attendeesField, Priority.ALWAYS);

        ObservableList<ZoneId> availableTimezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).toList());
        Label timezoneLabel = new Label("Timezone");
        timezoneLabel.setPadding(new Insets(5, 0, 0, 0));
        ComboBox<ZoneId> timezoneBox = new ComboBox<>(availableTimezones);
        timezoneBox.valueProperty().bindBidirectional(itemDefaults.timezoneProperty());
        timezoneBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(timezoneBox, Priority.ALWAYS);

        this.add(attendeesLabel, 0, 0, 1, 1);
        this.add(attendeesField, 0, 1, 1, 1);
        this.add(timezoneLabel, 0, 2, 1, 1);
        this.add(timezoneBox, 0, 3, 1, 1);

        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(2);

    }

}
