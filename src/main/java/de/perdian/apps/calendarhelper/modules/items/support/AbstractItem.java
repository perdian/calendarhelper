package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.support.types.Availability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class AbstractItem implements Item {

    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);

    protected Event createEvent() {
        Event event = new Event();
        event.setTransparency(this.availabilityProperty().getValue().getApiValue());
        return event;
    }

    public ObjectProperty<Availability> availabilityProperty() {
        return this.availability;
    }

}
