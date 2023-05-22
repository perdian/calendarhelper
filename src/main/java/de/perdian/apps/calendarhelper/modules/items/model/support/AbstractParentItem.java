package de.perdian.apps.calendarhelper.modules.items.model.support;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class AbstractParentItem<T extends Item> extends AbstractItem {

    private final ObservableList<T> children = FXCollections.observableArrayList();

    @Override
    public List<Event> createEvents() {
        return this.getChildren().stream()
                .flatMap(child -> child.createEvents().stream())
                .toList();
    }

    public ObservableList<T> getChildren() {
        return this.children;
    }

}
