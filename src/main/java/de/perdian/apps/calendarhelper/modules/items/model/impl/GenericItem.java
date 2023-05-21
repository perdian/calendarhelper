package de.perdian.apps.calendarhelper.modules.items.model.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractDateTimeItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GenericItem extends AbstractDateTimeItem {

    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.summaryProperty().getValue());
        event.setLocation(this.locationProperty().getValue());
        event.setDescription(this.descriptionProperty().getValue());
        return event;
    }

    public StringProperty summaryProperty() {
        return this.summary;
    }

    public StringProperty locationProperty() {
        return this.location;
    }

    public StringProperty descriptionProperty() {
        return this.description;
    }

}
