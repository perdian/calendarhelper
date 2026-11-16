package de.perdian.apps.calendarbuddy.modules.defaults;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.ZoneId;
import java.util.stream.Collectors;

public class DefaultsModelPane extends GridPane {

    public DefaultsModelPane(DefaultsModel defaultsModel) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label defaultAttendeesTitleLabel = componentFactory.createTitleLabel("Default Attendees");
        TextField defaultAttendeesField = componentFactory.createTextField(defaultsModel.attendeesProperty());
        defaultAttendeesField.setPrefWidth(300);
        GridPane.setHgrow(defaultAttendeesField, Priority.ALWAYS);

        ObservableList<ZoneId> allTimezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds().stream().sorted().map(ZoneId::of).collect(Collectors.toList()));
        Label defaultTimezoneTitleLabel = componentFactory.createTitleLabel("Default Timezone");
        GridPane.setMargin(defaultTimezoneTitleLabel, new Insets(8, 0, 0, 0));
        ComboBox<?> defaultTimezoneComboBox = componentFactory.createComboBox(defaultsModel.timezoneProperty(), allTimezones);
        GridPane.setHgrow(defaultTimezoneComboBox, Priority.ALWAYS);

        this.add(defaultAttendeesTitleLabel, 0, 0, 1, 1);
        this.add(defaultAttendeesField, 0, 1, 1, 1);
        this.add(defaultTimezoneTitleLabel, 0, 2, 1, 1);
        this.add(defaultTimezoneComboBox, 0, 3, 1, 1);
        this.setHgap(10);
        this.setVgap(2);

    }
}
