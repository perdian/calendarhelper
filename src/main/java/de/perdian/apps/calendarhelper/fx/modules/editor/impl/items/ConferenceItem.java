package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarEntryJob;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.List;

public class ConferenceItem implements EditorItem {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty comment = new SimpleStringProperty();

    @Override
    public List<GoogleCalendarEntryJob> createEntryJobs() {
        throw new UnsupportedOperationException();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }
    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
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
