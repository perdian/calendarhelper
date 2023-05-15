package de.perdian.apps.calendarhelper.fx.modules.calendar;

import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendar;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class ActiveCalendarPane extends BorderPane {

    public ActiveCalendarPane(ObservableList<GoogleCalendar> allCalenders, ObjectProperty<GoogleCalendar> activeCalendar) {

        ComboBox<GoogleCalendar> activeCalendarBox = new ComboBox<>(allCalenders);
        activeCalendarBox.setMaxWidth(Double.MAX_VALUE);
        activeCalendarBox.disableProperty().bind(Bindings.isEmpty(allCalenders));
        activeCalendar.addListener((o, oldValue, newValue) -> {
            if (newValue == null) {
                Platform.runLater(() -> activeCalendarBox.getSelectionModel().clearSelection());
            } else if (!Objects.equals(newValue, activeCalendarBox.getSelectionModel().getSelectedItem())) {
                Platform.runLater(() -> activeCalendarBox.getSelectionModel().select(newValue));
            }
        });
        activeCalendarBox.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (!Objects.equals(newValue, activeCalendar.getValue())) {
                activeCalendar.setValue(newValue);
            }
        });
        this.setCenter(activeCalendarBox);

    }

}
