package de.perdian.apps.calendarhelper.fx.modules.calendar;

import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendar;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class ActiveCalendarPane extends BorderPane {

    public ActiveCalendarPane(ObservableList<GoogleCalendar> allCalenders, ObjectProperty<GoogleCalendar> activeCalendar) {
    }

}
