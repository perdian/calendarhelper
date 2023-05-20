package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public abstract class AbstractDateItem implements EditorItem {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final SimpleStringProperty summary = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();

    protected Event createEvent() {
        if (this.startDateProperty().getValue() == null) {
            throw new IllegalArgumentException("No start date set!");
        } else {
            Event event = new Event();
            event.setStart(new EventDateTime().setDate(new DateTime(this.startDateProperty().getValue().toString())));
            event.setEnd(new EventDateTime().setDate(new DateTime(this.endDateProperty().getValue() == null ? this.startDateProperty().getValue().toString() : this.endDateProperty().getValue().toString())));
            event.setSummary(this.summaryProperty().getValue());
            event.setLocation(this.locationProperty().getValue());
            event.setDescription(this.descriptionProperty().getValue());
            return event;
        }
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }

    public SimpleStringProperty summaryProperty() {
        return this.summary;
    }

    public SimpleStringProperty locationProperty() {
        return this.location;
    }

    public SimpleStringProperty descriptionProperty() {
        return this.description;
    }

}
