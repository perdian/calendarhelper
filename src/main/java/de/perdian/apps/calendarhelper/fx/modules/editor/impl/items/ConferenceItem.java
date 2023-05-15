package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import de.perdian.apps.calendarhelper.services.google.calendar.GoogleCalendarEntryJob;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class ConferenceItem implements EditorItem {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();

    @Override
    public GoogleCalendarEntryJob createEntryJob() {
        throw new UnsupportedOperationException();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }
    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }

}
