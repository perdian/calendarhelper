package de.perdian.apps.calendarbuddy.modules.execution;

import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

@Component
public class ExecutionModel {

    private BooleanProperty available;

    ExecutionModel(GoogleCalendarModel googleCalendarModel, ItemListModel itemListModel) {
        this.available = new SimpleBooleanProperty();
        this.available.bind(googleCalendarModel.selectedCalendarProperty().isNotNull().and(Bindings.isNotEmpty(itemListModel.getItems())));
    }

    public boolean isAvailable() {
        return available.get();
    }
    public ReadOnlyBooleanProperty availableProperty() {
        return this.available;
    }

}
