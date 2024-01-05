package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.support.types.Availability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class AbstractItem implements Item {

    private final ObjectProperty<Availability> availability = new SimpleObjectProperty<>(Availability.BLOCKED);
    private final StringProperty id = new SimpleStringProperty();

    protected String createEventId() {
        if (StringUtils.isEmpty(this.idProperty().getValue())) {
            UUID uuid = UUID.randomUUID();
            StringBuilder eventId = new StringBuilder();
            eventId.append("calendarHelper");
            eventId.append(Math.abs(uuid.getLeastSignificantBits()));
            eventId.append(Math.abs(uuid.getMostSignificantBits()));
            this.idProperty().setValue(eventId.toString());
        }
        return this.idProperty().getValue();
    }

    protected Event createEvent() {
        Event event = new Event();
        event.setId(this.createEventId());
        event.setTransparency(this.availabilityProperty().getValue().getApiValue());
        return event;
    }

    public ObjectProperty<Availability> availabilityProperty() {
        return this.availability;
    }

    private StringProperty idProperty() {
        return this.id;
    }

}
