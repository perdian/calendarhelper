package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ConferenceItem implements EditorItem {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> startDateZone = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<ZoneId> endDateZone = new SimpleObjectProperty<>(ZoneId.of("Europe/Berlin"));
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty comment = new SimpleStringProperty();

    @Override
    public List<Event> createEvents() {
        Event event = new Event();
        event.setStart(new EventDateTime().setDate(new DateTime(this.startDateProperty().getValue().toString())));
        event.setEnd(new EventDateTime().setDate(new DateTime(this.endDateProperty().getValue() == null ? this.startDateProperty().getValue().toString() : this.endDateProperty().getValue().toString())));
        event.setSummary(this.nameProperty().getValue());
        return List.of(event);
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }

    public ObjectProperty<ZoneId> startDateZoneProperty() {
        return this.startDateZone;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }

    public ObjectProperty<ZoneId> endDateZoneProperty() {
        return this.endDateZone;
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }

    public SimpleStringProperty locationProperty() {
        return this.location;
    }

    public SimpleStringProperty commentProperty() {
        return this.comment;
    }

}
