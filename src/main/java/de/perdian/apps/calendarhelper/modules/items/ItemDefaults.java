package de.perdian.apps.calendarhelper.modules.items;

import javafx.beans.property.*;

import java.time.ZoneId;

public class ItemDefaults {

    private final StringProperty attendees = new SimpleStringProperty();
    private final ObjectProperty<ZoneId> timezone = new SimpleObjectProperty<>(ZoneId.systemDefault());
    private final BooleanProperty createJourneyEvent = new SimpleBooleanProperty(true);

    public StringProperty attendeesProperty() {
        return this.attendees;
    }

    public ObjectProperty<ZoneId> timezoneProperty() {
        return this.timezone;
    }

    public BooleanProperty createJourneyEventProperty() {
        return this.createJourneyEvent;
    }

}
