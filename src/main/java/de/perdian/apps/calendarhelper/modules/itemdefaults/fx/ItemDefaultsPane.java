package de.perdian.apps.calendarhelper.modules.itemdefaults.fx;

import de.perdian.apps.calendarhelper.modules.items.model.ItemDefaults;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ItemDefaultsPane extends GridPane {

    public ItemDefaultsPane(ItemDefaults itemDefaults) {

        Label attendeesLabel = new Label("Attendees");
        TextField attendeesField = new TextField();
        attendeesField.textProperty().bindBidirectional(itemDefaults.attendeesProperty());
        attendeesLabel.setLabelFor(attendeesField);
        GridPane.setHgrow(attendeesField, Priority.ALWAYS);

        this.add(attendeesLabel, 0, 0, 1, 1);
        this.add(attendeesField, 0, 1, 1, 1);

        this.setPadding(new Insets(10, 10, 10, 10));
        this.setHgap(5);
        this.setVgap(5);

    }

}
