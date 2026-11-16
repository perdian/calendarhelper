package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import de.perdian.apps.calendarbuddy.modules.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class JourneyItem<C extends Item> extends Item {

    private final ObservableList<C> children;

    protected JourneyItem() {
        this.children = FXCollections.observableArrayList();
    }

    @Override
    public List<Event> createEvents(DefaultsModel defaultsModel) {
        throw new UnsupportedOperationException();
    }

    protected C appendChildItem() {
        C childItem = this.createChildItem();
        this.getChildren().add(childItem);
        return childItem;
    }

    protected abstract C createChildItem();

    protected ObservableList<C> getChildren() {
        return this.children;
    }

    protected boolean removeChild(C childItem) {
        if (this.getChildren().size() > 1) {
            return this.getChildren().remove(childItem);
        } else {
            return false;
        }
    }

}
