package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class JourneyItem<C extends Item> extends Item {

    private final ObservableList<C> children;

    protected JourneyItem() {
        this.children = FXCollections.observableArrayList();
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
