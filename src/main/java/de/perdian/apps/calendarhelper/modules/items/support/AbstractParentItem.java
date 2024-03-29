package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParentItem<C extends Item> implements Item {

    private final ObservableList<C> children = FXCollections.observableArrayList();

    @Override
    public List<Event> createEvents() {
        List<Event> allEvents = new ArrayList<>();
        this.getChildren().forEach(childItem -> allEvents.addAll(childItem.createEvents()));
        return allEvents;
    }

    public C appendChild(ItemDefaults itemDefaults) {
        C childInstance = this.createChildInstance(itemDefaults);
        this.getChildren().add(childInstance);
        return childInstance;
    }

    protected abstract C createChildInstance(ItemDefaults itemDefaults);

    public ObservableList<C> getChildren() {
        return this.children;
    }

}
