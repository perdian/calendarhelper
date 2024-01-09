package de.perdian.apps.calendarhelper.modules.items;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.ZoneId;

public class ItemDefaults {

    private final StringProperty attendees = new SimpleStringProperty();
    private final ObjectProperty<ZoneId> timezone = new SimpleObjectProperty<>(ZoneId.systemDefault());

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    public ObjectProperty<ZoneId> timezoneProperty() {
        return this.timezone;
    }

}
